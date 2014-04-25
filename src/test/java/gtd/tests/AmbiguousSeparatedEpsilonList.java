package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
* S ::= sep(A, SEP)+
* A ::= a | epsilon
* SEP ::= epsilon
*/
public class AmbiguousSeparatedEpsilonList{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A"), new Sort("SEP")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Char('a')),
			new Alternative(new Epsilon())
		};
	}
	
	public static Alternative[] SEP(){
		return new Alternative[]{
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(AmbiguousSeparatedEpsilonList.class).generate();
		Parser asel = new Parser("a".toCharArray(), structure);
		AbstractNode result = asel.parse("S");
		System.out.println(result);
		
		System.out.println("S([{A, SEP}+(A(),repeat(SEP(),A()),SEP(),A('a')),{A, SEP}+(A('a')),{A, SEP}+([{A, SEP}+(A('a')),{A, SEP}+(A(),repeat(SEP(),A()),SEP(),A('a'))],SEP(),A(),repeat(SEP(),A()))]) <- good, but not minimal");
		//System.out.println("S([(ASEP)+([(ASEP)+(A('a')),(ASEP)+(A(),repeat(SEP(),A()),SEP(),A('a'))],repeat(SEP(),A())),(ASEP)+(A(),repeat(SEP(),A()),SEP(),A('a')),(ASEP)+(A('a'))]) <- good");
	}
}
