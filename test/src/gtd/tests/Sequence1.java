package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sequence;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class Sequence1{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sequence(new Sort("A"), new Sort("B"), new Sort("C")))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Literal("a"))
		};
	}
	
	public static Alternative[] B(){
		return new Alternative[]{
			new Alternative(new Literal("b"))
		};
	}
	
	public static Alternative[] C(){
		return new Alternative[]{
			new Alternative(new Literal("c"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(Sequence1.class).generate();
		Parser seq1 = new Parser("abc".toCharArray(), structure);
		AbstractNode result = seq1.parse("S");
		System.out.println(result);
		
		System.out.println("S(sequence(A,B,C)(A(a),B(b),C(c))) <- good");
	}
}
