package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= SSS | SS | a
*/
public class AmbiguousRecursive extends SGTDBF{
	
	public AmbiguousRecursive(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("S"), new Sort("S"), new Sort("S")),
			new Alternative(new Sort("S"), new Sort("S")),
			new Alternative(new Literal("a"))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(AmbiguousRecursive.class).generate();
		AmbiguousRecursive ar = new AmbiguousRecursive("aaa".toCharArray(), structure);
		AbstractNode result = ar.parse("S");
		System.out.println(result);
		
		System.out.println("[S(S(a),S(S(a),S(a))),S(S(a),S(a),S(a)),S(S(S(a),S(a)),S(a))] <- good"); // Temp
	}
}
