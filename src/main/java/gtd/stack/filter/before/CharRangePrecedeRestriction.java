package gtd.stack.filter.before;

import gtd.stack.filter.IBeforeFilter;

public class CharRangePrecedeRestriction implements IBeforeFilter{
	private final char from;
	private final char to;
	
	public CharRangePrecedeRestriction(char from, char to){
		super();
		
		this.from = from;
		this.to = to;
	}
	

	public boolean isFiltered(char[] input, int start){
		int location = start - 1;
		if(location < 0) return false;
		
		return (from <= input[location] && input[location] <= to);
	}
	
	public int hashCode(){
		int hashCode = 9;
		hashCode ^= from << 21 | from >>> 11;
		hashCode ^= to << 17 | to >>> 15;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharRangePrecedeRestriction){
			CharRangePrecedeRestriction otherCharRangePrecedeRestriction = (CharRangePrecedeRestriction) other;
			
			return (from == otherCharRangePrecedeRestriction.from && to == otherCharRangePrecedeRestriction.to);
		}
		return false;
	}
}
