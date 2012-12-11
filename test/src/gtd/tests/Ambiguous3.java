package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AA
A ::= aa | a
*/
public class Ambiguous3 extends SGTDBF{
	
	public Ambiguous3(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("aa")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		Ambiguous3 a3 = new Ambiguous3("aaa".toCharArray());
		AbstractNode result = a3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(aa),A(a)),S(A(a),A(aa))] <- good");
	}
}
