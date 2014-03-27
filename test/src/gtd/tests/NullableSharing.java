package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
* S ::= N N
* N ::= A
* A ::= epsilon
*/
public class NullableSharing{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("N"), new Sort("N"))
		};
	}
	
	public static Alternative[] N(){
		return new Alternative[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static Alternative[] A(){
		return new Alternative[]{
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(NullableSharing.class).generate();
		SGTDBF ns = new SGTDBF("".toCharArray(), structure);
		AbstractNode result = ns.parse("S");
		System.out.println(result);
		
		System.out.println("S(N(A()),N(A())) <- good");
	}
}
