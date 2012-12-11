package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Optional;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= aO?
O ::= a
*/
public class Optional1 extends SGTDBF{
	
	public Optional1(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Literal("a"), new Optional(new Sort("O")))
		};
	}
	
	public Alternative[] O(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		Optional1 o1 = new Optional1("aa".toCharArray());
		AbstractNode result = o1.parse("S");
		System.out.println(result);
		
		System.out.println("S(a,O?(O(a))) <- good");
	}
}
