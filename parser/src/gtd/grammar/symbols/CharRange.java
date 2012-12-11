package gtd.grammar.symbols;


public class CharRange extends AbstractSymbol{
	public final char from;
	public final char to;
	
	public CharRange(char from, char to){
		super(String.format("[%c-%c]", Character.valueOf(from), Character.valueOf(to)));
		
		this.from = from;
		this.to = to;
	}

	public int hashCode(){
		return (from ^ to);
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CharRange){
			CharRange otherCharRange = (CharRange) other;
			if(from != otherCharRange.from) return false;
			if(to != otherCharRange.to) return false;
			
			return true;
		}
		return false;
	}
}
