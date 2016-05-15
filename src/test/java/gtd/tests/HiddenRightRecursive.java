package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class HiddenRightRecursive {
	
	public static Alternative[] A() {
		return new Alternative[] {
			new Alternative(new Sort("B"), new Epsilon())
		};
	}

	public static Alternative[] B() {
		return new Alternative[] {
			new Alternative(new Literal("y")),
			new Alternative(new Literal("x"), new Sort("A"))
		};
	}

	public static void main(String[] args) {
		ParserStructure structure = new FromClassGenerator(HiddenRightRecursive.class).generate();
		Parser hrr = new Parser("xy".toCharArray(), structure);
		AbstractNode result = hrr.parse("A");
		System.out.println(result);
		
		System.out.println("A(B(\"x\",A(B(\"y\"),)),) <- good");
	}
}
