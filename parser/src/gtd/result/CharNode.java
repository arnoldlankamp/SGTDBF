package gtd.result;

import gtd.result.struct.Link;
import gtd.util.IndexedStack;

public class CharNode extends AbstractNode{
	private final char character;
	
	public CharNode(char character){
		super();
		
		this.character = character;
	}
	
	public void addAlternative(Link children){
		throw new UnsupportedOperationException();
	}
	
	public boolean isEmpty(){
		return false;
	}
	
	public boolean isSeparator(){
		return false;
	}
	
	protected String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		StringBuilder sb = new StringBuilder();
		sb.append('\'');
		sb.append(character);
		sb.append('\'');
		return sb.toString();
	}
}
