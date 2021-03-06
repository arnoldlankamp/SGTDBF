package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Choice;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class Choice1{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Choice(new Sort("A")))
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
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Choice1.class).generate();
		Parser c1 = new Parser("a".toCharArray(), structure);
		AbstractNode result = c1.parse("S");
		System.out.println(result);
		
		System.out.println("S(choice(A)(A('a'))) <- good");
	}
}
