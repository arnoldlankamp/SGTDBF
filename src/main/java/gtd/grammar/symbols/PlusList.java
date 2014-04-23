package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class PlusList extends AbstractList{
	
	protected PlusList(AbstractSymbol symbol, AbstractSymbol[] separators, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(symbol, true, separators, beforeFilters, afterFilters);
	}
	
	public PlusList(AbstractSymbol symbol, AbstractSymbol... separators){
		this(symbol, separators, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new PlusList(symbol, separators, beforeFilters, afterFilters);
	}
}
