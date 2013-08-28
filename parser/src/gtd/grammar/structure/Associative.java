package gtd.grammar.structure;
/*
left: { A(1) + A(2), A(3) - A(4) } | a
A(1), A(3) -> A(1), A(3), a
A(2), A(4) -> a

right: { A(1) + A(2), A(3) - A(4) } | a
A(1), A(3) -> a
A(2), A(4) -> A(1), A(3), a

non: { A(1) + A(2), A(3) - A(4) } | a
A(1), A(3), A(2), A(4) -> a*/
public class Associative{
	public final Kind kind;
	public final IStructure[] structure;
	
	public Associative(Kind kind, IStructure... structure){
		super();
		
		this.kind = kind;
		this.structure = structure;
	}
	
	public enum Kind{
		LEFT,
		RIGHT,
		NON
	}
}
