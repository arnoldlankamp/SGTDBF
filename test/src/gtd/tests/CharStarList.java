package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.StarList;
import gtd.result.AbstractNode;

/*
S ::= [a-z]*
*/
public class CharStarList{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new StarList(new CharRange('a', 'z')))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(CharStarList.class).generate();
		Parser csl = new Parser("abc".toCharArray(), structure);
		AbstractNode result = csl.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z]*([a-z](a),[a-z](b),[a-z](c))) <- good");
	}
}
