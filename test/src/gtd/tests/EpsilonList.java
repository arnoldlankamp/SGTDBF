package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= epsilon
*/
public class EpsilonList extends SGTDBF{
	
	public EpsilonList(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		EpsilonList el = new EpsilonList("".toCharArray());
		AbstractNode result = el.parse("S");
		System.out.println(result);
		
		System.out.println("S(A+(repeat(A()))) <- good");
	}
}
