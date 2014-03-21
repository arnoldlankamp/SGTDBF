package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Optional;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= aO? | aA
O ::= A
A ::= a
*/
public class Optional3 extends SGTDBF{
	
	public Optional3(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Literal("a"), new Optional(new Sort("O"))),
			new Alternative(new Literal("a"), new Sort("A"))
		};
	}
	
	public static Alternative[] O(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Optional3.class).generate();
		Optional3 o3 = new Optional3("aa".toCharArray(), structure);
		AbstractNode result = o3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(a,A(a)),S(a,O?(O(A(a))))] <- good");
	}
}

