package gtd.generator;

import gtd.grammar.symbols.AbstractSymbol;

public class IdentifiedSymbol extends AbstractSymbol{
	public final AbstractSymbol symbol;
	public final int id;
	public final boolean restricted;

	public IdentifiedSymbol(AbstractSymbol symbol, int id, boolean restricted) {
		super(symbol.name);
		
		this.symbol = symbol;
		this.id = id;
		this.restricted = restricted;
	}
	
	public int hashCode(){
		return id;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof IdentifiedSymbol){
			IdentifiedSymbol otherIdentifiedSymbol = (IdentifiedSymbol) other;
			if(id != otherIdentifiedSymbol.id) return false;
			
			return true;
		}
		return false;
	}
}
