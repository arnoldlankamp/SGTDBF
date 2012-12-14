package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A | a
A ::= a
*/
public class Ambiguous1 extends SGTDBF{
	
	public Ambiguous1(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous1.class).generate();
		Ambiguous1 a1 = new Ambiguous1("a".toCharArray(), structure);
		AbstractNode result = a1.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(a)),S(a)] <- good");
	}
}
