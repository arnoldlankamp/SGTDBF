package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= SSSS | a | epsilon
*/
public class HiddenHiddenRightRecursive extends SGTDBF{
	
	public HiddenHiddenRightRecursive(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("S"), new Sort("S"), new Sort("S"), new Sort("S")),
			new Alternative(new Literal("a")),
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		HiddenHiddenRightRecursive hhrr = new HiddenHiddenRightRecursive("a".toCharArray());
		AbstractNode result = hhrr.parse("S");
		System.out.println(result);
		
		System.out.println("[S([S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()]),S(a)] <- good");
	}
}
