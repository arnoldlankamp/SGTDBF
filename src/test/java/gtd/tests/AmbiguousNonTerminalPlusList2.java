package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= A+
A ::= a | aa
*/
public class AmbiguousNonTerminalPlusList2{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a")),
			new Alternative(new Literal("aa"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(AmbiguousNonTerminalPlusList2.class).generate();
		Parser nrpl2 = new Parser("aaa".toCharArray(), structure);
		AbstractNode result = nrpl2.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+([A+(A(\"a\"),A(\"a\")),A+(A(\"aa\"))],A(\"a\")),A+(A(\"a\"),A(\"aa\"))]) <- good");
	}
}
