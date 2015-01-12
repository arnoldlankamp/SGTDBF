package gtd.stack.filter.after;

import gtd.stack.filter.IAfterFilter;

public class CharMatchRestriction implements IAfterFilter{
	private final char character;
	
	public CharMatchRestriction(char character){
		super();
		
		this.character = character;
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if((end - start) != 1) return false;
		
		return input[start] == character;
	}
	
	public int hashCode(){
		int hashCode = 10;
		hashCode ^= character << 21 | character >>> 11;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharMatchRestriction){
			CharMatchRestriction otherCharMatchRestriction = (CharMatchRestriction) other;
			
			return character == otherCharMatchRestriction.character;
		}
		return false;
	}
}
