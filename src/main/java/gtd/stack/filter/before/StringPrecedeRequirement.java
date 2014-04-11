package gtd.stack.filter.before;

import gtd.stack.filter.IBeforeFilter;

public class StringPrecedeRequirement implements IBeforeFilter{
	private final char[] string;
	
	public StringPrecedeRequirement(String string){
		super();
		
		this.string = string.toCharArray();
	}
	
	public boolean isFiltered(char[] input, int start){
		int startLocation = start - string.length;
		if(startLocation < 0) return true;
		
		for(int i = string.length - 1; i >= 0; --i){
			if(input[startLocation + i] != string[i]) return true;
		}
		
		return false;
	}
	
	public int hashCode(){
		int hashCode = 2;
		for(int i = string.length - 1; i >= 0; --i){
			hashCode = hashCode << 21 | hashCode >>> 11;
			hashCode ^= string[i];
		}
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof StringPrecedeRequirement){
			StringPrecedeRequirement otherStringPrecedeRequirement = (StringPrecedeRequirement) other;
			
			char[] otherString = otherStringPrecedeRequirement.string;
			if(string.length != otherString.length) return false;
			
			for(int i = string.length - 1; i >= 0; --i){
				if(string[i] != otherString[i]) return false;
			}
			
			return true;
		}
		return false;
	}
}
