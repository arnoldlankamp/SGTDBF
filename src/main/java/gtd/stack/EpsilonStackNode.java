package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.EpsilonNode;
import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public final class EpsilonStackNode extends AbstractMatchableStackNode{
	private final static EpsilonNode result = new EpsilonNode();
	
	public EpsilonStackNode(int id, boolean isEndNode, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(id, isEndNode, beforeFilters, afterFilters);
	}
	
	private EpsilonStackNode(EpsilonStackNode original, int startLocation){
		super(original, startLocation);
	}
	
	public boolean isEmptyLeafNode(){
		return true;
	}
	
	public AbstractNode match(char[] input, int location){
		return result;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new EpsilonStackNode(this, startLocation);
	}
	
	public AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result){
		return new EpsilonStackNode(this, startLocation);
	}
	
	public int getLength(){
		return 0;
	}
	
	public AbstractNode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
