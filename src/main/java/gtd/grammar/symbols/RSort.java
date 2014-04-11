package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class RSort extends Sort{
	
	protected RSort(String sortName, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(sortName, beforeFilters, afterFilters);
	}
	
	public RSort(String sortName){
		this(sortName, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new RSort(sortName, beforeFilters, afterFilters);
	}

	public int hashCode(){
		return sortName.hashCode();
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof RSort){
			RSort otherRSort = (RSort) other;
			return sortName.equals(otherRSort.sortName) && hasEqualFilters(otherRSort);
		}
		return false;
	}
}
