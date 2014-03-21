package gtd.grammar.symbols;

public class TLSort extends Sort{
	
	public TLSort(String sortName){
		super(sortName);
	}

	public int hashCode(){
		return sortName.hashCode();
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof TLSort){
			TLSort otherRSort = (TLSort) other;
			if(!sortName.equals(otherRSort.sortName)) return false;
			
			return true;
		}
		return false;
	}
}
