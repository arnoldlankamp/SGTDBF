package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AB | AC
A ::= a
B ::= a
C ::= a

NOTE: This test, tests prefix sharing.
*/
public class Ambiguous8 extends SGTDBF{
	
	public Ambiguous8(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("B")),
			new Alternative(new Sort("A"), new Sort("C"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		Ambiguous8 a8 = new Ambiguous8("aa".toCharArray());
		AbstractNode result = a8.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(a),C(a)),S(A(a),B(a))] <- good");
	}
}
