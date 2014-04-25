package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AA
A ::= BB
B ::= bb | b
*/
public class Ambiguous4{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Sort("B"))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("bb")),
			new Alternative(new Literal("b"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous4.class).generate();
		Parser a4 = new Parser("bbbbbb".toCharArray(), structure);
		AbstractNode result = a4.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(B(\"b\"),B(\"b\")),A(B(\"bb\"),B(\"bb\"))),S([A(B(\"b\"),B(\"bb\")),A(B(\"bb\"),B(\"b\"))],[A(B(\"b\"),B(\"bb\")),A(B(\"bb\"),B(\"b\"))]),S(A(B(\"bb\"),B(\"bb\")),A(B(\"b\"),B(\"b\")))] <- good");
	}
}
