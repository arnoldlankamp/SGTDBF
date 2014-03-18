package gtd.grammar.structure;

public class Scope implements IStructure{
	public final IStructure[] alternatives;
	
	public Scope(IStructure[] alternatives){
		super();
		
		this.alternatives = alternatives;
	}
}
