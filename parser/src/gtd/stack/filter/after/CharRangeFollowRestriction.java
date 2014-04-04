package gtd.stack.filter.after;

import gtd.stack.filter.IAfterFilter;

public class CharRangeFollowRestriction implements IAfterFilter{
	private final char from;
	private final char to;
	
	public CharRangeFollowRestriction(char from, char to){
		super();
		
		this.from = from;
		this.to = to;
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if(end >= input.length) return false;
		
		return (from <= input[end] && input[end] <= to);
	}
	
	public int hashCode(){
		int hashCode = 6;
		hashCode ^= from << 21 | from >>> 11;
		hashCode ^= to << 17 | to >>> 15;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharRangeFollowRestriction){
			CharRangeFollowRestriction otherCharRangeFollowRestriction = (CharRangeFollowRestriction) other;
			
			return (from == otherCharRangeFollowRestriction.from && to == otherCharRangeFollowRestriction.to);
		}
		return false;
	}
}
