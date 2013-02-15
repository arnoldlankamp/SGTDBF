package gtd.grammar.structure;

public class Priority implements IStructure{
	public final IStructure higher;
	public final IStructure lower;
	
	public Priority(IStructure higher, IStructure lower){
		super();
		
		this.higher = higher;
		this.lower = lower;
	}
}
