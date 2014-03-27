package gtd;

import gtd.generator.ParserStructure;
import gtd.result.AbstractContainerNode;
import gtd.result.AbstractNode;
import gtd.result.ListContainerNode;
import gtd.result.SortContainerNode;
import gtd.result.struct.Link;
import gtd.stack.AbstractExpandableStackNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.SortStackNode;
import gtd.stack.edge.EdgesSet;
import gtd.util.ArrayList;
import gtd.util.DoubleStack;
import gtd.util.IntegerList;
import gtd.util.IntegerObjectList;
import gtd.util.Stack;

@SuppressWarnings({"unchecked"})
public final class SGTDBF implements IGTD{
	private final static int DEFAULT_TODOLIST_CAPACITY = 16;
	
	protected final char[] input;
	
	private DoubleStack<AbstractStackNode, AbstractNode>[] todoLists;
	private int queueIndex;
	
	private final Stack<AbstractStackNode> stacksToExpand;
	private DoubleStack<AbstractStackNode, AbstractNode> stacksWithTerminalsToReduce;
	private final DoubleStack<AbstractStackNode, AbstractNode> stacksWithNonTerminalsToReduce;
	
	private EdgesSet[] cachedEdgesForExpect;
	
	private AbstractStackNode[] sharedNextNodes;
	
	protected int location;
	
	private final AbstractStackNode[][] expectMatrix;
	private final ArrayList<String> containerIndexMap;
	private final int numberOfContainers;
	private final int numberOfUniqueNodes;
	
	public SGTDBF(char[] input, ParserStructure structure){
		super();
		
		this.input = input;
		
		expectMatrix = structure.expectMatrix;
		containerIndexMap = structure.containerIndexMap;
		numberOfContainers = containerIndexMap.size();
		numberOfUniqueNodes = structure.numberOfUniqueNodes;
		
		stacksToExpand = new Stack<AbstractStackNode>();
		stacksWithTerminalsToReduce = new DoubleStack<AbstractStackNode, AbstractNode>();
		stacksWithNonTerminalsToReduce = new DoubleStack<AbstractStackNode, AbstractNode>();
		
		cachedEdgesForExpect = new EdgesSet[numberOfContainers];
		
		sharedNextNodes = new AbstractStackNode[numberOfUniqueNodes];
		
		location = 0;
	}
	
	private AbstractStackNode updateNextNode(AbstractStackNode next, AbstractStackNode node, AbstractNode result){
		AbstractStackNode alternative = sharedNextNodes[next.getId()];
		
		if(alternative != null){
			if(node.getStartLocation() == location){
				if(alternative.isMatchable()){
					if(alternative.isEmptyLeafNode()){
						// Encountered possible stack 'overtake'.
						if(node.getStartLocation() != location){
							propagateEdgesAndPrefixes(node, result, alternative, alternative.getResult());
						}else{
							propagateEdgesAndPrefixesForNullable(node, result, alternative, alternative.getResult(), node.getEdges().size());
						}
						return alternative;
					}
				}else{
					if(alternative.getStartLocation() == location){
						EdgesSet alternativeEdgesSet = alternative.getIncomingEdges();
						if(alternativeEdgesSet != null && alternativeEdgesSet.getLastVisistedLevel() == location){
							// Encountered possible stack 'overtake'.
							if(node.getStartLocation() != location){
								propagateEdgesAndPrefixes(node, result, alternative, alternativeEdgesSet.getLastResult());
							}else{
								propagateEdgesAndPrefixesForNullable(node, result, alternative, alternativeEdgesSet.getLastResult(), node.getEdges().size());
							}
							return alternative;
						}
					}
				}
			}
			
			alternative.updateNode(node, result);
			
			return alternative;
		}
		
		if(next.isMatchable()){
			if((location + next.getLength()) > input.length) return null;
			
			AbstractNode nextResult = next.match(input, location);
			if(nextResult == null){
				sharedNextNodes[next.getId()] = null;
				return null;
			}
			
			next = next.getCleanCopyWithResult(location, nextResult);
		}else{
			next = next.getCleanCopy(location);
		}
		
		if(!node.isMatchable() || node.getStartLocation() == location){
			next.updateNode(node, result);
		}else{
			next.updateNodeAfterNonEmptyMatchable(node, result);
		}
		
		sharedNextNodes[next.getId()] = next;
		stacksToExpand.push(next);
		return next;
	}
	
