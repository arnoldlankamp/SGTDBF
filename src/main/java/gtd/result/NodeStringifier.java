package gtd.result;

import gtd.result.struct.Link;
import gtd.util.ArrayList;
import gtd.util.HashSet;

@SuppressWarnings("unchecked")
public class NodeStringifier{
	
	public static boolean evalPrefix(Link link, StringBuilder sb, HashSet<Link> visitedLinks){
		if(link == null) return true;
		
		if(!visitedLinks.add(link)) return false;
		
		if(link.prefixes != null){
			ArrayList<Link> prefixes = link.prefixes;
			for(int i = prefixes.size() - 1; i >= 0; --i){
				if(evalPrefix(prefixes.get(i), sb, visitedLinks)) break;
			}
		}
		
		evalNode(link.node, sb, visitedLinks);
		return true;
	}
	
	public static void evalNode(AbstractNode node, StringBuilder sb, HashSet<Link> visitedLinks){
		if(node instanceof AbstractContainerNode){
			Object alternatives = ((AbstractContainerNode) node).alternatives;
			Link alternative;
			if(alternatives instanceof Link){
				alternative = (Link) alternatives;
			}else{
				alternative = ((ArrayList<Link>) alternatives).get(0);
			}
			evalPrefix(alternative, sb, visitedLinks);
		}else if(node instanceof LiteralNode){
			sb.append(((LiteralNode) node).content);
		}else if(node instanceof CharNode){
			sb.append(((CharNode) node).character);
		}
	}
	
	public static String nodeToString(AbstractNode node){
		StringBuilder sb = new StringBuilder();
		HashSet<Link> visitedLinks = new HashSet<Link>();
		
		evalNode(node, sb, visitedLinks);
		
		return sb.toString();
	}
}
