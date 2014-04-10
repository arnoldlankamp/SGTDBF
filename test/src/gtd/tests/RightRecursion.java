package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A
A ::= aA | a
*/
public class RightRecursion{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Char('a'), new Sort("A")),
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(RightRecursion.class).generate();
		Parser rr = new Parser("aaa".toCharArray(), structure);
		AbstractNode result = rr.parse("S");
		System.out.println(result);
		
		System.out.println("S(A('a',A('a',A('a')))) <- good");
	}
}
