package gtd.grammar.symbols;

public class Optional extends AbstractConstruct{
	public final AbstractSymbol symbol;
	
	public Optional(AbstractSymbol symbol){
		super(String.format("%s?", symbol.name));
		
		this.symbol = symbol;
	}

	public int hashCode(){
		int hashCode = symbol.hashCode();
		return (hashCode << 13 | hashCode >>> 19);
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Optional){
			Optional otherOptional = (Optional) other;
			if(!symbol.equals(otherOptional.symbol)) return false;
			
			return true;
		}
		return false;
	}
}
