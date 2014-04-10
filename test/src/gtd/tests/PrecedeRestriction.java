package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;
import gtd.stack.filter.before.StringPrecedeRestriction;

public class PrecedeRestriction{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("A")),
			new Alternative(new Sort("B"), new Sort("B").withBeforeFilters(new StringPrecedeRestriction("a")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(PrecedeRestriction.class).generate();
		Parser parser = new Parser("aa".toCharArray(), structure);
		AbstractNode result = parser.parse("S");
		System.out.println(result);
		
		System.out.println("S(A('a'),A('a')) <- good");
	}
}
