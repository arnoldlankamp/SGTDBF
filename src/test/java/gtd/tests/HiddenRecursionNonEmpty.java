package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

/*
S ::= S T U | epsilon | a
T ::= T U S | epsilon
U ::= U S T | epsilon
*/
public class HiddenRecursionNonEmpty{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("S"), new Sort("T"), new Sort("U")),
			new Alternative(new Epsilon()),
			new Alternative(new Char('a'))
		};
	}
	
	public static Alternative[] T(){
		return new Alternative[]{
			new Alternative(new Sort("T"), new Sort("U"), new Sort("S")),
			new Alternative(new Epsilon())
		};
	}
	
	public static Alternative[] U(){
		return new Alternative[]{
			new Alternative(new Sort("U"), new Sort("S"), new Sort("T")),
			new Alternative(new Epsilon())
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(HiddenRecursionNonEmpty.class).generate();
		Parser hrne = new Parser("a".toCharArray(), structure);
		AbstractNode result = hrne.parse("S");
		System.out.println(result);
		
		System.out.println("[S('a'),S([S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])],[T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])],[U([U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])],[S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])],[T([T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])],[U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])],cycle(S,3)),T([T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])],cycle(U,2),[S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])]),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])],[S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])])]),U([U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])],cycle(S,2),[T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])]),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])],[T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])])]),S([S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])],[T([T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])],[U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])],cycle(S,2)),T([T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])],[U([U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])],[S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])],cycle(T,2)),U([U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])],cycle(S,3),[T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])]),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])],[T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])])],[S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])]),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])],[S(),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),cycle(S,3),cycle(T,2))],cycle(S,2))],[U(),U(cycle(U,1),cycle(S,2),[T(),T(cycle(T,1),cycle(U,2),cycle(S,3))])])])],[U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])]),S(cycle(S,1),[T(),T(cycle(T,1),[U(),U(cycle(U,1),[S(),S(cycle(S,1),cycle(T,3),cycle(U,2))],cycle(T,2))],[S(),S(cycle(S,1),cycle(T,2),[U(),U(cycle(U,1),cycle(S,2),cycle(T,3))])])],[U(),U(cycle(U,1),[S(),S(cycle(S,1),[T(),T(cycle(T,1),cycle(U,3),cycle(S,2))],cycle(U,2))],[T(),T(cycle(T,1),cycle(U,2),[S(),S(cycle(S,1),cycle(T,2),cycle(U,3))])])])] <- good");
	}
}
