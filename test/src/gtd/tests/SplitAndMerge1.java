package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= aAa
A ::= Ba | aB
B ::= a
*/
public class SplitAndMerge1 extends SGTDBF{
	
	public SplitAndMerge1(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Literal("a"), new Sort("A"), new Literal("a"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Literal("a")),
			new Alternative(new Literal("a"), new Sort("B"))
		};
	}
	
	public Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		SplitAndMerge1 sm1 = new SplitAndMerge1("aaaa".toCharArray());
		AbstractNode result = sm1.parse("S");
		System.out.println(result);
		
		System.out.println("S(a,[A(a,B(a)),A(B(a),a)],a) <- good");
	}
}
