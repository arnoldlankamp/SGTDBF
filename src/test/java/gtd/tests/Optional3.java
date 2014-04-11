package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Optional;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= aO? | aA
O ::= A
A ::= a
*/
public class Optional3{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Char('a'), new Optional(new Sort("O"))),
			new Alternative(new Char('a'), new Sort("A"))
		};
	}
	
	public static Alternative[] O(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Optional3.class).generate();
		Parser o3 = new Parser("aa".toCharArray(), structure);
		AbstractNode result = o3.parse("S");
		System.out.println(result);
		
		System.out.println("[S('a',A('a')),S('a',O?(O(A('a'))))] <- good");
	}
}

