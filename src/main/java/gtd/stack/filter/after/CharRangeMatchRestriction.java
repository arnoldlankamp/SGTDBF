package gtd.stack.filter.after;

import gtd.stack.filter.IAfterFilter;

public class CharRangeMatchRestriction implements IAfterFilter{
	private final char from;
	private final char to;
	
	public CharRangeMatchRestriction(char from, char to){
		super();
		
		this.from = from;
		this.to = to;
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if((end - start) != 1) return false;
		
		return (from <= input[start] && input[start] <= to);
	}
	
	public int hashCode(){
		int hashCode = 17;
		hashCode ^= from << 21 | from >>> 11;
		hashCode ^= to << 17 | to >>> 15;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharMatchRestriction){
			CharRangeMatchRestriction otherCharRangeMatchRestriction = (CharRangeMatchRestriction) other;
			
			return (from == otherCharRangeMatchRestriction.from && to == otherCharRangeMatchRestriction.to);
		}
		return false;
	}
}
