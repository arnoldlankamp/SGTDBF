package gtd.generator;

import gtd.grammar.structure.Alternative;
import gtd.stack.AbstractStackNode;
import gtd.util.HashMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FromClassGenerator{
	private final Object parserInstance;
	
	public FromClassGenerator(Object parserInstance){
		super();
		
		this.parserInstance = parserInstance;
	}
	
	public HashMap<String, AbstractStackNode[]> generate(){
		HashMap<String, AbstractStackNode[]> expectMatrix = new HashMap<String, AbstractStackNode[]>();
		
		int idCounter = 1;
		
		Method[] methods = parserInstance.getClass().getMethods();
		for(int i = methods.length - 1; i >= 0; --i){
			Method method = methods[i];
			
			if(method.getReturnType() == Alternative[].class &&
					(method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC){
				try{
					Alternative[] alternatives = (Alternative[]) method.invoke(parserInstance);
					Preprocessor alternativesProcessor = new Preprocessor(alternatives, idCounter);
					AbstractStackNode[] expects = alternativesProcessor.buildExpects();
					expectMatrix.put(method.getName(), expects);
					idCounter = alternativesProcessor.getIdCounter();
				}catch(IllegalAccessException ex){
					throw new RuntimeException(ex); // Should never happen
				}catch(InvocationTargetException ex){
					throw new RuntimeException("Unable to invoke method: " + method.getName() + " on class: " + parserInstance.getClass().getName(), ex);
				}catch(EmptyAlternativeException ex){
					throw new RuntimeException(String.format("Encountered an empty alternative in: %s.", method.getName()), ex);
				}
			}
		}
		
		return expectMatrix;
	}
}
