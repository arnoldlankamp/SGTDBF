package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public abstract class AbstractConstruct extends AbstractSymbol{
	
	protected AbstractConstruct(String name, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(name, beforeFilters, afterFilters);
	}
}
