package gtd.result;

import gtd.result.struct.Link;
import gtd.util.ArrayList;
import gtd.util.HashSet;

@SuppressWarnings("unchecked")
public abstract class NodeIterator{
	private final AbstractNode result;
	
	public NodeIterator(AbstractNode result){
		super();
		
		this.result = result;
	}
	
	protected abstract void visit(String name, SortContainerNode node);
	
	private void visitPrefix(Link link, HashSet<AbstractNode> visitedNodes){
		if(link == null) return;
		
		if(link.node instanceof AbstractContainerNode){
			if(!visitContainerNode((AbstractContainerNode) link.node, visitedNodes)) return;
		}
		
		if(link.prefixes != null){
			for(int i = link.prefixes.size() - 1; i >= 0; --i){
				visitPrefix(link.prefixes.get(i), visitedNodes);
			}
		}
	}
	
	private boolean visitContainerNode(AbstractContainerNode node, HashSet<AbstractNode> visitedNodes){
		if(!visitedNodes.add(node)) return false;
		
		if(node instanceof SortContainerNode){
			visit(node.name, (SortContainerNode) node);
		}
		
		if(node.alternatives instanceof Link){
			visitPrefix((Link) node.alternatives, visitedNodes);
		}else{
			ArrayList<Link> alternatives = (ArrayList<Link>) node.alternatives;
			for(int i = alternatives.size() - 1; i >= 0; --i){
				visitPrefix(alternatives.get(i), visitedNodes);
			}
		}
		
		return true;
	}
	
	public void iterate(){
		HashSet<AbstractNode> visitedNodes = new HashSet<AbstractNode>();
		
		if(result instanceof AbstractContainerNode) {
			AbstractContainerNode node = (AbstractContainerNode) result;
			visitContainerNode(node, visitedNodes);
		}
	}
}
