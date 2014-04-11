package gtd.generator;

import gtd.grammar.symbols.AbstractSymbol;
import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class IdentifiedSymbol extends AbstractSymbol{
	public final AbstractSymbol symbol;
	public final int id;
	public final boolean restricted;

	public IdentifiedSymbol(AbstractSymbol symbol, int id, boolean restricted) {
		super(symbol.name, null, null);
		
		this.symbol = symbol;
		this.id = id;
		this.restricted = restricted;
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		throw new UnsupportedOperationException("Identified symbols cannot be cloned");
	}
	
	protected static class Key{
		public final String symbolName;
		public final int scopeId;
		public final boolean isRestricted;
		
		public Key(String symbolName, int scopeId, boolean isRestricted){
			super();
			
			this.symbolName = symbolName;
			this.scopeId = scopeId;
			this.isRestricted = isRestricted;
		}
		
		public int hashCode(){
			return symbolName.hashCode() + scopeId << 24 ^ (isRestricted ? 0x80000000 : 0);
		}
		
		public boolean equals(Object other){
			if(other == this) return true;
			if(other == null) return false;
			
			if(other instanceof Key){
				Key otherKey = (Key) other;
				return symbolName.equals(otherKey.symbolName) && scopeId == otherKey.scopeId && isRestricted == otherKey.isRestricted;
			}
			return false;
		}
		
		public String toString(){
			StringBuilder buffer = new StringBuilder();
			buffer.append(symbolName);
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
