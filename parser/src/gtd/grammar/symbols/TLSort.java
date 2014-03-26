package gtd.grammar.symbols;

// Does not have it's own equals function, since we want to to be able to be equal to 'plain' sorts.
public class TLSort extends Sort{
	
	public TLSort(String sortName){
		super(sortName);
	}
}
