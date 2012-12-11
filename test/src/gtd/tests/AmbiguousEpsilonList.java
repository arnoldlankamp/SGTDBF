package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.Epsilon;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= a | epsilon
*/
public class AmbiguousEpsilonList extends SGTDBF{
	
	public AmbiguousEpsilonList(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Char('a')),
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		AmbiguousEpsilonList ael = new AmbiguousEpsilonList("a".toCharArray());
		AbstractNode result = ael.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+([A+(A([a](a))),A+(repeat(A()),A([a](a)))],repeat(A())),A+(repeat(A()),A([a](a))),A+(A([a](a)))]) <- good");
	}
}
