package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A
A ::= AA | a
*/
public class Ambiguous7 extends SGTDBF{
	
	public Ambiguous7(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		Ambiguous7 a7 = new Ambiguous7("aaaa".toCharArray());
		AbstractNode result = a7.parse("S");
		System.out.println(result);
		
		System.out.println("S([A(A(A(a),A(a)),A(A(a),A(a))),A(A(a),[A(A(a),A(A(a),A(a))),A(A(A(a),A(a)),A(a))]),A([A(A(a),A(A(a),A(a))),A(A(A(a),A(a)),A(a))],A(a))]) <- good");
	}
}
