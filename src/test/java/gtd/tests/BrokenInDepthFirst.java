package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= aA | Aa
A ::= BCD
B ::= a | aa
C ::= a
D ::= a | aa
*/
public class BrokenInDepthFirst{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Literal("a"), new Sort("A")),
			new Alternative(new Sort("A"), new Literal("a"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Sort("C"), new Sort("D"))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("a")),
			new Alternative(new Literal("aa"))
		};
	}
	
	public static Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static Alternative[] D(){
		return new Alternative[]{
			new Alternative(new Literal("a")),
			new Alternative(new Literal("aa"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(BrokenInDepthFirst.class).generate();
		Parser bidf = new Parser("aaaaa".toCharArray(), structure);
		AbstractNode result = bidf.parse("S");
		System.out.println(result);
		
		System.out.println("[S([A(B(\"a\"),C(\"a\"),D(\"aa\")),A(B(\"aa\"),C(\"a\"),D(\"a\"))],\"a\"),S(\"a\",[A(B(\"a\"),C(\"a\"),D(\"aa\")),A(B(\"aa\"),C(\"a\"),D(\"a\"))])] <- good");
	}
}
