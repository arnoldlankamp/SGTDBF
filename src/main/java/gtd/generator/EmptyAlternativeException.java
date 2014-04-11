package gtd.generator;

public class EmptyAlternativeException extends RuntimeException {
	private final static long serialVersionUID = 1719025922844291838L;

	public EmptyAlternativeException(){
		super("Encountered empty alternative");
	}
}
