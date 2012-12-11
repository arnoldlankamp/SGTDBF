package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalPlusList extends SGTDBF{
	
	public NonTerminalPlusList(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		NonTerminalPlusList nrpl = new NonTerminalPlusList("aaa".toCharArray());
		AbstractNode result = nrpl.parse("S");
		System.out.println(result);
		
		System.out.println("S(A+(A(a),A(a),A(a))) <- good");
	}
}
