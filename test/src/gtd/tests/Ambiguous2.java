package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Literal("ab")),
			new Alternative(new Literal("bab"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("b"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous2.class).generate();
		Parser a2 = new Parser("bab".toCharArray(), structure);
		AbstractNode result = a2.parse("S");
		System.out.println(result);
		
		System.out.println("[S(\"bab\"),S(A(B(\"b\")),\"ab\")] <- good");
	}
}
