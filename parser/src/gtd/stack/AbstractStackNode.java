package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.struct.Link;
import gtd.stack.edge.EdgesSet;
import gtd.util.ArrayList;
import gtd.util.BitSet;
import gtd.util.IntegerList;
import gtd.util.IntegerObjectList;

@SuppressWarnings({"unchecked", "cast"})
public abstract class AbstractStackNode{
	public final static int START_SYMBOL_ID = -1;
	public final static int DEFAULT_START_LOCATION = -1;
	
	protected AbstractStackNode next;
	protected AbstractStackNode[] alternateNexts;
	protected IntegerObjectList<EdgesSet> edgesMap;
	protected ArrayList<Link>[] prefixesMap;
	
	protected EdgesSet incomingEdges;
	
	protected final int id;
	
	protected final int startLocation;
	
	private boolean isEndNode;
	
	private boolean isSeparator;
	
	private BitSet propagatedPrefixes;
	private IntegerList propagatedReductions;
	
	protected AbstractStackNode(int id, boolean isEndNode){
		super();
		
		this.id = id;
		
		this.isEndNode = isEndNode;
		
		startLocation = DEFAULT_START_LOCATION;
	}
	
	protected AbstractStackNode(AbstractStackNode original, int startLocation){
		super();
		
		id = original.id;
		
		next = original.next;
		alternateNexts = original.alternateNexts;
		
		this.isEndNode = original.isEndNode;
		this.isSeparator = original.isSeparator;
		
		this.startLocation = startLocation;
	}
	
	// General.
	public int getId(){
		return id;
	}
	
	public boolean isEndNode(){
		return isEndNode;
	}
	
	public void markAsSeparator(){
		isSeparator = true;
	}
	
	public boolean isSeparator(){
		return isSeparator;
	}
	
	public final boolean isMatchable(){
		return (this instanceof AbstractMatchableStackNode);
	}
	
	public final boolean isExpandable(){
		return (this instanceof AbstractExpandableStackNode);
	}
	
	public final boolean isEpsilon(){
		return (this instanceof EpsilonStackNode);
	}
	
	public abstract boolean isEmptyLeafNode();
	
	public abstract String getName();
	
	public abstract int getNonterminalIndex();
	
	public abstract AbstractNode match(char[] input, int location);
	
	// Sharing.
	public abstract AbstractStackNode getCleanCopy(int startLocation);
	
