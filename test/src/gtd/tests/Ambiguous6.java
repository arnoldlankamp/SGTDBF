package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A | E
A ::= B
B ::= C
C ::= D
D ::= E | a
E ::= F
F ::= G
G ::= a
*/
public class Ambiguous6{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A")),
			new Alternative(new Sort("E"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Sort("B"))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Sort("C"))
		};
	}
	
	public static Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Sort("D"))
		};
	}
	
	public static Alternative[] D(){
		return new Alternative[]{
			new Alternative(new Sort("E")),
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] E(){
		return new Alternative[]{
			new Alternative(new Sort("F"))
		};
	}
	
	public static Alternative[] F(){
		return new Alternative[]{
			new Alternative(new Sort("G"))
		};
	}
	
	public static Alternative[] G(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Ambiguous6.class).generate();
		Parser a6 = new Parser("a".toCharArray(), structure);
		AbstractNode result = a6.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(B(C([D(E(F(G('a')))),D('a')])))),S(E(F(G('a'))))] <- good");
	}
}
