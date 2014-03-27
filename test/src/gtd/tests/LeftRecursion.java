package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A
A ::= Aa | a
*/
public class LeftRecursion{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Literal("a")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(LeftRecursion.class).generate();
		Parser lr = new Parser("aaa".toCharArray(), structure);
		AbstractNode result = lr.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(A(A(a),a),a)) <- good");
	}
}
