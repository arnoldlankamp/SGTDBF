package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A
A ::= AA | a | epsilon
*/
public class CycleEpsilon extends SGTDBF{
	
	public CycleEpsilon(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Literal("a")),
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		CycleEpsilon ce = new CycleEpsilon("a".toCharArray());
		AbstractNode result = ce.parse("S");
		System.out.println(result);
		
		System.out.println("S([A([A(cycle(A,1),cycle(A,1)),A()],cycle(A,1)),A(cycle(A,1),[A(cycle(A,1),cycle(A,1)),A()]),A(a)]) <- good");
	}
}
