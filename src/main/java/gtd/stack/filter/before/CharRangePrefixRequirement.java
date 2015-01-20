package gtd.stack.filter.before;

import gtd.stack.filter.IBeforeFilter;

public class CharRangePrefixRequirement implements IBeforeFilter{
	private final char from;
	private final char to;
	
	public CharRangePrefixRequirement(char from, char to){
		super();
		
		this.from = from;
		this.to = to;
	}
	
	public boolean isFiltered(char[] input, int start){
		if(start + 1 > input.length) return true;
		
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