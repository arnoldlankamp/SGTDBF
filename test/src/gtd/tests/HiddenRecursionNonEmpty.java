package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Literal;
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
			new Alternative(new Literal("a"))
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
		SGTDBF hrne = new SGTDBF("a".toCharArray(), structure);
		AbstractNode result = hrne.parse("S");
		System.out.println(result);
		
		System.out.println("[S([S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],[T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],[U([U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],cycle(S,2),[T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()]),U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],[T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()]),U([U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],[T([T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],cycle(U,2),[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()]),T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()]),T([T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],[U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],cycle(S,3))])]),S([S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],[T([T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],[U([U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],cycle(S,3),[T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()]),U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],[T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()]),U([U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],cycle(T,2))],[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()]),T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()]),T([T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],[U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],cycle(S,2))],[U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()]),S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],[U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()]),S(a)] <- good");
	}
}
