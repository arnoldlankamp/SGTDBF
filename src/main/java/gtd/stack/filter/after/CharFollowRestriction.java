package gtd.stack.filter.after;

import gtd.stack.filter.IAfterFilter;

public class CharFollowRestriction implements IAfterFilter{
	private final char character;
	
	public CharFollowRestriction(char character){
		super();
		
		this.character = character;
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if(end >= input.length) return false;
		
		return input[end] == character;
	}
	
	public int hashCode(){
		int hashCode = 11;
		hashCode ^= character << 21 | character >>> 11;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharFollowRestriction){
			CharFollowRestriction otherCharFollowRestriction = (CharFollowRestriction) other;
			
			return character == otherCharFollowRestriction.character;
		}
		return false;
	}
}
