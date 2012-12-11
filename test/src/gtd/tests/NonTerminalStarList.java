package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.StarList;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalStarList extends SGTDBF{
	
	public NonTerminalStarList(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("A")))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		NonTerminalStarList nrsl = new NonTerminalStarList("aaa".toCharArray());
		AbstractNode result = nrsl.parse("S");
		System.out.println(result);
		
		System.out.println("S(A*(A(a),A(a),A(a))) <- good");
	}
}
