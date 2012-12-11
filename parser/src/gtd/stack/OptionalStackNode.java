package gtd.stack;


public final class OptionalStackNode extends AbstractExpandableStackNode{
	private final String nodeName;
	
	private final AbstractStackNode[] children;
	private final AbstractStackNode emptyChild;
	
	public OptionalStackNode(int id, boolean isEndNode, AbstractStackNode optional, String nodeName){
		super(id, isEndNode);
		
		this.nodeName = nodeName;
		
		this.children = generateChildren(optional);
		this.emptyChild = generateEmptyChild();
	}
	
	private OptionalStackNode(OptionalStackNode original, int startLocation){
		super(original, startLocation);
		
		nodeName = original.nodeName;
		
		children = original.children;
		emptyChild = original.emptyChild;
	}
	
	private static AbstractStackNode[] generateChildren(AbstractStackNode optional){
		AbstractStackNode child = optional.getCleanCopy(DEFAULT_START_LOCATION);
		
		return new AbstractStackNode[]{child};
	}
	
	private static AbstractStackNode generateEmptyChild(){
		return EMPTY.getCleanCopy(DEFAULT_START_LOCATION);
	}
	
	public String getName(){
		return nodeName;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new OptionalStackNode(this, startLocation);
	}
	
	public AbstractStackNode[] getChildren(){
		return children;
	}
	
	public boolean canBeEmpty(){
		return true;
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
