package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A | a
A ::= a
*/
public class Ambiguous1 extends SGTDBF{
	
	public Ambiguous1(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A")),
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		Ambiguous1 a1 = new Ambiguous1("a".toCharArray());
		AbstractNode result = a1.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(a)),S(a)] <- good");
	}
}