	private boolean updateAlternativeNextNode(AbstractStackNode next, AbstractStackNode node, AbstractNode result, IntegerObjectList<EdgesSet> edgesMap, ArrayList<Link>[] prefixesMap){
		AbstractStackNode alternative = sharedNextNodes[next.getId()];
		if(alternative != null){
			if(node.getStartLocation() == location){
				if(alternative.isMatchable()){
					if(alternative.isEmptyLeafNode()){
						// Encountered possible stack 'overtake'.
						propagateAlternativeEdgesAndPrefixes(node, result, alternative, alternative.getResult(), node.getEdges().size(), edgesMap, prefixesMap);
						return true;
					}
				}else{
					if(alternative.getStartLocation() == location){
						EdgesSet alternativeEdgesSet = alternative.getIncomingEdges();
						if(alternativeEdgesSet != null && alternativeEdgesSet.getLastVisistedLevel() == location){
							// Encountered possible stack 'overtake'.
							propagateAlternativeEdgesAndPrefixes(node, result, alternative, alternativeEdgesSet.getLastResult(), node.getEdges().size(), edgesMap, prefixesMap);
							return true;
						}
					}
				}
			}
			
			alternative.updatePrefixSharedNode(edgesMap, prefixesMap);
			
			return true;
		}
		
		if(next.isMatchable()){
			if((location + next.getLength()) > input.length) return false;
			
			AbstractNode nextResult = next.match(input, location);
			if(nextResult == null){
				sharedNextNodes[next.getId()] = null;
				return false;
			}
			
			next = next.getCleanCopyWithResult(location, nextResult);
		}else{
			next = next.getCleanCopy(location);
		}
		
		next.updatePrefixSharedNode(edgesMap, prefixesMap);
		
		sharedNextNodes[next.getId()] = next;
		stacksToExpand.push(next);
		
		return true;
	}
	
	private void propagateReductions(AbstractStackNode node, AbstractNode nodeResultStore, AbstractStackNode next, AbstractNode nextResultStore, int potentialNewEdges){
		IntegerList propagatedReductions = next.getPropagatedReductions();
		
		IntegerObjectList<EdgesSet> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixes = node.getPrefixesMap();
		
		int fromIndex = edgesMap.size() - potentialNewEdges;
		for(int i = edgesMap.size() - 1; i >= fromIndex; --i){
			int startLocation = edgesMap.getKey(i);
			
			// We know we haven't been here before.
			propagatedReductions.add(startLocation);
			
			ArrayList<Link> edgePrefixes = new ArrayList<Link>();
			Link prefix = (prefixes != null) ? new Link(prefixes[i], nodeResultStore) : new Link(null, nodeResultStore);
			edgePrefixes.add(prefix);
			
			Link resultLink = new Link(edgePrefixes, nextResultStore);
			
			handleEdgeSet(edgesMap.getValue(i), resultLink, edgesMap.getKey(i));
		}
	}
	
