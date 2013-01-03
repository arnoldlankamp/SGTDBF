package gtd.generator;

import gtd.grammar.structure.Alternative;
import gtd.stack.AbstractStackNode;
import gtd.util.ArrayList;
import gtd.util.IntegerKeyedHashMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FromClassGenerator{
	private final Class<?> parserClass;
	
	public FromClassGenerator(Class<?> parserClass){
		super();
		
		this.parserClass = parserClass;
	}
	
	public ParserStructure generate(){
		IntegerKeyedHashMap<AbstractStackNode[]> expectMap = new IntegerKeyedHashMap<AbstractStackNode[]>();
		ArrayList<String> sortIndexMap = new ArrayList<String>();
		
		int idCounter = 1;
		
		Method[] methods = parserClass.getMethods();
		for(int i = methods.length - 1; i >= 0; --i){
			Method method = methods[i];
			
			if(method.getReturnType() == Alternative[].class &&
					(method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC &&
					(method.getModifiers() & Modifier.STATIC) == Modifier.STATIC){
				try{
					Alternative[] alternatives = (Alternative[]) method.invoke(null);
					Preprocessor alternativesProcessor = new Preprocessor(alternatives, idCounter);
					AbstractStackNode[] expects = alternativesProcessor.buildExpects(sortIndexMap);
					int sortIndex = Preprocessor.getContainerIndex(sortIndexMap, method.getName());
					expectMap.putUnsafe(sortIndex, expects);
					idCounter = alternativesProcessor.getIdCounter();
				}catch(IllegalAccessException ex){
					throw new RuntimeException(ex); // Should never happen
				}catch(InvocationTargetException ex){
					throw new RuntimeException("Unable to invoke method: " + method.getName() + " on class: " + parserClass.getName(), ex);
				}catch(EmptyAlternativeException ex){
					throw new RuntimeException(String.format("Encountered an empty alternative in: %s.", method.getName()), ex);
				}
			}
		}
		
		AbstractStackNode[][] expectMatrix = new AbstractStackNode[sortIndexMap.size()][];
		for(int i = 0; i < sortIndexMap.size(); ++i){
			expectMatrix[i] = expectMap.get(i);
		}
		return new ParserStructure(expectMatrix, sortIndexMap);
	}
}
