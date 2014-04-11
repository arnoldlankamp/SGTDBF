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
S ::= A
A ::= AA | a | epsilon
*/
public class CycleEpsilon{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Char('a')),
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(CycleEpsilon.class).generate();
		Parser ce = new Parser("a".toCharArray(), structure);
		AbstractNode result = ce.parse("S");
		System.out.println(result);
		
		System.out.println("S([A([A(cycle(A,1),cycle(A,1)),A()],cycle(A,1)),A(cycle(A,1),[A(cycle(A,1),cycle(A,1)),A()]),A('a')]) <- good");
	}
}
