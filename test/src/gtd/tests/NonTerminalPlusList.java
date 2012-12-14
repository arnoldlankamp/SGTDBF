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
A ::= a
*/
public class NonTerminalPlusList extends SGTDBF{
	
	public NonTerminalPlusList(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(NonTerminalPlusList.class).generate();
		NonTerminalPlusList nrpl = new NonTerminalPlusList("aaa".toCharArray(), structure);
		AbstractNode result = nrpl.parse("S");
		System.out.println(result);
		
		System.out.println("S(A+(A(a),A(a),A(a))) <- good");
	}
}
