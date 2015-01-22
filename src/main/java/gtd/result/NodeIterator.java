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
	
	private void visitPrefix(Link link, HashSet<Link> visitedLinks, HashSet<AbstractNode> visitedNodes){
		if(link == null || !visitedLinks.add(link)) return;
		
		if(link.node instanceof AbstractContainerNode){
			visitContainerNode((AbstractContainerNode) link.node, visitedLinks, visitedNodes);
		}
		
		if(link.prefixes != null){
			for(int i = link.prefixes.size() - 1; i >= 0; --i){
				visitPrefix(link.prefixes.get(i), visitedLinks, visitedNodes);
			}
		}
	}
	
	private void visitContainerNode(AbstractContainerNode node, HashSet<Link> visitedLinks, HashSet<AbstractNode> visitedNodes){
		if(node instanceof SortContainerNode && !visitedNodes.contains(node)){
			visit(node.name, (SortContainerNode) node);

			visitedNodes.add(node);
		}
		
		if(node.alternatives instanceof Link){
			visitPrefix((Link) node.alternatives, visitedLinks, visitedNodes);
		}else{
			ArrayList<Link> alternatives = (ArrayList<Link>) node.alternatives;
			for(int i = alternatives.size() - 1; i >= 0; --i){
				visitPrefix(alternatives.get(i), visitedLinks, visitedNodes);
			}
		}
	}
	
	public void iterate(){
		HashSet<Link> visitedLinks = new HashSet<Link>();
		HashSet<AbstractNode> visitedNodes = new HashSet<AbstractNode>();
		
		if(result instanceof AbstractContainerNode) {
			AbstractContainerNode node = (AbstractContainerNode) result;
			visitContainerNode(node, visitedLinks, visitedNodes);
		}
	}
}
