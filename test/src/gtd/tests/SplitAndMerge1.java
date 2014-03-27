package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= aAa
A ::= Ba | aB
B ::= a
*/
public class SplitAndMerge1{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Literal("a"), new Sort("A"), new Literal("a"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Literal("a")),
			new Alternative(new Literal("a"), new Sort("B"))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(SplitAndMerge1.class).generate();
		SGTDBF sm1 = new SGTDBF("aaaa".toCharArray(), structure);
		AbstractNode result = sm1.parse("S");
		System.out.println(result);
		
		System.out.println("S(a,[A(a,B(a)),A(B(a),a)],a) <- good");
	}
}
