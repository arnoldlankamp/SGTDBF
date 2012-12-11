package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= aA | Aa
A ::= BCD
B ::= a | aa
C ::= a
D ::= a | aa
*/
public class BrokenInDepthFirst extends SGTDBF{
	
	public BrokenInDepthFirst(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Literal("a"), new Sort("A")),
			new Alternative(new Sort("A"), new Literal("a"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Sort("C"), new Sort("D"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("a")),
			new Alternative(new Literal("aa"))
		};
	}
	
	public Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] D(){
		return new Alternative[]{
			new Alternative(new Literal("a")),
			new Alternative(new Literal("aa"))
		};
	}
	
	public static void main(String[] args){
		BrokenInDepthFirst bidf = new BrokenInDepthFirst("aaaaa".toCharArray());
		AbstractNode result = bidf.parse("S");
		System.out.println(result);
		
		System.out.println("[S(a,[A(B(aa),C(a),D(a)),A(B(a),C(a),D(aa))]),S([A(B(aa),C(a),D(a)),A(B(a),C(a),D(aa))],a)] <- good");
	}
}
