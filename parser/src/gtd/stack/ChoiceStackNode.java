package gtd.stack;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class ChoiceStackNode extends AbstractExpandableStackNode{
	private final String nodeName;
	
	private final AbstractStackNode[] children;
	
	public ChoiceStackNode(int id, int containerIndex, boolean isEndNode, AbstractStackNode[] children, String nodeName, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(id, containerIndex, isEndNode, beforeFilters, afterFilters);
		
		this.nodeName = nodeName;
		
		this.children = children;
	}
	
	private ChoiceStackNode(ChoiceStackNode original, int startLocation){
		super(original, startLocation);
		
		nodeName = original.nodeName;
		
		children = original.children;
	}
	
	public String getName(){
		return nodeName;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new ChoiceStackNode(this, startLocation);
	}
	
	public AbstractStackNode[] getChildren(){
		return children;
	}
	
	public boolean canBeEmpty(){
		return false;
	}
	
	public AbstractStackNode getEmptyChild(){
		return null;
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
