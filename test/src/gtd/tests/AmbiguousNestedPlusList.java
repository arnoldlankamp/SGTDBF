package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= [a]+
*/
public class AmbiguousNestedPlusList{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new PlusList(new Char('a')))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(AmbiguousNestedPlusList.class).generate();
		Parser anpl = new Parser("aa".toCharArray(), structure);
		AbstractNode result = anpl.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+(A([a]+([a](a))),A([a]+([a](a)))),A+(A([a]+([a](a),[a](a))))]) <- good");
	}
}
