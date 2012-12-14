package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.StarList;
import gtd.result.AbstractNode;

/*
S ::= [a-z]*
*/
public class CharStarList extends SGTDBF{
	
	public CharStarList(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new StarList(new CharRange('a', 'z')))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(CharStarList.class).generate();
		CharStarList csl = new CharStarList("abc".toCharArray(), structure);
		AbstractNode result = csl.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z]*([a-z](a),[a-z](b),[a-z](c))) <- good");
	}
}
