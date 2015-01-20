package gtd.stack.filter.after;

import gtd.stack.filter.IAfterFilter;

public class CharPrefixRestriction implements IAfterFilter{
	private final char character;
	
	public CharPrefixRestriction(char character){
		super();
		
		this.character = character;
	}
	

	public boolean isFiltered(char[] input, int start, int end){
		if(start + 1 > end) return false;
		
		return (input[start] == character);
	}
	
	public int hashCode(){
		int hashCode = 13;
		hashCode ^= character << 21 | character >>> 11;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharPrefixRestriction){
			CharPrefixRestriction otherCharPrefixRestriction = (CharPrefixRestriction) other;
			
			return (character == otherCharPrefixRestriction.character);
		}
		return false;
	}
}
