package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.result.AbstractNode;

/*
S ::= [a-z]
*/
public class CharRange{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new gtd.grammar.symbols.CharRange('a', 'z'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(CharRange.class).generate();
		Parser cr = new Parser("a".toCharArray(), structure);
		AbstractNode result = cr.parse("S");
		System.out.println(result);
		
		System.out.println("S('a') <- good");
	}
}
