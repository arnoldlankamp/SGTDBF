package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2 extends SGTDBF{
	
	public Ambiguous2(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Literal("ab")),
			new Alternative(new Literal("bab"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("b"))
		};
	}
	
	public static void main(String[] args){
		Ambiguous2 a2 = new Ambiguous2("bab".toCharArray());
		AbstractNode result = a2.parse("S");
		System.out.println(result);
		
		System.out.println("[S(bab),S(A(B(b)),ab)] <- good");
	}
}
