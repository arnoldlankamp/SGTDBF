package gtd.examples;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.IStructure;
import gtd.grammar.structure.Scope;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.TLSort;
import gtd.result.AbstractNode;

public class Priority {

	public static IStructure[] Number() {
		return new IStructure[] {
			new Alternative(new CharRange('0', '9'))
		};
	}

	public static IStructure[] Expr() {
		return new IStructure[] {
			new Alternative(new Sort("Expr"), new Char('+'), new Sort("Expr")),
			// Recursive sorts within a scope can only contain alternatives from
			// within that scope and scopes below it in the hierarchy.
			new Scope(
				new Alternative(new Sort("Expr"), new Char('*'), new Sort("Expr")),
				// The top-level version of the sort can be referenced from within
				// a scope using TLSorts instead of 'normal' ones.
				new Alternative(new Char('('), new TLSort("Expr"), new Char(')')),
				new Alternative(new Sort("Number"))
			)
		};
	}

	public static void main(String[] args) {
		ParserStructure structure = new FromClassGenerator(Priority.class).generate();

		char[] input = "1+2*(3*4+5)".toCharArray();
		Parser parser = new Parser(input, structure);

		AbstractNode result = parser.parse("Expr");
		System.out.println(result);
	}
}
