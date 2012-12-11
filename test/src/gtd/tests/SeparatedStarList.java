package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.StarList;
import gtd.result.AbstractNode;

/*
S ::= sep(A, b)*
A ::= a

sep(X, Y) means, a list of X separated by Y's.
*/
public class SeparatedStarList extends SGTDBF{
	
	public SeparatedStarList(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("A"), new Literal("b")))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		SeparatedStarList nrsl = new SeparatedStarList("ababa".toCharArray());
		AbstractNode result = nrsl.parse("S");
		System.out.println(result);
		
		System.out.println("S({A, lit(b)}*(A(a),b,A(a),b,A(a))) <- good");
	}
}
