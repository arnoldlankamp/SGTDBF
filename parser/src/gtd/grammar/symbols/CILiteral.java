package gtd.grammar.symbols;

public class CILiteral extends AbstractSymbol{
	public final String literal;

	public CILiteral(String literal){
		super(String.format("cilit(%s)", literal));
		
		this.literal = literal;
	}

	public int hashCode(){
		return literal.hashCode();
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof CILiteral){
			CILiteral otherLiteral = (CILiteral) other;
			if(!literal.equals(otherLiteral.literal)) return false;
			
			return true;
		}
		return false;
	}
}
