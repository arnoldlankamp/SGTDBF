package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.PlusList;
import gtd.result.AbstractNode;

/*
S ::= [a-z]+
*/
public class CharPlusList{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new CharRange('a', 'z')))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(CharPlusList.class).generate();
		Parser cpl = new Parser("abc".toCharArray(), structure);
		AbstractNode result = cpl.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z]+('a','b','c')) <- good");
	}
}
