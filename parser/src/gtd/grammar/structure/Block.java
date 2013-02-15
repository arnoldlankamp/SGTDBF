package gtd.grammar.structure;

public class Block implements IStructure{
	public final Alternative[] alternatives;
	
	public Block(Alternative... alternatives){
		super();
		
		this.alternatives = alternatives;
	}
}
