package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A | B
A ::= B | a
B ::= A | a
*/
public class UselessSelfLoop extends SGTDBF{
	
	public UselessSelfLoop(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A")),
			new Alternative(new Sort("B"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B")),
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("A")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		UselessSelfLoop usl = new UselessSelfLoop("a".toCharArray());
		AbstractNode result = usl.parse("S");
		System.out.println(result);
		
		System.out.println("[S([B([A(cycle(B,2)),A(a)]),B(a)]),S([A([B(cycle(A,2)),B(a)]),A(a)])] <- good");
	}
}
