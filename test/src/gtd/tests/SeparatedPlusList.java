package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= sep(A, b)+
A ::= a

sep(X, Y) means, a list of X separated by Y's.
*/
public class SeparatedPlusList{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new PlusList(new Sort("A"), new Literal("b")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(SeparatedPlusList.class).generate();
		SGTDBF nrpl = new SGTDBF("ababa".toCharArray(), structure);
		AbstractNode result = nrpl.parse("S");
		System.out.println(result);
		
		System.out.println("S({A, lit(b)}+(A(a),b,A(a),b,A(a))) <- good");
	}
}
