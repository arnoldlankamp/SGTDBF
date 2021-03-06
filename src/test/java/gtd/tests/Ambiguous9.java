package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= E
E ::= E + E | E * E | 1

NOTE: This test, tests prefix sharing.
*/
public class Ambiguous9{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("E"))
		};
	}
	
	public static Alternative[] E(){
		return new Alternative[]{
			new Alternative(new Sort("E"), new Char('+'), new Sort("E")),
			new Alternative(new Sort("E"), new Char('*'), new Sort("E")),
			new Alternative(new Char('1'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous9.class).generate();
		Parser a9 = new Parser("1+1+1".toCharArray(), structure);
		AbstractNode result = a9.parse("S");
		System.out.println(result);
		
		System.out.println("S([E(E(E('1'),'+',E('1')),'+',E('1')),E(E('1'),'+',E(E('1'),'+',E('1')))]) <- good");
	}
}
