package gtd.generator;

import gtd.stack.AbstractStackNode;
import gtd.util.ArrayList;

public class ParserStructure{
	public final AbstractStackNode[][] expectMatrix;
	public final ArrayList<String> containerIndexMap;
	public final int numberOfUniqueNodes;
	
	public ParserStructure(AbstractStackNode[][] expectMatrix, ArrayList<String> containerIndexMap, int numberOfUniqueNodes){
		super();
		
		this.expectMatrix = expectMatrix;
		this.containerIndexMap = containerIndexMap;
		this.numberOfUniqueNodes = numberOfUniqueNodes;
	}
}
