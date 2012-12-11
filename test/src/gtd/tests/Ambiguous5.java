package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A
A ::= BB
B ::= aa | a
*/
public class Ambiguous5 extends SGTDBF{
	
	public Ambiguous5(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Sort("B"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("aa")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		Ambiguous5 a5 = new Ambiguous5("aaa".toCharArray());
		AbstractNode result = a5.parse("S");
		System.out.println(result);
		
		System.out.println("S([A(B(aa),B(a)),A(B(a),B(aa))]) <- good");
	}
}
