package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AB | AC
A ::= a
B ::= a
C ::= a

NOTE: This test, tests prefix sharing.
*/
public class Ambiguous8{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("B")),
			new Alternative(new Sort("A"), new Sort("C"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous8.class).generate();
		Parser a8 = new Parser("aa".toCharArray(), structure);
		AbstractNode result = a8.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A('a'),C('a')),S(A('a'),B('a'))] <- good");
	}
}
