package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.StarList;
import gtd.result.AbstractNode;

/*
* S ::= A*B*A*
* A ::= a
* B ::= b
*/
public class ListOverlap extends SGTDBF{
	
	public ListOverlap(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("A")), new StarList(new Sort("B")), new StarList(new Sort("A")))
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
		ListOverlap lo = new ListOverlap("aab".toCharArray());
		AbstractNode result = lo.parse("S");
		System.out.println(result);
		
		System.out.println("S(A*(A(a),A(a)),B*(B(b)),A*()) <- good");
	}
}
