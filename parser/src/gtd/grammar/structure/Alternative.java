package gtd.grammar.structure;

import gtd.grammar.symbols.AbstractSymbol;

public class Alternative extends AbstractSymbol implements IStructure{
	public final AbstractSymbol[] alternative;
	
	public Alternative(AbstractSymbol... alternative){
		super(generateName(alternative));
		
		this.alternative = alternative;
	}
	
	private static String generateName(AbstractSymbol[] symbols){
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append("alt(");
		if(symbols.length > 0){
			nameBuilder.append(symbols[0].name);
			for(int i = 0; i < symbols.length; ++i){
				nameBuilder.append(' ');
				nameBuilder.append(symbols[i].name);
			}
		}
		nameBuilder.append(')');
		return nameBuilder.toString();
	}

	public int hashCode(){
		int hashCode = 5;
		for(int i = alternative.length - 1; i >= 0; --i){
			int symbolHashCode = alternative[i].hashCode();
			hashCode ^= symbolHashCode << 15 | symbolHashCode >>> 17;
		}
		return hashCode;
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Alternative){
			Alternative otherAlternative = (Alternative) other;
			
			int numberOfSymbols = alternative.length;
			if(numberOfSymbols != otherAlternative.alternative.length) return false;
			
			for(int i = numberOfSymbols - 1; i >= 0; --i){
				if(alternative[i] != otherAlternative.alternative[i]) return false;
			}
			
			return true;
		}
		return false;
	}
}
