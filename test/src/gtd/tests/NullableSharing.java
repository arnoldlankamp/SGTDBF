package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
* S ::= N N
* N ::= A
* A ::= epsilon
*/
public class NullableSharing extends SGTDBF{
	
	public NullableSharing(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("N"), new Sort("N"))
		};
	}
	
	public Alternative[] N(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		NullableSharing ns = new NullableSharing("".toCharArray());
		AbstractNode result = ns.parse("S");
		System.out.println(result);
		
		System.out.println("S(N(A()),N(A())) <- good");
	}
}
