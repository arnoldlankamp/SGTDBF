package gtd.stack.filter.after;

import gtd.stack.filter.IAfterFilter;

public class CharFollowRequirement implements IAfterFilter{
	private final char character;
	
	public CharFollowRequirement(char character){
		super();
		
		this.character = character;
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if(end >= input.length) return true;
		
		return input[end] != character;
	}
	
	public int hashCode(){
		int hashCode = 8;
		hashCode ^= character << 21 | character >>> 11;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharFollowRequirement){
			CharFollowRequirement otherCharFollowRequirement = (CharFollowRequirement) other;
			
			return character == otherCharFollowRequirement.character;
		}
		return false;
	}
}
