package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
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
			new Alternative(new Char('a'), new Sort("A"), new Char('a'))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Char('a')),
			new Alternative(new Char('a'), new Sort("B"))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(SplitAndMerge1.class).generate();
		Parser sm1 = new Parser("aaaa".toCharArray(), structure);
		AbstractNode result = sm1.parse("S");
		System.out.println(result);
		
		System.out.println("S('a',[A(B('a'),'a'),A('a',B('a'))],'a') <- good");
	}
}
