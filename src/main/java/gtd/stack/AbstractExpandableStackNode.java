package gtd.stack;

import gtd.result.AbstractNode;
import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public abstract class AbstractExpandableStackNode extends AbstractStackNode{
	public final static int DEFAULT_LIST_EPSILON_ID = -2;
	public final static EpsilonStackNode EMPTY = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID, true, null, null);
	
	private final int containerIndex;
	
	protected AbstractExpandableStackNode(int id, int containerIndex, boolean isEndNode, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(id, isEndNode, beforeFilters, afterFilters);
		
		this.containerIndex = containerIndex;
	}
	
	protected AbstractExpandableStackNode(AbstractExpandableStackNode original, int startLocation){
		super(original, startLocation);
		
		containerIndex = original.containerIndex;
	}
	
	public abstract AbstractStackNode[] getChildren();
	
	public abstract boolean canBeEmpty();
	
	public abstract AbstractStackNode getEmptyChild();
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public int getContainerIndex(){
		return containerIndex;
	}
	
	public AbstractNode match(char[] input, int location){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result){
		throw new UnsupportedOperationException();
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractNode getResult(){
		throw new UnsupportedOperationException();
	}
}
