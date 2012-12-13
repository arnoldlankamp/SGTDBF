package gtd.generator;

import gtd.stack.AbstractStackNode;
import gtd.util.ArrayList;

public class ParserStructure{
	public final AbstractStackNode[][] expectMatrix;
	public final ArrayList<String> sortIndexMap;
	
	public ParserStructure(AbstractStackNode[][] expectMatrix, ArrayList<String> sortIndexMap){
		super();
		
		this.expectMatrix = expectMatrix;
		this.sortIndexMap = sortIndexMap;
	}
}
