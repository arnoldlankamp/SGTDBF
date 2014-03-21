package gtd.grammar.symbols;

public class RSort extends Sort{
	
	public RSort(String sortName){
		super(sortName);
	}

	public int hashCode(){
		return sortName.hashCode();
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof RSort){
			RSort otherRSort = (RSort) other;
			if(!sortName.equals(otherRSort.sortName)) return false;
			
			return true;
		}
		return false;
	}
}
