package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= a | epsilon
*/
public class AmbiguousEpsilonList{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Char('a')),
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(AmbiguousEpsilonList.class).generate();
		Parser ael = new Parser("a".toCharArray(), structure);
		AbstractNode result = ael.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+(repeat(A()),A('a')),A+(A('a')),A+([A+(A('a')),A+(repeat(A()),A('a'))],repeat(A()))]) <- good");
	}
}
