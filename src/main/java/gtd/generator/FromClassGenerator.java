package gtd.generator;

import gtd.generator.IdentifiedSymbol.Key;
import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.IStructure;
import gtd.stack.AbstractStackNode;
import gtd.util.ArrayList;
import gtd.util.IntegerKeyedHashMap;
import gtd.util.IntegerKeyedHashMap.Entry;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;

public class FromClassGenerator{
	private final static String MODULE_IMPORTS_FIELD_NAME = "IMPORTS";

	private final Class<?> parserClass;
	
	public FromClassGenerator(Class<?> parserClass){
		super();
		
		this.parserClass = parserClass;
	}
	
	private static void gatherModuleImports(Class<?> parserClass, ArrayList<Class<?>> sourceClasses){
		try{
			Field importsField = parserClass.getField(MODULE_IMPORTS_FIELD_NAME);
			if(Class[].class.isAssignableFrom(importsField.getType()) &&
					(importsField.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC &&
					(importsField.getModifiers() & Modifier.STATIC) == Modifier.STATIC){
				Class<?>[] imports = (Class<?>[]) importsField.get(parserClass);
				for(int i = 0; i < imports.length; ++i){
					Class<?> importedClass = imports[i];
					if(!sourceClasses.contains(importedClass)){ // O(N^2) operation, but the list should be fairly short anyway
						sourceClasses.add(importedClass);
					}
				}
				return;
			}
		}catch(SecurityException ex){
			throw new RuntimeException(ex); // Should never happen
		}catch(NoSuchFieldException ex){
			// Ignore, imports are absent
		}catch(IllegalArgumentException ex){
			throw new RuntimeException(ex); // Should never happen
		}catch(IllegalAccessException ex){
			throw new RuntimeException(ex); // Should never happen
		}
	}
	
	public ParserStructure generate(){
		IntegerKeyedHashMap<AbstractStackNode[]> expectMap = new IntegerKeyedHashMap<AbstractStackNode[]>();
		
		ArrayList<Key> containerIndexMap = new ArrayList<Key>();
		GrammarEncoder grammarEncoder = new GrammarEncoder(containerIndexMap);
		
		int idCounter = 0;
		
		ArrayList<Class<?>> sourceClasses = new ArrayList<Class<?>>();
		sourceClasses.add(parserClass);
		gatherModuleImports(parserClass, sourceClasses);
		
		for(int j = sourceClasses.size() - 1; j >= 0; --j){
			Method[] methods = sourceClasses.get(j).getMethods();
			for(int i = methods.length - 1; i >= 0; --i){
				Method method = methods[i];
				
				if(IStructure[].class.isAssignableFrom(method.getReturnType()) &&
						(method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC &&
						(method.getModifiers() & Modifier.STATIC) == Modifier.STATIC){
					try{
						IStructure[] structures = (IStructure[]) method.invoke(null);
						
						IntegerKeyedHashMap<ArrayList<Alternative>> encodedAlternatives = grammarEncoder.flatten(method.getName(), structures);
						Iterator<Entry<ArrayList<Alternative>>> encodedAlternativesIterator = encodedAlternatives.entryIterator();
						while(encodedAlternativesIterator.hasNext()){
							Entry<ArrayList<Alternative>> encodedAlternativesEntry = encodedAlternativesIterator.next();
							int sortIndex = encodedAlternativesEntry.key;
							ArrayList<Alternative> alternatives = encodedAlternativesEntry.value;
							
							Preprocessor alternativesProcessor = new Preprocessor(alternatives, idCounter);
							AbstractStackNode[] expects = alternativesProcessor.buildExpects();
	
							expectMap.putUnsafe(sortIndex, expects);
							idCounter = alternativesProcessor.getIdCounter();
						}
					}catch(IllegalAccessException ex){
						throw new RuntimeException(ex); // Should never happen
					}catch(InvocationTargetException ex){
						throw new RuntimeException("Unable to invoke method: " + method.getName() + " on class: " + parserClass.getName(), ex);
					}catch(EmptyAlternativeException ex){
						throw new RuntimeException(String.format("Encountered an empty alternative in: %s.", method.getName()), ex);
					}
				}
			}
		}
		
		ArrayList<String> sortIndexMap = new ArrayList<String>();
		for(int i = 0; i < containerIndexMap.size(); ++i){
			Key identifiedSymbolKey = containerIndexMap.get(i);
			sortIndexMap.add(identifiedSymbolKey.scopeId == 0 && !identifiedSymbolKey.isRestricted ? identifiedSymbolKey.symbolName : null);
		}
		
		AbstractStackNode[][] expectMatrix = new AbstractStackNode[sortIndexMap.size()][];
		for(int i = 0; i < sortIndexMap.size(); ++i){
			expectMatrix[i] = expectMap.get(i);
		}
		return new ParserStructure(expectMatrix, sortIndexMap, idCounter + 1);
	}
}
