package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class Sort extends AbstractSymbol{
	public final String sortName;
	
	protected Sort(String sortName, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(sortName, beforeFilters, afterFilters);
		
		this.sortName = sortName;
	}
	
	public Sort(String sortName){
		this(sortName, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new Sort(sortName, beforeFilters, afterFilters);
	}

	public int hashCode(){
		return sortName.hashCode();
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Sort){
			Sort otherSort = (Sort) other;
			return sortName.equals(otherSort.sortName) && hasEqualFilters(otherSort);
		}
		return false;
	}
}
