package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= D | Da
D ::= C
C ::= Baa | Ba
B ::= A
A ::= a
*/
public class SplitAndMerge2 extends SGTDBF{
	
	public SplitAndMerge2(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("D")),
			new Alternative(new Sort("D"), new Literal("a"))
		};
	}
	
	public Alternative[] D(){
		return new Alternative[]{
			new Alternative(new Sort("C"))
		};
	}
	
	public Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Literal("aa")),
			new Alternative(new Sort("B"), new Literal("a"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		SplitAndMerge2 sm2 = new SplitAndMerge2("aaa".toCharArray());
		AbstractNode result = sm2.parse("S");
		System.out.println(result);
		
		System.out.println("[S(D(C(B(A(a)),aa))),S(D(C(B(A(a)),a)),a)] <- good");
	}
}
