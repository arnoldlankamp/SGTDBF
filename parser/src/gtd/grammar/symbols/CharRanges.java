package gtd.grammar.symbols;

public class CharRanges extends AbstractSymbol{
	public final CharRange[] charRanges;
	
	public CharRanges(CharRange... charRanges){
		super(generateName(charRanges));
		
		this.charRanges = charRanges;
	}
	
	private static String generateName(CharRange[] charRanges){
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append("[");
		if(charRanges.length > 0){
			CharRange charRange = charRanges[0];
			nameBuilder.append(charRange.from);
			nameBuilder.append('-');
			nameBuilder.append(charRange.to);
			for(int i = 0; i < charRanges.length; ++i){
				charRange = charRanges[i];
				nameBuilder.append(' ');
				nameBuilder.append(charRange.from);
				nameBuilder.append('-');
				nameBuilder.append(charRange.to);
			}
		}
		nameBuilder.append(']');
		return nameBuilder.toString();
	}
}
