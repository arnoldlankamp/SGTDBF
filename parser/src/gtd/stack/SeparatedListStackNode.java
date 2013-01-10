package gtd.stack;


public final class SeparatedListStackNode extends AbstractExpandableStackNode{
	private final String nodeName;

	private final AbstractStackNode[] children;
	private final AbstractStackNode emptyChild;
	
	public SeparatedListStackNode(int id, int containerIndex, boolean isEndNode, AbstractStackNode child, AbstractStackNode[] separators, String nodeName, boolean isPlusList){
		super(id, containerIndex, isEndNode);
		
		this.nodeName = nodeName;
		
		this.children = generateChildren(child, separators);
		this.emptyChild = isPlusList ? null : generateEmptyChild();
	}
	
	private SeparatedListStackNode(SeparatedListStackNode original, int startLocation){
		super(original, startLocation);
		
		nodeName = original.nodeName;

		children = original.children;
		emptyChild = original.emptyChild;
	}
	
	private static AbstractStackNode[] generateChildren(AbstractStackNode child,  AbstractStackNode[] separators){
		AbstractStackNode previous = child;
		for(int i = 0; i < separators.length; ++i){
			AbstractStackNode separator = separators[i];
			previous.addNext(separator);
			previous = separator;
		}
		previous.addNext(child);
		
		return new AbstractStackNode[]{child};
	}
	
	private static AbstractStackNode generateEmptyChild(){
		return EMPTY;
	}
	
	public String getName(){
		return nodeName;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new SeparatedListStackNode(this, startLocation);
	}
	
	public AbstractStackNode[] getChildren(){
		return children;
	}
	
	public boolean canBeEmpty(){
		return (emptyChild != null);
	}
	
	public AbstractStackNode getEmptyChild(){
		return emptyChild;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(nodeName);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
