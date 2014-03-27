package gtd.tests;

import gtd.SGTDBF;
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
		SGTDBF bidf = new SGTDBF("aaaaa".toCharArray(), structure);
		AbstractNode result = bidf.parse("S");
		System.out.println(result);
		
		System.out.println("[S(a,[A(B(aa),C(a),D(a)),A(B(a),C(a),D(aa))]),S([A(B(aa),C(a),D(a)),A(B(a),C(a),D(aa))],a)] <- good");
	}
}
