package gtd.grammar.symbols;

import gtd.generator.IdentifiedSymbol;
import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class StarList extends AbstractList{
	
	protected StarList(AbstractSymbol symbol, AbstractSymbol[] separators, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(symbol, false, separators, beforeFilters, afterFilters);
	}
	
	public StarList(Char character, AbstractSymbol... separators){
		this(character, separators, null, null);
	}
	
	public StarList(CharRange charRange, AbstractSymbol... separators){
		this(charRange, separators, null, null);
	}
	
	public StarList(Literal literal, AbstractSymbol... separators){
		this(literal, separators, null, null);
	}
	
	public StarList(CILiteral ciLiteral, AbstractSymbol... separators){
		this(ciLiteral, separators, null, null);
	}
	
	public StarList(Sort sort, AbstractSymbol... separators){
		this(sort, separators, null, null);
	}
	
	public StarList(IdentifiedSymbol identifiedSymbol, AbstractSymbol... separators){
		this(identifiedSymbol, separators, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new StarList(symbol, separators, beforeFilters, afterFilters);
	}
}
