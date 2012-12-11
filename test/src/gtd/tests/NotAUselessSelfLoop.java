package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AA | B
A ::= CC | a
B ::= AA | CC
C ::= AA | a
*/
public class NotAUselessSelfLoop extends SGTDBF{
	
	public NotAUselessSelfLoop(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Sort("B"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("C"), new Sort("C")),
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Sort("C"), new Sort("C"))
		};
	}
	
	public Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		NotAUselessSelfLoop nausl = new NotAUselessSelfLoop("aaa".toCharArray());
		AbstractNode result = nausl.parse("S");
		System.out.println(result);
		
		System.out.println("[S([B(C(A(a),A(a)),C(a)),B(A(C(a),C(a)),A(a)),B(C(a),C(A(a),A(a))),B(A(a),A(C(a),C(a)))]),S(A(C(a),C(a)),A(a)),S(A(a),A(C(a),C(a)))] <- good");
	}
}
