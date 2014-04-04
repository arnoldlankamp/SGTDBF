package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class CILiteral extends AbstractSymbol{
	public final String literal;

	protected CILiteral(String literal, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(String.format("cilit(%s)", literal), beforeFilters, afterFilters);
		
		this.literal = literal;
	}
	
	public CILiteral(String literal){
		this(literal, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new CILiteral(literal, beforeFilters, afterFilters);
	}

	public int hashCode(){
		return literal.hashCode();
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CILiteral){
			CILiteral otherLiteral = (CILiteral) other;
			if(!literal.equals(otherLiteral.literal)) return false;
			
			return hasEqualFilters(otherLiteral);
		}
		return false;
	}
}
