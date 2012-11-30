package gtd.grammar.symbols;

public class StarList extends AbstractList{

	public StarList(Char character, AbstractSymbol... separators){
		super(character, false, separators);
	}
	
	public StarList(CharRange charRange, AbstractSymbol... separators){
		super(charRange, false, separators);
	}
	
	public StarList(Literal literal, AbstractSymbol... separators){
		super(literal, false, separators);
	}
	
	public StarList(CILiteral ciLiteral, AbstractSymbol... separators){
		super(ciLiteral, false, separators);
	}
	
	public StarList(Sort sort, AbstractSymbol... separators){
		super(sort, false, separators);
	}
}
