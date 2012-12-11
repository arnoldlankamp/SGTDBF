package gtd.tests;

import gtd.SGTDBF;
import gtd.grammar.structure.Alternative;
import gtd.result.AbstractNode;

/*
S ::= ci(bla)

NOTE: ci(*) means whatever * represents is Case Insensitive.
*/
public class CILiteral extends SGTDBF{
	
	public CILiteral(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new gtd.grammar.symbols.CILiteral("bla"))
		};
	}
	
	public static void main(String[] args){
		CILiteral cil = new CILiteral("Bla".toCharArray());
		AbstractNode result = cil.parse("S");
		System.out.println(result);
		
		System.out.println("S(Bla) <- good");
	}
}
