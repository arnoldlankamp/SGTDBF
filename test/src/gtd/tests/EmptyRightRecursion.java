package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AB
A ::= a
B ::= B | epsilon
*/
public class EmptyRightRecursion extends SGTDBF{
	
	public EmptyRightRecursion(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("B"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("B")),
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		EmptyRightRecursion erre = new EmptyRightRecursion("a".toCharArray());
		AbstractNode result = erre.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(a),[B(cycle(B,1)),B()]) <- good");
	}
}
