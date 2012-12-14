package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.result.AbstractNode;

/*
S ::= ci(bla)

NOTE: ci(*) means whatever * represents is Case Insensitive.
*/
public class CILiteral extends SGTDBF{
	
	public CILiteral(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new gtd.grammar.symbols.CILiteral("bla"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(CILiteral.class).generate();
		CILiteral cil = new CILiteral("Bla".toCharArray(), structure);
		AbstractNode result = cil.parse("S");
		System.out.println(result);
		
		System.out.println("S(Bla) <- good");
	}
}
