package gtd.grammar.structure;

import gtd.grammar.symbols.AbstractSymbol;

public class Sequence extends AbstractSymbol implements IStructure{
	public final AbstractSymbol[] symbols;
	
	public Sequence(AbstractSymbol... symbols){
		super(generateName(symbols));
		
		this.symbols = symbols;
	}
	
	private static String generateName(AbstractSymbol[] symbols){
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append("seq(");
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
		int hashCode = 3;
		for(int i = symbols.length - 1; i >= 0; --i){
			int symbolHashCode = symbols[i].hashCode();
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
			
			for(int i = numberOfSymbols - 1; i >= 0; --i){
				if(symbols[i] != otherSequence.symbols[i]) return false;
			}
			
			return true;
		}
		return false;
	}
}
