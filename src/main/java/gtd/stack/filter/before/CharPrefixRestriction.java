package gtd.stack.filter.before;

import gtd.stack.filter.IBeforeFilter;

public class CharPrefixRestriction implements IBeforeFilter{
	private final char character;
	
	public CharPrefixRestriction(char character){
		super();
		
		this.character = character;
	}
	

	public boolean isFiltered(char[] input, int start){
		if(start + 1 > input.length) return false;
		
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
