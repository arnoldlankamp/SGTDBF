package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= a | aa
*/
public class AmbiguousNonTerminalPlusList2 extends SGTDBF{
	
	public AmbiguousNonTerminalPlusList2(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a")),
			new Alternative(new Literal("aa"))
		};
	}
	
	public static void main(String[] args){
		AmbiguousNonTerminalPlusList2 nrpl2 = new AmbiguousNonTerminalPlusList2("aaa".toCharArray());
		AbstractNode result = nrpl2.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+([A+(A(a),A(a)),A+(A(aa))],A(a)),A+(A(a),A(aa))]) <- good");
	}
}
