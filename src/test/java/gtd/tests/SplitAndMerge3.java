package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A | C
A ::= Ba | a
B ::= Aa | a
C ::= B
*/
public class SplitAndMerge3{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A")),
			new Alternative(new Sort("C"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"), new Char('a')),
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Char('a')),
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Sort("B"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(SplitAndMerge3.class).generate();
		Parser sm3 = new Parser("aaa".toCharArray(), structure);
		AbstractNode result = sm3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(C(B(A(B('a'),'a'),'a'))),S(A(B(A('a'),'a'),'a'))] <- good");
	}
}
