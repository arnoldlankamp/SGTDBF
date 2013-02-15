package gtd.grammar.structure;

public class Associative{
	public final IStructure structure;
	public final Kind kind;
	
	public Associative(IStructure structure, Kind kind){
		super();
		
		this.structure = structure;
		this.kind = kind;
	}
	
	public enum Kind{
		LEFT,
		RIGHT,
		NON
	}
}
