package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.CharNode;
import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

public final class CharRangeStackNode extends AbstractMatchableStackNode{
	private final char[][] ranges;
	
	private final AbstractNode result;
	
	public CharRangeStackNode(int id, boolean isEndNode, String production, char[][] ranges, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super(id, isEndNode, beforeFilters, afterFilters);

		this.ranges = ranges;
		
		result = null;
	}
	
	private CharRangeStackNode(CharRangeStackNode original, int startLocation){
		super(original, startLocation);
		
		ranges = original.ranges;
		
		result = null;
	}
	
	private CharRangeStackNode(CharRangeStackNode original, int startLocation, AbstractNode result){
		super(original, startLocation);
		
		this.ranges = original.ranges;
		
		this.result = result;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public AbstractNode match(char[] input, int location){
		char next = input[location];
		for(int i = ranges.length - 1; i >= 0; --i){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]){
				return new CharNode(next);
			}
		}
		return null;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new CharRangeStackNode(this, startLocation);
	}
	
	public AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result){
		return new CharRangeStackNode(this, startLocation, result);
	}
	
	public int getLength(){
		return 1;
	}
	
	public AbstractNode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
