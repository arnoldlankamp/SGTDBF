package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AB
A ::= a
B ::= b
*/
public class Simple2{
	
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
			new Alternative(new Char('b'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Simple2.class).generate();
		Parser s2 = new Parser("ab".toCharArray(), structure);
		AbstractNode result = s2.parse("S");
		System.out.println(result);
		
		System.out.println("S(A('a'),B('b')) <- good");
	}
}
