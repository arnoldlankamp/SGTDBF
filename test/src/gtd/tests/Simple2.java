package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AB
A ::= a
B ::= b
*/
public class Simple2 extends SGTDBF{
	
	public Simple2(char[] input){
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
			new Alternative(new Literal("b"))
		};
	}
	
	public static void main(String[] args){
		Simple2 s2 = new Simple2("ab".toCharArray());
		AbstractNode result = s2.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(a),B(b)) <- good");
	}
}
