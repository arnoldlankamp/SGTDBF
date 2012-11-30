package gtd.util;

public class MarkableForwardSplittingLink<E>{
	public final ArrayList<MarkableForwardSplittingLink<E>> nexts;
	
	public final E element;
	
	public boolean mark;
	
	public MarkableForwardSplittingLink(E element){
		super();
		
		this.nexts = new ArrayList<MarkableForwardSplittingLink<E>>();
		
		this.element = element;
	}
	
	public void addLink(MarkableForwardSplittingLink<E> next){
		nexts.add(next);
	}
	
	public void setMark(){
		mark = true;
	}
}
