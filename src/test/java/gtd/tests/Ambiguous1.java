package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A | a
A ::= a
*/
public class Ambiguous1{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A")),
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous1.class).generate();
		Parser a1 = new Parser("a".toCharArray(), structure);
		AbstractNode result = a1.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A('a')),S('a')] <- good");
	}
}
