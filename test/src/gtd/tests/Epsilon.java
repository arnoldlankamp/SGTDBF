package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AAB
A ::= epsilon
B ::= a
*/
public class Epsilon extends SGTDBF{
	
	public Epsilon(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A"), new Sort("B"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new gtd.grammar.symbols.Epsilon())
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		Epsilon e = new Epsilon(new char[]{'a'});
		AbstractNode result = e.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(),A(),B(a)) <- good");
	}
}
