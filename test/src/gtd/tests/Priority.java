package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.IStructure;
import gtd.grammar.structure.Scope;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class Priority{
	
	public static IStructure[] S(){
		return new IStructure[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static IStructure[] A(){
		return new IStructure[]{
			new Alternative(new Sort("A"), new Literal("+"), new Sort("A")),
			new Scope(
				new Alternative(new Sort("A"), new Literal("*"), new Sort("A")),
				new Alternative(new Literal("a"))
			)
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Priority.class).generate();
		Parser p = new Parser("a+a*a".toCharArray(), structure);
		AbstractNode result = p.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(A(a),+,A(A(a),*,A(a)))) <- good");
	}
}
