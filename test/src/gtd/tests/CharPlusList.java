package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.PlusList;
import gtd.result.AbstractNode;

/*
S ::= [a-z]+
*/
public class CharPlusList extends SGTDBF{
	
	public CharPlusList(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new CharRange('a', 'z')))
		};
	}
	
	public static void main(String[] args){
		CharPlusList cpl = new CharPlusList("abc".toCharArray());
		AbstractNode result = cpl.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z]+([a-z](a),[a-z](b),[a-z](c))) <- good");
	}
}
