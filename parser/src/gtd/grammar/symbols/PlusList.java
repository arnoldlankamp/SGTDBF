package gtd.grammar.symbols;

import gtd.generator.IdentifiedSymbol;

public class PlusList extends AbstractList{
	
	public PlusList(Char character, AbstractSymbol... separators){
		super(character, true, separators);
	}
	
	public PlusList(CharRange charRange, AbstractSymbol... separators){
		super(charRange, true, separators);
	}
	
	public PlusList(Literal literal, AbstractSymbol... separators){
		super(literal, true, separators);
	}
	
	public PlusList(CILiteral ciLiteral, AbstractSymbol... separators){
		super(ciLiteral, true, separators);
	}
	
	public PlusList(Sort sort, AbstractSymbol... separators){
		super(sort, true, separators);
	}
	
	public PlusList(IdentifiedSymbol identifiedSymbol, AbstractSymbol... separators){
		super(identifiedSymbol, true, separators);
	}
}
