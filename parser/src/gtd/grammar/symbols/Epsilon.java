package gtd.grammar.symbols;


public class Epsilon extends AbstractSymbol{
	
	public Epsilon(){
		super("");
	}

	public int hashCode(){
		return 0;
	}

	public boolean equals(Object other){
		if(other == this) return true;
		if(other == null) return false;
		
		return (other instanceof Epsilon);
	}
}
