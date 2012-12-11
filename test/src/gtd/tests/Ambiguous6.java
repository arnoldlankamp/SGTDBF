package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A | E
A ::= B
B ::= C
C ::= D
D ::= E | a
E ::= F
F ::= G
G ::= a
*/
public class Ambiguous6 extends SGTDBF{
	
	public Ambiguous6(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A")),
			new Alternative(new Sort("E"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("C"))
		};
	}
	
	public Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Sort("D"))
		};
	}
	
	public Alternative[] D(){
		return new Alternative[]{
			new Alternative(new Sort("E")),
			new Alternative(new Literal("a"))
		};
	}
	
	public Alternative[] E(){
		return new Alternative[]{
			new Alternative(new Sort("F"))
		};
	}
	
	public Alternative[] F(){
		return new Alternative[]{
			new Alternative(new Sort("G"))
		};
	}
	
	public Alternative[] G(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		Ambiguous6 a6 = new Ambiguous6("a".toCharArray());
		AbstractNode result = a6.parse("S");
		System.out.println(result);
		
		System.out.println("[S(E(F(G(a)))),S(A(B(C([D(E(F(G(a)))),D(a)]))))] <- good");
	}
}