	public abstract AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result);
	
	public boolean isSimilar(AbstractStackNode node){
		return (node.id == id);
	}
	
	// Linking & prefixes.
	public void addNext(AbstractStackNode next){
		if(this.next == null){
			this.next = next;
		}else{
			if(alternateNexts == null){
				alternateNexts = new AbstractStackNode[]{next};
			}else{
				int nrOfAlternateNexts = alternateNexts.length;
				AbstractStackNode[] newAlternateNexts = new AbstractStackNode[nrOfAlternateNexts + 1];
				System.arraycopy(alternateNexts, 0, newAlternateNexts, 0, nrOfAlternateNexts);
				newAlternateNexts[nrOfAlternateNexts] = next;
				alternateNexts = newAlternateNexts;
			}
		}
	}
	
	public boolean hasNext(){
		return (next != null);
	}
	
	public AbstractStackNode getNext(){
		return next;
	}
	
	public AbstractStackNode[] getAlternateNexts(){
		return alternateNexts;
	}
	
	public void initEdges(){
		edgesMap = new IntegerObjectList<EdgesSet>();
	}
	
	public void setIncomingEdges(EdgesSet incomingEdges){
		this.incomingEdges = incomingEdges;
	}
	
	public void addEdges(EdgesSet edges, int startLocation){
		edgesMap.add(startLocation, edges);
	}
	
	public void setEdgesSetWithPrefix(EdgesSet edges, Link prefix, int startLocation){
		int edgesMapSize = edgesMap.size();
		if(prefixesMap == null){
			prefixesMap = (ArrayList<Link>[]) new ArrayList[(edgesMapSize + 1) << 1];
		}else{
			int prefixesMapSize = prefixesMap.length;
			int possibleMaxSize = edgesMapSize + 1;
			if(prefixesMapSize < possibleMaxSize){
				ArrayList<Link>[] oldPrefixesMap = prefixesMap;
				prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize << 1];
				System.arraycopy(oldPrefixesMap, 0, prefixesMap, 0, edgesMapSize);
			}
		}
		
		int index = edgesMapSize;
		edgesMap.add(startLocation, edges);
		
		ArrayList<Link> prefixes = prefixesMap[index];
		if(prefixes == null){
			prefixes = new ArrayList<Link>(1);
			prefixesMap[index] = prefixes;
		}
		prefixes.add(prefix);
	}
	
	private void addPrefix(Link prefix, int index){
		ArrayList<Link> prefixes = prefixesMap[index];
		if(prefixes == null){
			prefixes = new ArrayList<Link>(1);
			prefixesMap[index] = prefixes;
		}
		
		prefixes.add(prefix);
	}
	
	public void updateNodeAfterNonEmptyMatchable(AbstractStackNode predecessor, AbstractNode result){
		ArrayList<Link>[] prefixesMapToAdd = predecessor.prefixesMap;
		
		edgesMap = predecessor.edgesMap;

		prefixesMap = (ArrayList<Link>[]) new ArrayList[edgesMap.size()];
		
		if(prefixesMapToAdd == null){
			addPrefix(new Link(null, result), edgesMap.findKey(predecessor.getStartLocation()));
		}else{
			int nrOfPrefixes = edgesMap.size();
			for(int i = nrOfPrefixes - 1; i >= 0; --i){
				ArrayList<Link> prefixes = new ArrayList<Link>(1);
				prefixesMap[i] = prefixes;
				
				prefixes.add(new Link(prefixesMapToAdd[i], result));
			}
		}
	}
	
	public void updateNode(AbstractStackNode predecessor, AbstractNode result){
		IntegerObjectList<EdgesSet> edgesMapToAdd = predecessor.edgesMap;
		ArrayList<Link>[] prefixesMapToAdd = predecessor.prefixesMap;
		
		if(edgesMap == null){
			edgesMap = new IntegerObjectList<EdgesSet>(edgesMapToAdd);
			
			prefixesMap = (ArrayList<Link>[]) new ArrayList[edgesMap.size()];
			
			if(prefixesMapToAdd == null){
				int index = edgesMap.findKey(predecessor.getStartLocation());
				addPrefix(new Link(null, result), index);
			}else{
				int nrOfPrefixes = edgesMapToAdd.size();
				for(int i = nrOfPrefixes - 1; i >= 0; --i){
					ArrayList<Link> prefixes = prefixesMap[i];
					if(prefixes == null){
						prefixes = new ArrayList<Link>(1);
						prefixesMap[i] = prefixes;
					}
					
					prefixes.add(new Link(prefixesMapToAdd[i], result));
				}
			}
		}else if(edgesMap != edgesMapToAdd){
			int edgesMapSize = edgesMap.size();
			int possibleMaxSize = edgesMapSize + edgesMapToAdd.size();
			if(prefixesMap == null){
				prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
			}else{
				if(prefixesMap.length < possibleMaxSize){
					ArrayList<Link>[] oldPrefixesMap = prefixesMap;
					prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
					System.arraycopy(oldPrefixesMap, 0, prefixesMap, 0, edgesMapSize);
				}
			}
			
			if(prefixesMapToAdd == null){
				addPrefix(new Link(null, result), edgesMapSize);
				edgesMap.add(predecessor.getStartLocation(), edgesMapToAdd.getValue(0));
			}else{
				for(int i = edgesMapToAdd.size() - 1; i >= 0; --i){
					int startLocation = edgesMapToAdd.getKey(i);
					int index = edgesMap.findKeyBefore(startLocation, edgesMapSize);
					ArrayList<Link> prefixes;
					if(index == -1){
						index = edgesMap.size();
						edgesMap.add(startLocation, edgesMapToAdd.getValue(i));
						
						prefixes = new ArrayList<Link>(1);
						prefixesMap[index] = prefixes;
					}else{
						prefixes = prefixesMap[index];
					}
					
					prefixes.add(new Link(prefixesMapToAdd[i], result));
				}
			}
		}else{
			if(prefixesMapToAdd == null){
				int index = edgesMap.findKey(predecessor.getStartLocation());
				addPrefix(new Link(null, result), index);
			}else{
				int nrOfPrefixes = edgesMapToAdd.size();
				for(int i = nrOfPrefixes - 1; i >= 0; --i){
					prefixesMap[i].add(new Link(prefixesMapToAdd[i], result));
				}
			}
		}
	}
	
	public int updateOvertakenNode(AbstractStackNode predecessor, AbstractNode result){
		IntegerObjectList<EdgesSet> edgesMapToAdd = predecessor.edgesMap;
		ArrayList<Link>[] prefixesMapToAdd = predecessor.prefixesMap;
		
		int edgesMapSize = edgesMap.size();
		int possibleMaxSize = edgesMapSize + edgesMapSize;
		if(prefixesMap == null){
			prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
		}else{
			if(prefixesMap.length < possibleMaxSize){
				ArrayList<Link>[] oldPrefixesMap = prefixesMap;
				prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
				System.arraycopy(oldPrefixesMap, 0, prefixesMap, 0, edgesMapSize);
			}
		}
		
		int nrOfAddedEdges = 0;
		if(prefixesMapToAdd == null){
			addPrefix(new Link(null, result), edgesMapSize);
			edgesMap.add(predecessor.getStartLocation(), edgesMapToAdd.getValue(0));
			nrOfAddedEdges = 1;
		}else{
			int fromIndex = edgesMapToAdd.size() - edgesMapSize;
			for(int i = edgesMapToAdd.size() - 1; i >= fromIndex; --i){
				int startLocation = edgesMapToAdd.getKey(i);
				
				int index = edgesMap.findKey(startLocation);
				ArrayList<Link> prefixes;
				if(index == -1){
					index = edgesMap.size();
					edgesMap.add(startLocation, edgesMapToAdd.getValue(i));
					
					prefixes = new ArrayList<Link>(1);
					prefixesMap[index] = prefixes;
					
					++nrOfAddedEdges;
				}else{
					prefixes = prefixesMap[index];
				}
				
				prefixes.add(new Link(prefixesMapToAdd[i], result));
			}
		}
		
		return nrOfAddedEdges;
	}
	
	public int updateNullableOvertakenNode(AbstractStackNode predecessor, AbstractNode result, int potentialNewEdges){
		IntegerObjectList<EdgesSet> edgesMapToAdd = predecessor.edgesMap;
		ArrayList<Link>[] prefixesMapToAdd = predecessor.prefixesMap;
		
		int edgesMapSize = edgesMap.size();
		int possibleMaxSize = edgesMapSize + potentialNewEdges;
		
		if(prefixesMap == null){
			prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
			
			propagatedPrefixes = new BitSet(possibleMaxSize);
		}else{
			if(prefixesMap.length < possibleMaxSize){
				ArrayList<Link>[] oldPrefixesMap = prefixesMap;
				prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
				System.arraycopy(oldPrefixesMap, 0, prefixesMap, 0, edgesMapSize);
			}
			
			if(propagatedPrefixes == null){
				propagatedPrefixes = new BitSet(possibleMaxSize);
			}else{
				propagatedPrefixes.enlargeTo(possibleMaxSize);
			}
			
		}
		
		int nrOfAddedEdges = 0;
		if(prefixesMapToAdd == null){
			addPrefix(new Link(null, result), edgesMapSize);
			edgesMap.add(predecessor.getStartLocation(), edgesMapToAdd.getValue(0));
			nrOfAddedEdges = 1;
		}else{
			int fromIndex = edgesMapToAdd.size() - potentialNewEdges;
			for(int i = edgesMapToAdd.size() - 1; i >= fromIndex; --i){
				int startLocation = edgesMapToAdd.getKey(i);

				int index = edgesMap.findKey(startLocation);
				
				ArrayList<Link> prefixes;
				if(index == -1){
					index = edgesMap.size();
					edgesMap.add(startLocation, edgesMapToAdd.getValue(i));
					propagatedPrefixes.set(index);
					
					prefixes = new ArrayList<Link>(1);
					prefixesMap[index] = prefixes;
					
					++nrOfAddedEdges;
				}else{
					if(propagatedPrefixes.isSet(index)) continue;
					
					prefixes = prefixesMap[index];
				}
				
				prefixes.add(new Link(prefixesMapToAdd[i], result));
			}
		}
		
		return nrOfAddedEdges;
	}
	
	public void updatePrefixSharedNode(IntegerObjectList<EdgesSet> edgesMap, ArrayList<Link>[] prefixesMap){
		this.edgesMap = edgesMap;
		this.prefixesMap = prefixesMap;
	}
	
	public IntegerObjectList<EdgesSet> getEdges(){
		return edgesMap;
	}
	
	public EdgesSet getIncomingEdges(){
		return incomingEdges;
	}
	
	public ArrayList<Link>[] getPrefixesMap(){
		return prefixesMap;
	}
	
	public IntegerList getPropagatedReductions(){
		if(propagatedReductions == null) propagatedReductions = new IntegerList();
		
		return propagatedReductions;
	}
	
	// Location.
	public int getStartLocation(){
		return startLocation;
	}
	
	public abstract int getLength();
	
	// Expandable.
	public abstract AbstractStackNode[] getChildren();
	
	public abstract boolean canBeEmpty();
	
	public abstract AbstractStackNode getEmptyChild();
	
	// Results.
	public abstract AbstractNode getResult();
}
