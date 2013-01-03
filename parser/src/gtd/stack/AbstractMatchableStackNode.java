package gtd.stack;

import gtd.result.AbstractNode;

public abstract class AbstractMatchableStackNode extends AbstractStackNode{
	
	protected AbstractMatchableStackNode(int id, boolean isEndNode){
		super(id, isEndNode);
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
