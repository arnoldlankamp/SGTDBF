package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class Epsilon extends AbstractSymbol{
	
	protected Epsilon(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super("", beforeFilters, afterFilters);
	}
	
	public Epsilon(){
		this(null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new Epsilon(beforeFilters, afterFilters);
	}

	public int hashCode(){
		return 0;
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		return (other instanceof Epsilon) && hasEqualFilters((Epsilon) other);
	}
}
