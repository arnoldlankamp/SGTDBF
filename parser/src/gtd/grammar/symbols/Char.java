package gtd.grammar.symbols;


public class Char extends AbstractSymbol{
	public final char character;
	
	public Char(char character){
		super(String.format("[%c]", Character.valueOf(character)));
		
		this.character = character;
	}

	public int hashCode(){
		return character;
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Char){
			Char otherChar = (Char) other;
			if(character != otherChar.character) return false;
			
			return true;
		}
		return false;
	}
}
