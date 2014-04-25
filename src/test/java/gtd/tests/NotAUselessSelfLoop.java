package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= AA | B
A ::= CC | a
B ::= AA | CC
C ::= AA | a
*/
public class NotAUselessSelfLoop{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Sort("B"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("C"), new Sort("C")),
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Sort("C"), new Sort("C"))
		};
	}
	
	public static Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(NotAUselessSelfLoop.class).generate();
		Parser nausl = new Parser("aaa".toCharArray(), structure);
		AbstractNode result = nausl.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A('a'),A(C('a'),C('a'))),S([B(A('a'),A(C('a'),C('a'))),B(C(A('a'),A('a')),C('a')),B(A(C('a'),C('a')),A('a')),B(C('a'),C(A('a'),A('a')))]),S(A(C('a'),C('a')),A('a'))] <- good");
	}
}
