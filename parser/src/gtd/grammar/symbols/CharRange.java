package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class CharRange extends AbstractSymbol{
	public final char from;
	public final char to;
	
	protected CharRange(char from, char to, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(String.format("[%c-%c]", Character.valueOf(from), Character.valueOf(to)), beforeFilters, afterFilters);
		
		this.from = from;
		this.to = to;
	}
	
	public CharRange(char from, char to){
		this(from, to, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new CharRange(from, to, beforeFilters, afterFilters);
	}

	public int hashCode(){
		return (from ^ to);
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharRange){
			CharRange otherCharRange = (CharRange) other;
			if(from != otherCharRange.from) return false;
			if(to != otherCharRange.to) return false;
			
			return hasEqualFilters(otherCharRange);
		}
		return false;
	}
}
