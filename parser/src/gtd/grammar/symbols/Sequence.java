package gtd.grammar.symbols;

public class Sequence extends AbstractSymbol{
	public final AbstractSymbol[] symbols;
	
	public Sequence(AbstractSymbol... symbols){
		super(generateName(symbols));
		
		this.symbols = symbols;
	}
	
	private static String generateName(AbstractSymbol[] symbols){
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append("sequence(");
		nameBuilder.append(symbols[0].name);
		for(int i = 1; i < symbols.length; ++i){
			nameBuilder.append(',');
			nameBuilder.append(symbols[i].name);
		}
		nameBuilder.append(')');
		return nameBuilder.toString();
	}

	public int hashCode(){
		int hashCode = 5;
		for(int i = symbols.length - 1; i >= 0; --i){
			int symbolHashCode = symbols.hashCode();
			hashCode ^= symbolHashCode << 15 | symbolHashCode >>> 17;
		}
		
		return hashCode;
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Sequence){
			Sequence otherSequence = (Sequence) other;
			
			int numberOfSymbols = symbols.length;
			if(numberOfSymbols != otherSequence.symbols.length) return false;
			
			for(int i = otherSequence.symbols.length - 1; i >= 0; --i){
				if(!symbols[i].equals(otherSequence.symbols[i])) return false;
			}
		}
		return true;
	}
}
