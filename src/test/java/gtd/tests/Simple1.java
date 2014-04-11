package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= Ab
A ::= aa
*/
public class Simple1{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Literal("b"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("aa"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Simple1.class).generate();
		Parser s1 = new Parser("aab".toCharArray(), structure);
		AbstractNode result = s1.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(\"aa\"),\"b\") <- good");
	}
}
