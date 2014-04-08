package gtd.examples;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class HelloWorld {

	public static Alternative[] Exclamation() {
		return new Alternative[] {
			new Alternative(new Sort("Word"), new PlusList(new Sort("Whitespace")), new Sort("Word"))
		};
	}

	public static Alternative[] Word() {
		return new Alternative[] {
			new Alternative(new Literal("Hello")),
			new Alternative(new Literal("world"))
		};
	}

	public static Alternative[] Whitespace() {
		return new Alternative[]{
			new Alternative(new Char(' '))
		};
	}

	public static void main(String[] args) {
		// Convert the given class into a for the parser usable format (this structure is reusable)
		ParserStructure structure = new FromClassGenerator(HelloWorld.class).generate();

		char[] input = "Hello world".toCharArray();
		// Construct a new parser instance for the given input string
		Parser parser = new Parser(input, structure);

		// Start the parser with the given sort as start symbol
		AbstractNode result = parser.parse("Exclamation");
		System.out.println(result);
	}
}
