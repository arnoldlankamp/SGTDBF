package gtd.examples;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class Imports {

	public static class GenericStuff {

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

	public static class Spec {
		public final static Class<?>[] IMPORTS = new Class<?>[]{GenericStuff.class};

		public static Alternative[] B() {
			return new Alternative[] {
				new Alternative(new Literal("b"))
			};
		}

		public static Alternative[] S() {
			return new Alternative[] {
				new Alternative(new Sort("A")),
				new Alternative(new Sort("A"), new Sort("B"))
			};
		}
	}

	public static void main(String[] args) {
		// Generate a parser structure for the SubClass
		ParserStructure structure = new FromClassGenerator(Spec.class).generate();

		char[] input = "ab".toCharArray();
		Parser parser = new Parser(input, structure);

		AbstractNode result = parser.parse("S");
		System.out.println(result);
	}
}
