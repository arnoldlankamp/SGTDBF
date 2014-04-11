package gtd.stack.filter.after;

import gtd.stack.filter.IAfterFilter;

public class CharRangeFollowRequirement implements IAfterFilter{
	private final char from;
	private final char to;
	
	public CharRangeFollowRequirement(char from, char to){
		super();
		
		this.from = from;
		this.to = to;
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if(end >= input.length) return true;
		
		return (from > input[end] || to < input[end]);
	}
	
	public int hashCode(){
		int hashCode = 7;
		hashCode ^= from << 21 | from >>> 11;
		hashCode ^= to << 17 | to >>> 15;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharRangeFollowRequirement){
			CharRangeFollowRequirement otherCharRangeFollowRequirement = (CharRangeFollowRequirement) other;
			
			return (from == otherCharRangeFollowRequirement.from && to == otherCharRangeFollowRequirement.to);
		}
		return false;
	}
}
