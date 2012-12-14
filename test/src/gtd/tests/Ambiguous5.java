package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A
A ::= BB
B ::= aa | a
*/
public class Ambiguous5 extends SGTDBF{
	
	public Ambiguous5(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Sort("B"))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("aa")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous5.class).generate();
		Ambiguous5 a5 = new Ambiguous5("aaa".toCharArray(), structure);
		AbstractNode result = a5.parse("S");
		System.out.println(result);
		
		System.out.println("S([A(B(aa),B(a)),A(B(a),B(aa))]) <- good");
	}
}
