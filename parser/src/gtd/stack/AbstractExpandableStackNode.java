package gtd.stack;

import gtd.result.AbstractNode;

public abstract class AbstractExpandableStackNode extends AbstractStackNode{
	public final static int DEFAULT_LIST_EPSILON_ID = -2;
	public final static EpsilonStackNode EMPTY = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID, true);
	
	protected AbstractExpandableStackNode(int id, boolean isEndNode){
		super(id, isEndNode);
	}
	
	protected AbstractExpandableStackNode(AbstractExpandableStackNode original, int startLocation){
		super(original, startLocation);
	}
	
	public abstract AbstractStackNode[] getChildren();
	
	public abstract boolean canBeEmpty();
	
	public abstract AbstractStackNode getEmptyChild();
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public int getNonterminalIndex(){
		throw new UnsupportedOperationException();
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
