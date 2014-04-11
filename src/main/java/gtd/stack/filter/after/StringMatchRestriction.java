package gtd.stack.filter.after;

import gtd.stack.filter.IAfterFilter;

public class StringMatchRestriction implements IAfterFilter{
	private final char[] string;
	
	public StringMatchRestriction(String string){
		super();
		
		this.string = string.toCharArray();
	}
	
	public boolean isFiltered(char[] input, int start, int end){
		if((end - start) != string.length) return false;
		
		for(int i = string.length - 1; i >= 0; --i){
			if(input[start + i] != string[i]) return false;
		}
		
		return true;
	}
	
	public int hashCode(){
		int hashCode = 5;
		for(int i = string.length - 1; i >= 0; --i){
			hashCode = hashCode << 21 | hashCode >>> 11;
			hashCode ^= string[i];
		}
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof StringMatchRestriction){
			StringMatchRestriction otherStringMatchRestriction = (StringMatchRestriction) other;
			
			char[] otherString = otherStringMatchRestriction.string;
			if(string.length != otherString.length) return false;
			
			for(int i = string.length - 1; i >= 0; --i){
				if(string[i] != otherString[i]) return false;
			}
			
			return true;
		}
		return false;
	}
}
