package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AAB
A ::= epsilon
B ::= a
*/
public class Epsilon extends SGTDBF{
	
	public Epsilon(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A"), new Sort("B"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new gtd.grammar.symbols.Epsilon())
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Epsilon.class).generate();
		Epsilon e = new Epsilon(new char[]{'a'}, structure);
		AbstractNode result = e.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(),A(),B(a)) <- good");
	}
}
