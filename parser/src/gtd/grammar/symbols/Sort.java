package gtd.grammar.symbols;

public class Sort extends AbstractSymbol{
	public final String sortName;
	
	public Sort(String sortName){
		super(sortName);
		
		this.sortName = sortName;
	}

	public int hashCode(){
		return sortName.hashCode();
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		if(other instanceof Sort){
			Sort otherSort = (Sort) other;
			if(!sortName.equals(otherSort.sortName)) return false;
			
			return true;
		}
		return false;
	}
}
