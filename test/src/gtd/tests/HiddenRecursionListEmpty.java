package gtd.tests;

import gtd.SGTDBF;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.StarList;
import gtd.result.AbstractNode;

/*
S ::= S* T* U*
T ::= T* U* S*
U ::= U* S* T*
*/
public class HiddenRecursionListEmpty extends SGTDBF{

	public HiddenRecursionListEmpty(char[] input, ParserStructure structure){
		super(input, structure);
	}
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("S")), new StarList(new Sort("T")), new StarList(new Sort("U")))
		};
	}
	
	public static Alternative[] T(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("T")), new StarList(new Sort("U")), new StarList(new Sort("S")))
		};
	}
	
	public static Alternative[] U(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("U")), new StarList(new Sort("S")), new StarList(new Sort("T")))
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(HiddenRecursionListEmpty.class).generate();
		HiddenRecursionListEmpty hrle = new HiddenRecursionListEmpty("".toCharArray(), structure);
		AbstractNode result = hrle.parse("S");
		System.out.println(result);
		
		System.out.println("S([S*(repeat(cycle(S,2))),S*()],[T*(repeat(T(cycle(T*,2),[U*(repeat(U(cycle(U*,2),[S*(repeat(cycle(S,6))),S*()],cycle(T*,4)))),U*()],[S*(repeat(cycle(S,4))),S*()]))),T*()],[U*(repeat(U(cycle(U*,2),[S*(repeat(cycle(S,4))),S*()],[T*(repeat(T(cycle(T*,2),cycle(U*,4),[S*(repeat(cycle(S,6))),S*()]))),T*()]))),U*()]) <- good");
	}
}
