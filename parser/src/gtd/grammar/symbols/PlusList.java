package gtd.grammar.symbols;

import gtd.generator.IdentifiedSymbol;
import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class PlusList extends AbstractList{
	
	protected PlusList(AbstractSymbol symbol, AbstractSymbol[] separators, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(symbol, true, separators, beforeFilters, afterFilters);
	}
	
	public PlusList(Char character, AbstractSymbol... separators){
		this(character, separators, null, null);
	}
	
	public PlusList(CharRange charRange, AbstractSymbol... separators){
		this(charRange, separators, null, null);
	}
	
	public PlusList(Literal literal, AbstractSymbol... separators){
		this(literal, separators, null, null);
	}
	
	public PlusList(CILiteral ciLiteral, AbstractSymbol... separators){
		this(ciLiteral, separators, null, null);
	}
	
	public PlusList(Sort sort, AbstractSymbol... separators){
		this(sort, separators, null, null);
	}
	
	public PlusList(IdentifiedSymbol identifiedSymbol, AbstractSymbol... separators){
		this(identifiedSymbol, separators, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new PlusList(symbol, separators, beforeFilters, afterFilters);
	}
}
