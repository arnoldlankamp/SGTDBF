package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AA
A ::= aa | a
*/
public class Ambiguous3{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("aa")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous3.class).generate();
		SGTDBF a3 = new SGTDBF("aaa".toCharArray(), structure);
		AbstractNode result = a3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(aa),A(a)),S(A(a),A(aa))] <- good");
	}
}
