package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class Char extends AbstractSymbol{
	public final char character;
	
	protected Char(char character, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(String.format("[%c]", Character.valueOf(character)), beforeFilters, afterFilters);
		
		this.character = character;
	}
	
	public Char(char character){
		this(character, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new Char(character, beforeFilters, afterFilters);
	}

	public int hashCode(){
		return character;
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Char){
			Char otherChar = (Char) other;
			if(character != otherChar.character) return false;
			
			return hasEqualFilters(otherChar);
		}
		return false;
	}
}
