package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= [a]+
*/
public class AmbiguousNestedPlusList extends SGTDBF{
	
	public AmbiguousNestedPlusList(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public Alternative[] A(){
		return new Alternative[]{
			new Alternative(new PlusList(new Char('a')))
		};
	}
	
	public static void main(String[] args){
		AmbiguousNestedPlusList anpl = new AmbiguousNestedPlusList("aa".toCharArray());
		AbstractNode result = anpl.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+(A([a]+([a](a))),A([a]+([a](a)))),A+(A([a]+([a](a),[a](a))))]) <- good");
	}
}
