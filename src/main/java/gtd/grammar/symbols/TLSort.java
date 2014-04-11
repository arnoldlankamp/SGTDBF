package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

// Does not have it's own equals function, since we want to to be able to be equal to 'plain' sorts.
public class TLSort extends Sort{
	
	protected TLSort(String sortName, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(sortName, beforeFilters, afterFilters);
	}
	
	public TLSort(String sortName){
		super(sortName);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new TLSort(sortName, beforeFilters, afterFilters);
	}
}
