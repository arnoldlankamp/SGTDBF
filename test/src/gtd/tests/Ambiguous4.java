package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AA
A ::= BB
B ::= bb | b
*/
public class Ambiguous4 extends SGTDBF{
	
	public Ambiguous4(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Sort("B"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("bb")),
			new Alternative(new Literal("b"))
		};
	}
	
	public static void main(String[] args){
		Ambiguous4 a4 = new Ambiguous4("bbbbbb".toCharArray());
		AbstractNode result = a4.parse("S");
		System.out.println(result);
		
		System.out.println("[S([A(B(bb),B(b)),A(B(b),B(bb))],[A(B(bb),B(b)),A(B(b),B(bb))]),S(A(B(bb),B(bb)),A(B(b),B(b))),S(A(B(b),B(b)),A(B(bb),B(bb)))] <- good");
	}
}
