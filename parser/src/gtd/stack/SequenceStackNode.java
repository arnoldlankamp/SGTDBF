package gtd.stack;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public class SequenceStackNode extends AbstractExpandableStackNode{
	private final String nodeName;
	
	private final AbstractStackNode[] children;
	
	public SequenceStackNode(int id, int containerIndex, boolean isEndNode, AbstractStackNode[] nodes, String nodeName, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(id, containerIndex, isEndNode, beforeFilters, afterFilters);
		
		this.nodeName = nodeName;
		
		this.children = generateChildren(nodes);
	}
	
	private SequenceStackNode(SequenceStackNode original, int startLocation){
		super(original, startLocation);
		
		nodeName = original.nodeName;
		
		children = original.children;
	}
	
	private static AbstractStackNode[] generateChildren(AbstractStackNode[] nodes){
		AbstractStackNode[] children = new AbstractStackNode[nodes.length];
		children[0] = nodes[0];
		
		for(int i = 1; i < children.length; ++i){
			children[i] = nodes[i];
			children[i - 1].addNext(children[i]);
		}
		
		return children;
	}
	
	public String getName(){
		return nodeName;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new SequenceStackNode(this, startLocation);
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
