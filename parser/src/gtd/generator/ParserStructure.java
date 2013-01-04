package gtd.generator;

import gtd.stack.AbstractStackNode;
import gtd.util.ArrayList;

public class ParserStructure{
	public final AbstractStackNode[][] expectMatrix;
	public final ArrayList<String> sortIndexMap;
	public final int numberOfUniqueNodes;
	
	public ParserStructure(AbstractStackNode[][] expectMatrix, ArrayList<String> sortIndexMap, int numberOfUniqueNodes){
		super();
		
		this.expectMatrix = expectMatrix;
		this.sortIndexMap = sortIndexMap;
		this.numberOfUniqueNodes = numberOfUniqueNodes;
	}
}
