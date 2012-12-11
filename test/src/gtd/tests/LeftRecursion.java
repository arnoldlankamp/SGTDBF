package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A
A ::= Aa | a
*/
public class LeftRecursion extends SGTDBF{
	
	public LeftRecursion(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Literal("a")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		LeftRecursion lr = new LeftRecursion("aaa".toCharArray());
		AbstractNode result = lr.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(A(A(a),a),a)) <- good");
	}
}
