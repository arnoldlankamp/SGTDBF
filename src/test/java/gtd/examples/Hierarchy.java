package gtd.examples;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class Hierarchy {
	
	public static class SuperClass {
		
		public static Alternative[] A() {
			return new Alternative[] {
				new Alternative(new Literal("a"))
			};
		}
		
		public static Alternative[] S() {
			return new Alternative[] {
				new Alternative(new Sort("A"))
			};
		}
	}
	
	public static class SubClass extends SuperClass {
		// Add a new sort
		public static Alternative[] B() {
			return new Alternative[] {
				new Alternative(new Literal("b"))
			};
		}
		
		// Replace the definition of S
		public static Alternative[] S() {
			return new Alternative[] {
				new Alternative(new Sort("A")),
				new Alternative(new Sort("B"))
			};
		}
	}
	
	public static void main(String[] args) {
		// Generate a parser structure for the SubClass
		ParserStructure structure = new FromClassGenerator(SubClass.class).generate();

		char[] input = "b".toCharArray();
		Parser parser = new Parser(input, structure);

		AbstractNode result = parser.parse("S");
		System.out.println(result);
	}
}
