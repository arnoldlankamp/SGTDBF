package gtd.util;

@SuppressWarnings({"rawtypes","unchecked"})
public class BackwardLink<E>{
	public final BackwardLink<E> previous;
	
	public final E element;
	
	public BackwardLink(BackwardLink previous, E element){
		super();
		
		this.previous = previous;
		
		this.element = element;
	}
}
