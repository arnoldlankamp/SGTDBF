package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class Optional extends AbstractConstruct{
	public final AbstractSymbol symbol;
	
	protected Optional(AbstractSymbol symbol, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(String.format("%s?", symbol.name), beforeFilters, afterFilters);
		
		this.symbol = symbol;
	}
	
	public Optional(AbstractSymbol symbol){
		this(symbol, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new Optional(symbol, beforeFilters, afterFilters);
	}

	public int hashCode(){
		int hashCode = symbol.hashCode();
		return (hashCode << 13 | hashCode >>> 19);
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Optional){
			Optional otherOptional = (Optional) other;
			if(!symbol.equals(otherOptional.symbol)) return false;
			
			return hasEqualFilters(otherOptional);
		}
		return false;
	}
}
