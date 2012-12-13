package gtd.stack;

import gtd.result.AbstractNode;

public final class NonTerminalStackNode extends AbstractStackNode{
	private final int nonTerminalIndex;
	private final String nonTerminal;
	
	public NonTerminalStackNode(int id, int nonTerminalIndex, boolean isEndNode, String nonTerminal){
		super(id, isEndNode);
		
		this.nonTerminalIndex = nonTerminalIndex;
		this.nonTerminal = nonTerminal;
	}
	
	private NonTerminalStackNode(NonTerminalStackNode original, int startLocation){
		super(original, startLocation);
		
		nonTerminalIndex = original.nonTerminalIndex;
		nonTerminal = original.nonTerminal;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public int getNonterminalIndex(){
		return nonTerminalIndex;
	}
	
	public String getName(){
		return nonTerminal;
	}
	
	public AbstractNode match(char[] input, int location){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new NonTerminalStackNode(this, startLocation);
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
		sb.append(nonTerminal);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
