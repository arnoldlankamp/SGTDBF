package gtd.examples;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.IStructure;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.RSort;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class Associativity {

	public static IStructure[] Number() {
		return new IStructure[] {
			new Alternative(new CharRange('0', '9'))
		};
	}

	public static IStructure[] Expr() {
		return new IStructure[] {
			// Restrict the nesting of the self recursive alternatives of this sort
			// on the right side.
			new Alternative(new Sort("Expr"), new Char('+'), new RSort("Expr")),
			new Alternative(new Sort("Expr"), new Char('-'), new RSort("Expr")),
			new Alternative(new Sort("Number"))
		};
	}

	public static void main(String[] args) {
		ParserStructure structure = new FromClassGenerator(Associativity.class).generate();

		char[] input = "1+2-3".toCharArray();
		Parser parser = new Parser(input, structure);

		AbstractNode result = parser.parse("Expr");
		System.out.println(result);
	}
}
