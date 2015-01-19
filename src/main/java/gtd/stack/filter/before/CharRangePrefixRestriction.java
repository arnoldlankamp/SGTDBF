package gtd.stack.filter.before;

import gtd.stack.filter.IBeforeFilter;

public class CharRangePrefixRestriction implements IBeforeFilter{
	private final char from;
	private final char to;
	
	public CharRangePrefixRestriction(char from, char to){
		super();
		
		this.from = from;
		this.to = to;
	}
	

	public boolean isFiltered(char[] input, int start){
		if(start + 1 > input.length) return false;
		
		return (from <= input[start] && input[start] <= to);
	}
	
	public int hashCode(){
		int hashCode = 16;
		hashCode ^= from << 21 | from >>> 11;
		hashCode ^= to << 17 | to >>> 15;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharRangePrefixRestriction){
			CharRangePrefixRestriction otherCharRangePrefixRestriction = (CharRangePrefixRestriction) other;
			
			return (from == otherCharRangePrefixRestriction.from && to == otherCharRangePrefixRestriction.to);
		}
		return false;
	}
}
