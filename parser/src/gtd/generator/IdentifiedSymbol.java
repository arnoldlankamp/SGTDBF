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
	
	protected static class Key{
		public final AbstractSymbol symbol;
		public final int scopeId;
		public final boolean isRestricted;
		
		public Key(AbstractSymbol symbol, int scopeId, boolean isRestricted){
			super();
			
			this.symbol = symbol;
			this.scopeId = scopeId;
			this.isRestricted = isRestricted;
		}
		
		public int hashCode(){
			return symbol.hashCode() + scopeId << 24 ^ (isRestricted ? 0x80000000 : 0);
		}
		
		public boolean equals(Object other){
			if(other == this) return true;
			if(other == null) return false;
			
			if(other instanceof Key){
				Key otherKey = (Key) other;
				return symbol.equals(otherKey.symbol) && scopeId == otherKey.scopeId && isRestricted == otherKey.isRestricted;
			}
			return false;
		}
		
		public String toString(){
			StringBuilder buffer = new StringBuilder();
			buffer.append(symbol);
			buffer.append(':');
			buffer.append(scopeId);
			buffer.append('-');
			buffer.append(isRestricted);
			return buffer.toString();
		}
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
