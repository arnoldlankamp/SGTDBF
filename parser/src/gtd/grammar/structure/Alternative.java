package gtd.grammar.structure;

import gtd.grammar.symbols.AbstractSymbol;

public class Alternative{
	public final AbstractSymbol[] alternative;
	
	public Alternative(AbstractSymbol... alternative){
		super();
		
		this.alternative = alternative;
	}
}
