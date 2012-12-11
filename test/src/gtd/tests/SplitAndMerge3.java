package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A | C
A ::= Ba | a
B ::= Aa | a
C ::= B
*/
public class SplitAndMerge3 extends SGTDBF{
	
	public SplitAndMerge3(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A")),
			new Alternative(new Sort("C"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Literal("a")),
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Literal("a")),
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Sort("B"))
		};
	}
	
	public static void main(String[] args){
		SplitAndMerge3 sm3 = new SplitAndMerge3("aaa".toCharArray());
		AbstractNode result = sm3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(C(B(A(B(a),a),a))),S(A(B(A(a),a),a))] <- good");
	}
}
