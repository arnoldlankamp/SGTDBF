package gtd;

public class ParseErrorException extends RuntimeException{
	private static final long serialVersionUID = -5513756454125068624L;

	private final int location;
	
	public ParseErrorException(int location){
		super();
		
		this.location = location;
	}
	
	public String getMessage(){
		return String.format("Parse Error before: %s", location);
	}
}
