package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class CharRanges extends AbstractSymbol{
	public final CharRange[] charRanges;
	
	protected CharRanges(CharRange[] charRanges, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(generateName(charRanges), beforeFilters, afterFilters);
		
		this.charRanges = charRanges;
	}
	
	public CharRanges(CharRange... charRanges){
		this(charRanges, null, null);
	}
	
	protected AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		return new CharRanges(charRanges, beforeFilters, afterFilters);
	}
	
	private static String generateName(CharRange[] charRanges){
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append("[");
		if(charRanges.length > 0){
			CharRange charRange = charRanges[0];
			nameBuilder.append(charRange.from);
			nameBuilder.append('-');
			nameBuilder.append(charRange.to);
			for(int i = 1; i < charRanges.length; ++i){
				charRange = charRanges[i];
				nameBuilder.append(' ');
				nameBuilder.append(charRange.from);
				nameBuilder.append('-');
				nameBuilder.append(charRange.to);
			}
		}
		nameBuilder.append(']');
		return nameBuilder.toString();
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharRanges){
			CharRanges otherCharRanges = (CharRanges) other;
			if(charRanges.length != otherCharRanges.charRanges.length) return false;
			
			for(int i = charRanges.length - 1; i >= 0; --i){
				if(!charRanges[i].equals(otherCharRanges.charRanges[i])) return false;
			}
			
			return hasEqualFilters(otherCharRanges);
		}
		return false;
	}
}
