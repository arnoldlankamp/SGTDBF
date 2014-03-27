package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.StarList;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalStarList{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("A")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(NonTerminalStarList.class).generate();
		SGTDBF nrsl = new SGTDBF("aaa".toCharArray(), structure);
		AbstractNode result = nrsl.parse("S");
		System.out.println(result);
		
		System.out.println("S(A*(A(a),A(a),A(a))) <- good");
	}
}
