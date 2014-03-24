package gtd.grammar.symbols;

public abstract class AbstractSymbol{
	public final String name;
	
	public AbstractSymbol(String name){
		super();
		
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
