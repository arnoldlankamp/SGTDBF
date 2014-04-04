package gtd.stack;

import gtd.result.AbstractNode;
import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public abstract class AbstractMatchableStackNode extends AbstractStackNode{
	
	protected AbstractMatchableStackNode(int id, boolean isEndNode, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(id, isEndNode, beforeFilters, afterFilters);
	}
	
	protected AbstractMatchableStackNode(AbstractMatchableStackNode original, int startLocation){
		super(original, startLocation);
	}
	
	public abstract AbstractNode match(char[] input, int location);
	
	public abstract int getLength();
	
	public String getName(){
		throw new UnsupportedOperationException();
	}
	
	public int getContainerIndex(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public boolean canBeEmpty(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getEmptyChild(){
		throw new UnsupportedOperationException();
	}
}
