package gtd.stack;


public final class SeparatedListStackNode extends AbstractExpandableStackNode{
	private final String nodeName;

	private final AbstractStackNode[] children;
	private final AbstractStackNode emptyChild;
	
	public SeparatedListStackNode(int id, boolean isEndNode, AbstractStackNode child, AbstractStackNode[] separators, String nodeName, boolean isPlusList){
		super(id, isEndNode);
		
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
		AbstractStackNode listNode = child.getCleanCopy(DEFAULT_START_LOCATION);
		
		AbstractStackNode previous = listNode;
		for(int i = 0; i < separators.length; ++i){
			AbstractStackNode separator = separators[i];
			previous.addNext(separator);
			previous = separator;
		}
		previous.addNext(listNode);
		
		return new AbstractStackNode[]{listNode};
	}
	
	private static AbstractStackNode generateEmptyChild(){
		return EMPTY.getCleanCopy(DEFAULT_START_LOCATION);
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
