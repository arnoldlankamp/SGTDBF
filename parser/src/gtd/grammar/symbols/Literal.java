package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class Literal extends AbstractSymbol{
	public final String literal;
	
	protected Literal(String literal, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(String.format("lit(%s)", literal), beforeFilters, afterFilters);
		
		this.literal = literal;
	}
	
	public Literal(String literal){
		this(literal, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new Literal(literal, beforeFilters, afterFilters);
	}

	public int hashCode(){
		return literal.hashCode();
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Literal){
			Literal otherLiteral = (Literal) other;
			if(!literal.equals(otherLiteral.literal)) return false;
			
			return hasEqualFilters(otherLiteral);
		}
		return false;
	}
}
