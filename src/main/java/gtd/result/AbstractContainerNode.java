package gtd.result;

import gtd.result.struct.Link;
import gtd.util.ArrayList;

@SuppressWarnings("unchecked")
public abstract class AbstractContainerNode extends AbstractNode{
	protected final String name;
	protected Object alternatives;
	
	protected final boolean isNullable;
	protected final boolean isSeparator;
	
	public AbstractContainerNode(String name, boolean isNullable, boolean isSeparator){
		super();
		
		this.name = name;
		this.isNullable = isNullable;
		this.isSeparator = isSeparator;
	}
	
	public void addAlternative(Link children){
		if(this.alternatives == null){
			this.alternatives = children;
		}else{
			ArrayList<Link> alternatives;
			if(this.alternatives instanceof ArrayList){
				alternatives = (ArrayList<Link>) this.alternatives;
			}else{
				alternatives = new ArrayList<Link>(2);
				alternatives.add((Link) this.alternatives);
				this.alternatives = alternatives;
			}
			alternatives.add(children);
		}
	}
	
	public boolean isEmpty(){
		return isNullable;
	}
	
	public boolean isSeparator(){
		return isSeparator;
	}
}
