package gtd.grammar.symbols;


public class Literal extends AbstractSymbol{
	public final String literal;
	
	public Literal(String literal){
		super(String.format("lit(%s)", literal));
		
		this.literal = literal;
	}

	public int hashCode(){
		return literal.hashCode();
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Literal){
			Literal otherLiteral = (Literal) other;
			if(!literal.equals(otherLiteral.literal)) return false;
			
			return true;
		}
		return false;
	}
}
