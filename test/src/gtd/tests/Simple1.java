package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= Ab
A ::= aa
*/
public class Simple1 extends SGTDBF{
	
	public Simple1(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Literal("b"))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("aa"))
		};
	}
	
	public static void main(String[] args){
		Simple1 s1 = new Simple1("aab".toCharArray());
		AbstractNode result = s1.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(aa),b) <- good");
	}
}
