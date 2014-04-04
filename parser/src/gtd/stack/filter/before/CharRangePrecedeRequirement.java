package gtd.stack.filter.before;

import gtd.stack.filter.IBeforeFilter;

public class CharRangePrecedeRequirement implements IBeforeFilter{
	private final char from;
	private final char to;
	
	public CharRangePrecedeRequirement(char from, char to){
		super();
		
		this.from = from;
		this.to = to;
	}
	
	public boolean isFiltered(char[] input, int start){
		int location = start - 1;
		if(location < 0) return true;
		
		return (from > input[location] || to < input[location]);
	}
	
	public int hashCode(){
		int hashCode = 8;
		hashCode ^= from << 21 | from >>> 11;
		hashCode ^= to << 17 | to >>> 15;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharRangePrecedeRequirement){
			CharRangePrecedeRequirement otherCharRangePrecedeRequirement = (CharRangePrecedeRequirement) other;
			
			return (from == otherCharRangePrecedeRequirement.from && to == otherCharRangePrecedeRequirement.to);
		}
		return false;
	}
}
