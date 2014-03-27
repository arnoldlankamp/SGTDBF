package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Optional;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= aO?
O ::= a
*/
public class Optional1{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Literal("a"), new Optional(new Sort("O")))
		};
	}
	
	public static Alternative[] O(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Optional1.class).generate();
		SGTDBF o1 = new SGTDBF("aa".toCharArray(), structure);
		AbstractNode result = o1.parse("S");
		System.out.println(result);
		
		System.out.println("S(a,O?(O(a))) <- good");
	}
}
