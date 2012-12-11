package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A
A ::= aA | a
*/
public class RightRecursion extends SGTDBF{
	
	public RightRecursion(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"), new Sort("A")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		RightRecursion rr = new RightRecursion("aaa".toCharArray());
		AbstractNode result = rr.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(a,A(a,A(a)))) <- good");
	}
}
