package gtd.generator;

import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Sort;
import gtd.stack.AbstractStackNode;
import gtd.util.HashMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FromClassGenerator{
	private final Class<?> clazz;
	
	public FromClassGenerator(Class<?> clazz){
		super();
		
		this.clazz = clazz;
	}
	
	public HashMap<String, AbstractStackNode[]> generate(){
		HashMap<String, AbstractStackNode[]> expectMatrix = new HashMap<String, AbstractStackNode[]>();
		
		int idCounter = 1;
		
		Method[] methods = clazz.getMethods();
		for(int i = methods.length - 1; i >= 0; --i){
			Method method = methods[i];
			
			if(method.getReturnType() == Alternative[].class &&
					(method.getModifiers() & Modifier.STATIC) == Modifier.STATIC &&
					(method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC){
				try{
					Alternative[] alternatives = (Alternative[]) method.invoke(null);
					Preprocessor alternativesProcessor = new Preprocessor(alternatives, idCounter);
					AbstractStackNode[] expects = alternativesProcessor.buildExpects();
					expectMatrix.put(method.getName(), expects);
					idCounter = alternativesProcessor.getIdCounter();
				}catch(IllegalAccessException ex){
					throw new RuntimeException(ex); // Should never happen
				}catch(InvocationTargetException ex){
					throw new RuntimeException("Unable to invoke method: " + method.getName() + " on class: " + clazz.getName(), ex);
				}
			}
		}
		
		return expectMatrix;
	}
	
	// Temp (for testing only)
	private static class TestParser{
		public static Alternative[] S(){
			return new Alternative[]{
					new Alternative(new Sort("A"), new Sort("B")),
					new Alternative(new Sort("C")),
					new Alternative(new Sort("C"), new Sort("D"))
					};
		}
	}
	
	public static void main(String[] args){
		FromClassGenerator generator = new FromClassGenerator(TestParser.class);
		HashMap<String, AbstractStackNode[]> expectMatrix = generator.generate();
		System.out.println(expectMatrix.containsKey("S"));
		System.out.println(expectMatrix.get("S").length);
	}
}
