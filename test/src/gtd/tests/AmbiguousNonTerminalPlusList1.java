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
S ::= aA+ | A+a
A ::= a
*/
public class AmbiguousNonTerminalPlusList1 extends SGTDBF{
	
	public AmbiguousNonTerminalPlusList1(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Literal("a"), new PlusList(new Sort("A"))),
			new Alternative(new PlusList(new Sort("A")), new Literal("a"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(AmbiguousNonTerminalPlusList1.class).generate();
		AmbiguousNonTerminalPlusList1 nrpl1 = new AmbiguousNonTerminalPlusList1("aaa".toCharArray(), structure);
		AbstractNode result = nrpl1.parse("S");
		System.out.println(result);
		
		System.out.println("[S(a,A+(A(a),A(a))),S(A+(A(a),A(a)),a)] <- good");
	}
}
