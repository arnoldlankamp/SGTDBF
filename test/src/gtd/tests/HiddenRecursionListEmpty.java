package gtd.tests;

import gtd.SGTDBF;
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

	public HiddenRecursionListEmpty(char[] input){
		super(input);
	}
	
	public Alternative[] S(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("S")), new StarList(new Sort("T")), new StarList(new Sort("U")))
		};
	}
	
	public Alternative[] T(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("T")), new StarList(new Sort("U")), new StarList(new Sort("S")))
		};
	}
	
	public Alternative[] U(){
		return new Alternative[]{
			new Alternative(new StarList(new Sort("U")), new StarList(new Sort("S")), new StarList(new Sort("T")))
		};
	}
	
	public static void main(String[] args){
		HiddenRecursionListEmpty hrle = new HiddenRecursionListEmpty("".toCharArray());
		AbstractNode result = hrle.parse("S");
		System.out.println(result);
		
		System.out.println("S([S*(repeat(cycle(S,2))),S*()],[T*(repeat(T(cycle(T*,2),[U*(repeat(U(cycle(U*,2),[S*(repeat(cycle(S,6))),S*()],cycle(T*,4)))),U*()],[S*(repeat(cycle(S,4))),S*()]))),T*()],[U*(repeat(U(cycle(U*,2),[S*(repeat(cycle(S,4))),S*()],[T*(repeat(T(cycle(T*,2),cycle(U*,4),[S*(repeat(cycle(S,6))),S*()]))),T*()]))),U*()]) <- good");
	}
}