	private void propagatePrefixes(AbstractStackNode next, AbstractNode nextResult, int nrOfAddedEdges){
		// Proceed with the tail of the production.
		AbstractStackNode nextNext = next.getNext();
		AbstractStackNode nextNextAlternative = sharedNextNodes[nextNext.getId()];
		if(nextNextAlternative != null){
			if(nextNextAlternative.isMatchable()){
				if(nextNextAlternative.isEmptyLeafNode()){
					propagateEdgesAndPrefixesForNullable(next, nextResult, nextNextAlternative, nextNextAlternative.getResult(), nrOfAddedEdges);
				}else{
					nextNextAlternative.updateNode(next, nextResult);
				}
			}else{
				EdgesSet nextNextAlternativeEdgesSet = nextNextAlternative.getIncomingEdges();
				if(nextNextAlternativeEdgesSet != null && nextNextAlternativeEdgesSet.getLastVisistedLevel() == location){
					propagateEdgesAndPrefixesForNullable(next, nextResult, nextNextAlternative, nextNextAlternativeEdgesSet.getLastResult(), nrOfAddedEdges);
				}else{
					nextNextAlternative.updateNode(next, nextResult);
				}
			}
		}
		
		// Handle alternative nexts (and prefix sharing).
		AbstractStackNode[] alternateNexts = next.getAlternateNexts();
		if(alternateNexts != null){
			if(nextNextAlternative == null){ // If the first continuation has not been initialized yet (it may be a matchable that didn't match), create a dummy version to construct the necessary edges and prefixes.
				if(!nextNext.isMatchable()) return; // Matchable, abort.
				nextNextAlternative = nextNext.getCleanCopy(location);
				nextNextAlternative.updateNode(next, nextResult);
			}
			
			IntegerObjectList<EdgesSet> nextNextEdgesMap = nextNextAlternative.getEdges();
			ArrayList<Link>[] nextNextPrefixesMap = nextNextAlternative.getPrefixesMap();
			
			for(int i = alternateNexts.length - 1; i >= 0; --i){
				AbstractStackNode alternativeNext = alternateNexts[i];
				
				AbstractStackNode nextNextAltAlternative = sharedNextNodes[alternativeNext.getId()];
				if(nextNextAltAlternative != null){
					if(nextNextAltAlternative.isMatchable()){
						if(nextNextAltAlternative.isEmptyLeafNode()){
							propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextNextAltAlternative.getResult(), nrOfAddedEdges, nextNextEdgesMap, nextNextPrefixesMap);
						}else{
							nextNextAltAlternative.updatePrefixSharedNode(nextNextEdgesMap, nextNextPrefixesMap);
						}
					}else{
						EdgesSet nextAlternativeEdgesSet = nextNextAlternative.getIncomingEdges();
						if(nextAlternativeEdgesSet != null && nextAlternativeEdgesSet.getLastVisistedLevel() == location){
							propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextAlternativeEdgesSet.getLastResult(), nrOfAddedEdges, nextNextEdgesMap, nextNextPrefixesMap);
						}else{
							nextNextAltAlternative.updatePrefixSharedNode(nextNextEdgesMap, nextNextPrefixesMap);
						}
					}
				}
			}
		}
	}
	
	private void propagateEdgesAndPrefixes(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult){
		int nrOfAddedEdges = next.updateOvertakenNode(node, nodeResult);
		if(nrOfAddedEdges == 0) return;
		
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult, nrOfAddedEdges);
		}
		
		if(next.hasNext()){
			propagatePrefixes(next, nextResult, nrOfAddedEdges);
		}
	}
	
	private void propagateEdgesAndPrefixesForNullable(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult, int potentialNewEdges){
		int nrOfAddedEdges = next.updateNullableOvertakenNode(node, nodeResult, potentialNewEdges);
		if(nrOfAddedEdges == 0) return;
		
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult, nrOfAddedEdges);
		}
		
		if(next.hasNext()){
			propagatePrefixes(next, nextResult, nrOfAddedEdges);
		}
	}
	
	private void propagateAlternativeEdgesAndPrefixes(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult, int potentialNewEdges, IntegerObjectList<EdgesSet> edgesMap, ArrayList<Link>[] prefixesMap){
		next.updatePrefixSharedNode(edgesMap, prefixesMap);
		
		if(potentialNewEdges == 0) return;
		
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult, potentialNewEdges);
		}
		
		if(next.hasNext()){
			propagatePrefixes(next, nextResult, potentialNewEdges);
		}
	}
	
	private void updateEdges(AbstractStackNode node, AbstractNode result){
		IntegerObjectList<EdgesSet> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			
			handleEdgeSet(edgesMap.getValue(i), resultLink, edgesMap.getKey(i));
		}
	}
	
	private void updateNullableEdges(AbstractStackNode node, AbstractNode result){
		IntegerList propagatedReductions = node.getPropagatedReductions();
		
		int initialSize = propagatedReductions.size();
		
		IntegerObjectList<EdgesSet> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startLocation = edgesMap.getKey(i);
			
			if(propagatedReductions.containsBefore(startLocation, initialSize)) continue;
			propagatedReductions.add(startLocation);
			
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			
			handleEdgeSet(edgesMap.getValue(i), resultLink, startLocation);
		}
	}
	
	private void handleEdgeSet(EdgesSet edgeSet, Link resultLink, int startLocation){
		AbstractContainerNode resultStore = null;
		if(edgeSet.getLastVisistedLevel() != location){
			AbstractStackNode edge = edgeSet.get(0);
			
			resultStore = (!edge.isExpandable()) ? new SortContainerNode(edge.getName(), startLocation == location, edge.isSeparator()) : new ListContainerNode(edge.getName(), startLocation == location, edge.isSeparator());
			
			stacksWithNonTerminalsToReduce.push(edge, resultStore);
			
			for(int j = edgeSet.size() - 1; j >= 1; --j){
				edge = edgeSet.get(j);
				stacksWithNonTerminalsToReduce.push(edge, resultStore);
			}
			
			edgeSet.setLastResult(resultStore, location);
		}else{
			resultStore = edgeSet.getLastResult();
		}
		
		resultStore.addAlternative(resultLink);
	}
	
	private void moveToNext(AbstractStackNode node, AbstractNode result){
		AbstractStackNode newNext = node.getNext();
		AbstractStackNode next = updateNextNode(newNext, node, result);
		
		// Handle alternative nexts (and prefix sharing).
		AbstractStackNode[] alternateNexts = node.getAlternateNexts();
		if(alternateNexts != null){
			IntegerObjectList<EdgesSet> edgesMap = null;
			ArrayList<Link>[] prefixesMap = null;
			if(next != null){
				edgesMap = next.getEdges();
				prefixesMap = next.getPrefixesMap();
			}
			
			for(int i = alternateNexts.length - 1; i >= 0; --i){
				AbstractStackNode newAlternativeNext = alternateNexts[i];
				
				if(edgesMap != null){
					updateAlternativeNextNode(newAlternativeNext, node, result, edgesMap, prefixesMap);
				}else{
					AbstractStackNode alternativeNext = updateNextNode(newAlternativeNext, node, result);
					
					if(alternativeNext != null){
						edgesMap = alternativeNext.getEdges();
						prefixesMap = alternativeNext.getPrefixesMap();
					}
				}
			}
		}
	}
	
	private void move(AbstractStackNode node, AbstractNode result){
		if(node.isEndNode()){
			if(node.getStartLocation() != location || node.getId() == AbstractExpandableStackNode.DEFAULT_LIST_EPSILON_ID){
				updateEdges(node, result);
			}else{
				updateNullableEdges(node, result);
			}
		}
		
		if(node.hasNext()){
			moveToNext(node, result);
		}
	}
	
	private void reduce(){
		// Reduce terminals.
		while(!stacksWithTerminalsToReduce.isEmpty()){
			move(stacksWithTerminalsToReduce.peekFirst(), stacksWithTerminalsToReduce.popSecond());
		}
		
		// Reduce non-terminals.
		while(!stacksWithNonTerminalsToReduce.isEmpty()){
			move(stacksWithNonTerminalsToReduce.peekFirst(), stacksWithNonTerminalsToReduce.popSecond());
		}
	}
	
	private boolean findFirstStacksToReduce(){
		for(int i = 0; i < todoLists.length; ++i){
			DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[i];
			if(!(terminalsTodo == null || terminalsTodo.isEmpty())){
				stacksWithTerminalsToReduce = terminalsTodo;
				
				location += i;
				
				queueIndex = i;
				
				return true;
			}
		}
		
		return false;
	}
	
	private boolean findStacksToReduce(){
		int queueDepth = todoLists.length;
		for(int i = 1; i < queueDepth; ++i){
			queueIndex = (queueIndex + 1) % queueDepth;
			
			DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[queueIndex];
			if(!(terminalsTodo == null || terminalsTodo.isEmpty())){
				stacksWithTerminalsToReduce = terminalsTodo;
				
				location += i;
				
				return true;
			}
		}
		
		return false;
	}
	
	private void addTodo(AbstractStackNode node, int length, AbstractNode result){
		if(result == null) throw new RuntimeException();
		int queueDepth = todoLists.length;
		if(length >= queueDepth){
			DoubleStack<AbstractStackNode, AbstractNode>[] oldTodoLists = todoLists;
			todoLists = new DoubleStack[length + 1];
			System.arraycopy(oldTodoLists, queueIndex, todoLists, 0, queueDepth - queueIndex);
			System.arraycopy(oldTodoLists, 0, todoLists, queueDepth - queueIndex, queueIndex);
			queueDepth = length + 1;
			queueIndex = 0;
		}
		
		int insertLocation = (queueIndex + length) % queueDepth;
		DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[insertLocation];
		if(terminalsTodo == null){
			terminalsTodo = new DoubleStack<AbstractStackNode, AbstractNode>();
			todoLists[insertLocation] = terminalsTodo;
		}
		terminalsTodo.push(node, result);
	}
	
	private void handleExpects(EdgesSet cachedEdges, AbstractStackNode[] expects){
		for(int i = expects.length - 1; i >= 0; --i){
			AbstractStackNode first = expects[i];
			
			if(first.isMatchable()){
				int length = first.getLength();
				int endLocation = location + length;
				if(endLocation > input.length) continue;
				
				AbstractNode result = first.match(input, location);
				if(result == null) continue; // Discard if it didn't match.
				
				first = first.getCleanCopyWithResult(location, result);
				addTodo(first, length, result);
			}else{
				first = first.getCleanCopy(location);
				stacksToExpand.push(first);
			}
			
			first.initEdges();
			first.addEdges(cachedEdges, location);
		}
	}
	
	private void expandStack(AbstractStackNode node){
		if(node.isMatchable()){
			addTodo(node, node.getLength(), node.getResult());
		}else if(!node.isExpandable()){
			EdgesSet cachedEdges = cachedEdgesForExpect[node.getContainerIndex()];
			if(cachedEdges == null){
				cachedEdges = new EdgesSet(1);
				
				cachedEdgesForExpect[node.getContainerIndex()] = cachedEdges;
				
				AbstractStackNode[] expects = expectMatrix[node.getContainerIndex()];
				if(expects == null) return;
				
				handleExpects(cachedEdges, expects);
			}else{
				if(cachedEdges.getLastVisistedLevel() == location){ // Is nullable, add the known results.
					stacksWithNonTerminalsToReduce.push(node, cachedEdges.getLastResult());
				}
			}
			
			cachedEdges.add(node);
			
			node.setIncomingEdges(cachedEdges);
		}else{ // 'List'
			EdgesSet cachedEdges = cachedEdgesForExpect[node.getContainerIndex()];
			if(cachedEdges == null){
				cachedEdges = new EdgesSet();
				
				cachedEdgesForExpect[node.getContainerIndex()] = cachedEdges;
				
				AbstractStackNode[] listChildren = node.getChildren();
				
				for(int i = listChildren.length - 1; i >= 0; --i){
					AbstractStackNode child = listChildren[i];
					int childId = child.getId();
					
					AbstractStackNode sharedChild = sharedNextNodes[childId];
					if(sharedChild != null){
						sharedChild.setEdgesSetWithPrefix(cachedEdges, null, location);
					}else{
						if(child.isMatchable()){
							int length = child.getLength();
							int endLocation = location + length;
							if(endLocation > input.length) continue;
							
							AbstractNode result = child.match(input, location);
							if(result == null) continue; // Discard if it didn't match.
							
							child = child.getCleanCopyWithResult(location, result);
							addTodo(child, length, result);
						}else{
							child = child.getCleanCopy(location);
							stacksToExpand.push(child);
						}
						
						child.initEdges();
						child.setEdgesSetWithPrefix(cachedEdges, null, location);
						
						sharedNextNodes[childId] = child;
					}
				}
				
				if(node.canBeEmpty()){ // Star list or optional.
					AbstractStackNode empty = node.getEmptyChild().getCleanCopy(location);
					empty.initEdges();
					empty.addEdges(cachedEdges, location);
					
					stacksToExpand.push(empty);
				}
			}
			
			if(cachedEdges.getLastVisistedLevel() == location){ // Is nullable, add the known results.
				stacksWithNonTerminalsToReduce.push(node, cachedEdges.getLastResult());
			}
			
			cachedEdges.add(node);
			
			node.setIncomingEdges(cachedEdges);
		}
	}
	
	private void expand(){
		while(!stacksToExpand.isEmpty()){
			expandStack(stacksToExpand.pop());
		}
	}
	
	protected boolean isAtEndOfInput(){
		return (location == input.length);
	}
	
	protected boolean isInLookAhead(char[][] ranges, char[] characters){
		if(location == input.length) return false;
		
		char next = input[location];
		for(int i = ranges.length - 1; i >= 0; --i){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]) return true;
		}
		
		for(int i = characters.length - 1; i >= 0; --i){
			if(next == characters[i]) return true;
		}
		
		return false;
	}
	
	public AbstractNode parse(String start){
		// Initialize.
		todoLists = new DoubleStack[DEFAULT_TODOLIST_CAPACITY];
		
		AbstractStackNode rootNode = new SortStackNode(AbstractStackNode.START_SYMBOL_ID, containerIndexMap.find(start), true, start);
		rootNode = rootNode.getCleanCopy(0);
		rootNode.initEdges();
		
		stacksToExpand.push(rootNode);
		expand();
		
		if(findFirstStacksToReduce()){
			boolean shiftedLevel = (location != 0);
			do{
				if(shiftedLevel){ // Nullable fix for the first level.
					sharedNextNodes = new AbstractStackNode[numberOfUniqueNodes];
					cachedEdgesForExpect = new EdgesSet[numberOfContainers];
				}
				
				do{
					reduce();
					
					expand();
				}while(!stacksWithNonTerminalsToReduce.isEmpty() || !stacksWithTerminalsToReduce.isEmpty());
				shiftedLevel = true;
			}while(findStacksToReduce());
		}
		
		if(location == input.length){
			EdgesSet rootNodeEdgesSet = rootNode.getIncomingEdges();
			if(rootNodeEdgesSet != null && rootNodeEdgesSet.getLastVisistedLevel() == input.length){
				return rootNodeEdgesSet.getLastResult(); // Success.
			}
		}
		
		// Parse error.
		throw new RuntimeException("Parse Error before: "+(location == Integer.MAX_VALUE ? 0 : location));
	}
}
