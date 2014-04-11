package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A
A ::= AA | a
*/
public class Ambiguous7{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous7.class).generate();
		Parser a7 = new Parser("aaaa".toCharArray(), structure);
		AbstractNode result = a7.parse("S");
		System.out.println(result);
		
		System.out.println("S([A(A(A('a'),A('a')),A(A('a'),A('a'))),A(A('a'),[A(A('a'),A(A('a'),A('a'))),A(A(A('a'),A('a')),A('a'))]),A([A(A('a'),A(A('a'),A('a'))),A(A(A('a'),A('a')),A('a'))],A('a'))]) <- good");
	}
}
