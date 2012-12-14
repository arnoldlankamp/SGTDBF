package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= a | aa
*/
public class AmbiguousNonTerminalPlusList2 extends SGTDBF{
	
	public AmbiguousNonTerminalPlusList2(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a")),
			new Alternative(new Literal("aa"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(AmbiguousNonTerminalPlusList2.class).generate();
		AmbiguousNonTerminalPlusList2 nrpl2 = new AmbiguousNonTerminalPlusList2("aaa".toCharArray(), structure);
		AbstractNode result = nrpl2.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+([A+(A(a),A(a)),A+(A(aa))],A(a)),A+(A(a),A(aa))]) <- good");
	}
}
