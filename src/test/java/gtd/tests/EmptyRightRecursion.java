package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AB
A ::= a
B ::= B | epsilon
*/
public class EmptyRightRecursion{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("B"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("B")),
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(EmptyRightRecursion.class).generate();
		Parser erre = new Parser("a".toCharArray(), structure);
		AbstractNode result = erre.parse("S");
		System.out.println(result);
		
		System.out.println("S(A('a'),[B(),B(cycle(B,1))]) <- good");
	}
}
