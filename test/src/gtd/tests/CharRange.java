package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.result.AbstractNode;

/*
S ::= [a-z]
*/
public class CharRange extends SGTDBF{
	
	public CharRange(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new gtd.grammar.symbols.CharRange('a', 'z'))
		};
	}
	
	public static void main(String[] args){
		CharRange cr = new CharRange("a".toCharArray());
		AbstractNode result = cr.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z](a)) <- good");
	}
}
