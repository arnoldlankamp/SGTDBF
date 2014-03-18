package gtd.grammar.symbols;

public abstract class AbstractList extends AbstractConstruct{
	public final boolean isPlusList;
	public final AbstractSymbol symbol;
	public final AbstractSymbol[] separators;
	
	protected AbstractList(AbstractSymbol symbol, boolean isPlusList, AbstractSymbol... separators){
		super(generateName(symbol, isPlusList, separators));
		
		this.isPlusList = isPlusList;
		this.symbol = symbol;
		this.separators = separators;
	}
	
	private static String generateName(AbstractSymbol symbol, boolean isPlusList, AbstractSymbol[] separators){
		StringBuilder nameBuilder = new StringBuilder();
		
		if(separators.length == 0){
			nameBuilder.append(symbol.name);
			nameBuilder.append(isPlusList ? '+' : '*');
		}else{
			nameBuilder.append('{');
			nameBuilder.append(symbol.name);
			nameBuilder.append(", ");
			nameBuilder.append(separators[0].name);
			for(int i = 1; i < separators.length; ++i){
				nameBuilder.append(' ');
				nameBuilder.append(separators[i].name);
			}
			nameBuilder.append('}');
			nameBuilder.append(isPlusList ? '+' : '*');
		}
		return nameBuilder.toString();
	}

	public int hashCode(){
		int hashCode = symbol.hashCode();
		for(int i = separators.length - 1; i >= 0; --i){
			int separatorHashCode = separators.hashCode();
			hashCode ^= separatorHashCode << 15 | separatorHashCode >>> 17;
		}
		if(isPlusList) hashCode = hashCode << 13 | hashCode >>> 19;
		
		return hashCode;
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof AbstractList){
			AbstractList otherList = (AbstractList) other;
			if(!symbol.equals(otherList.symbol)) return false;
			
			int numberOfSeparators = separators.length;
			if(numberOfSeparators != otherList.separators.length) return false;
			
			for(int i = otherList.separators.length - 1; i >= 0; --i){
				if(!separators[i].equals(otherList.separators[i])) return false;
			}
			if(isPlusList != otherList.isPlusList) return false;
		}
		return true;
	}
}
