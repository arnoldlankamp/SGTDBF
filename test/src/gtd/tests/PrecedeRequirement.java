package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;
import gtd.stack.filter.before.StringPrecedeRequirement;

public class PrecedeRequirement{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("A"), new Sort("B").withBeforeFilters(new StringPrecedeRequirement("b"))),
			new Alternative(new Sort("C"), new Sort("B"))
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
	
	public static Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Char('a'))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(PrecedeRequirement.class).generate();
		Parser parser = new Parser("ab".toCharArray(), structure);
		AbstractNode result = parser.parse("S");
		System.out.println(result);
		
		System.out.println("S(C('a'),B('b')) <- good");
	}
}
