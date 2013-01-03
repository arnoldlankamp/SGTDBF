package gtd.stack;

import gtd.result.AbstractNode;

public final class SortStackNode extends AbstractStackNode{
	private final int containerIndex;
	private final String sortName;
	
	public SortStackNode(int id, int containerIndex, boolean isEndNode, String sortName){
		super(id, isEndNode);
		
		this.containerIndex = containerIndex;
		this.sortName = sortName;
	}
	
	private SortStackNode(SortStackNode original, int startLocation){
		super(original, startLocation);
		
		containerIndex = original.containerIndex;
		sortName = original.sortName;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public int getContainerIndex(){
		return containerIndex;
	}
	
	public String getName(){
		return sortName;
	}
	
	public AbstractNode match(char[] input, int location){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new SortStackNode(this, startLocation);
	}
	
	public AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result){
		throw new UnsupportedOperationException();
	}
	
	public int getLength(){
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
	
	public AbstractNode getResult(){
		throw new UnsupportedOperationException();
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(sortName);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
