package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class StarList extends AbstractList{
	
	protected StarList(AbstractSymbol symbol, AbstractSymbol[] separators, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(symbol, false, separators, beforeFilters, afterFilters);
	}
	
	public StarList(AbstractSymbol symbol, AbstractSymbol... separators){
		this(symbol, separators, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new StarList(symbol, separators, beforeFilters, afterFilters);
	}
}
