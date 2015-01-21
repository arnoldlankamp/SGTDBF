package gtd.stack.filter.after;

import gtd.stack.filter.IAfterFilter;

public class CharRangePrefixRequirement implements IAfterFilter{
	private final char from;
	private final char to;
	
	public CharRangePrefixRequirement(char from, char to){
		super();
		
		this.from = from;
		this.to = to;
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if(start + 1 > end) return true;
		
		return (from > input[start] || to < input[start]);
	}
	
	public int hashCode(){
		int hashCode = 15;
		hashCode ^= from << 21 | from >>> 11;
		hashCode ^= to << 17 | to >>> 15;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharRangePrefixRequirement){
			CharRangePrefixRequirement otherCharRangePrefixRequirement = (CharRangePrefixRequirement) other;
			
			return (from == otherCharRangePrefixRequirement.from && to == otherCharRangePrefixRequirement.to);
		}
		return false;
	}
}
