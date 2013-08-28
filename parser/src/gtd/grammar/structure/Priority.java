package gtd.grammar.structure;
/*
A(1) < A(2) | a
A(1) -> A(1), A(2), a
A(2) -> A(2), a
*/
public class Priority implements IStructure{
	public final IStructure[] higher;
	public final IStructure[] lower;
	
	public Priority(IStructure[] higher, IStructure... lower){
		super();
		
		this.higher = higher;
		this.lower = lower;
	}
	
	// TODO Enforce with types, instead of checking it
	public static void validate(IStructure[] higher){
		for(int i = higher.length - 1; i >= 0; --i){
			if(higher[i] instanceof Priority) throw new IllegalArgumentException("Can't nest priority structure in the 'higher' priority block");
		}
	}
}
