package gtd.stack.filter.before;

public class CharPrefixRequirement {
private final char character;
	
	public CharPrefixRequirement(char character){
		super();
		
		this.character = character;
	}
	

	public boolean isFiltered(char[] input, int start){
		if(start + 1 > input.length) return true;
		
		return (input[start] != character);
	}
	
	public int hashCode(){
		int hashCode = 14;
		hashCode ^= character << 21 | character >>> 11;
		return hashCode;
	}
	
	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharPrefixRequirement){
			CharPrefixRequirement otherCharPrefixRequirement = (CharPrefixRequirement) other;
			
			return (character == otherCharPrefixRequirement.character);
		}
		return false;
	}
}
