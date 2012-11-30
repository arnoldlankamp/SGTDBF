package gtd.generator;

import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.Sequence;
import gtd.grammar.symbols.AbstractSymbol;
import gtd.grammar.symbols.CILiteral;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.CharRanges;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Optional;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.StarList;
import gtd.stack.AbstractStackNode;
import gtd.stack.CaseInsensitiveLiteralStackNode;
import gtd.stack.CharRangeStackNode;
import gtd.stack.CharStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;
import gtd.stack.OptionalStackNode;
import gtd.stack.SeparatedListStackNode;
import gtd.util.ArrayList;
import gtd.util.BackwardLink;
import gtd.util.MarkableForwardSplittingLink;
import gtd.util.SortedIntegerObjectList;

public class Preprocessor{
	private final Alternative[] alternatives;
	
	private int idCounter;
	
	public Preprocessor(Alternative[] alternatives, int idCounter){
		super();
		
		this.alternatives = alternatives;
		
		this.idCounter = idCounter;
	}
	
	public int getIdCounter(){
		return idCounter;
	}
	
	private static SortedIntegerObjectList<ArrayList<Alternative>> sortAlternativesByLength(Alternative[] alternatives){
		SortedIntegerObjectList<ArrayList<Alternative>> sortedAlternatives = new SortedIntegerObjectList<ArrayList<Alternative>>();
		
		for(int i = alternatives.length - 1; i >= 0; --i){
			Alternative alternative = alternatives[i];
			int alternativeLength = alternative.alternative.length;
			ArrayList<Alternative> alternativesList = sortedAlternatives.findValue(alternativeLength);
			if(alternativesList == null){
				alternativesList = new ArrayList<Alternative>();
				sortedAlternatives.add(alternativeLength, alternativesList);
			}
			alternativesList.add(alternative);
		}
		
		return sortedAlternatives;
	}
	
	private static MarkableForwardSplittingLink<AbstractSymbol> shareSymbol(AbstractSymbol symbol, ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> existingContinuations){
		if(existingContinuations != null){
			for(int i = existingContinuations.size() - 1; i >= 0; --i){
				MarkableForwardSplittingLink<AbstractSymbol> forwardSplittingLink = existingContinuations.get(i);
				if(forwardSplittingLink.element.equals(symbol)){
					return forwardSplittingLink;
				}
			}
		}
		
		return null;
	}
	
	private AbstractStackNode buildStackNode(AbstractSymbol symbol, int position){
		if(symbol instanceof Sort){
			return new NonTerminalStackNode(++idCounter, position, ((Sort) symbol).sortName);
		}else if(symbol instanceof Epsilon){
			return new EpsilonStackNode(++idCounter, position);
		}else if(symbol instanceof Char){
			return new CharStackNode(++idCounter, position, ((Char) symbol).character);
		}else if(symbol instanceof CharRange){
			CharRange charRange = (CharRange) symbol;
			char[][] ranges = new char[1][];
			ranges[0] = new char[2];
			ranges[0][0] = charRange.from;
			ranges[0][1] = charRange.to;
			return new CharRangeStackNode(++idCounter, position, charRange.name, ranges);
		}else if(symbol instanceof CharRanges){
			CharRanges charRanges = (CharRanges) symbol;
			CharRange[] charRangesArray = charRanges.charRanges;
			int numberOfCharRanges = charRangesArray.length;
			char[][] ranges = new char[numberOfCharRanges][];
			for(int i = numberOfCharRanges - 1; i >= 0; --i){
				CharRange charRange = charRangesArray[i];
				ranges[i] = new char[2];
				ranges[i][0] = charRange.from;
				ranges[i][1] = charRange.to;
			}
			return new CharRangeStackNode(++idCounter, position, charRanges.name, ranges);
		}else if(symbol instanceof Literal){
			return new LiteralStackNode(++idCounter, position, ((Literal) symbol).literal.toCharArray());
		}else if(symbol instanceof CILiteral){
			return new CaseInsensitiveLiteralStackNode(++idCounter, position, ((CILiteral) symbol).literal.toCharArray());
		}else if(symbol instanceof Optional){
			Optional optional = (Optional) symbol;
			AbstractStackNode child = buildStackNode(optional.symbol, 0);
			return new OptionalStackNode(++idCounter, position, child, optional.name);
		}else if(symbol instanceof PlusList){
			PlusList plusList = (PlusList) symbol;
			AbstractStackNode child = buildStackNode(plusList.symbol, 0);
			AbstractSymbol[] separatorSymbols = plusList.separators;
			if(separatorSymbols == null || separatorSymbols.length == 0){
				return new ListStackNode(++idCounter, position, child, plusList.name, true);
			}
			
			int numberOfSeparators = separatorSymbols.length;
			AbstractStackNode[] separators = new AbstractStackNode[numberOfSeparators];
			for(int i = 0; i < numberOfSeparators; ++i){
				separators[i] = buildStackNode(separatorSymbols[i], i + 1);
				separators[i].markAsSeparator();
			}
			
			return new SeparatedListStackNode(++idCounter, position, child, separators, plusList.name, true);
		}else if(symbol instanceof StarList){
			StarList starList = (StarList) symbol;
			AbstractStackNode child = buildStackNode(starList.symbol, 0);
			AbstractSymbol[] separatorSymbols = starList.separators;
			if(separatorSymbols == null || separatorSymbols.length == 0){
				return new ListStackNode(++idCounter, position, child, starList.name, false);
			}
			
			int numberOfSeparators = separatorSymbols.length;
			AbstractStackNode[] separators = new AbstractStackNode[numberOfSeparators];
			for(int i = 0; i < numberOfSeparators; ++i){
				separators[i] = buildStackNode(separatorSymbols[i], i + 1);
				separators[i].markAsSeparator();
			}
			
			return new SeparatedListStackNode(++idCounter, position, child, separators, starList.name, false);
		}else if(symbol instanceof Sequence){
			throw new RuntimeException("Sequence symbols are currently unsupported");
		}else if(symbol instanceof Alternative){
			throw new RuntimeException("Alternative symbols are currently unsupported");
		}else{
			throw new RuntimeException(String.format("Unsupported symbol type: %s", symbol.getClass().toString()));
		}
	}
	
	private void gatherPrefixSharedAlternatives(AbstractStackNode[] sharedPrefix, MarkableForwardSplittingLink<AbstractSymbol> alternativeTreeRoot, BackwardLink<AbstractStackNode> alternativePrefix, ArrayList<AbstractStackNode> alternativeFirstNodes, int position){
		AbstractStackNode stackNode = buildStackNode(alternativeTreeRoot.element, position);
		
		if(alternativeTreeRoot.mark) stackNode.markAsEndNode(); // TODO Make constructor arg and move
		
		ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> alternativeTreeRootContinuations = alternativeTreeRoot.nexts;
		int numberOfAlternativeTreeRootContinuations = alternativeTreeRootContinuations.size();
		if(numberOfAlternativeTreeRootContinuations > 0){
			BackwardLink<AbstractStackNode> alternativeChain = new BackwardLink<AbstractStackNode>(alternativePrefix, stackNode);
			
			int nextPosition = position + 1;
			
			gatherAlternatives(alternativeTreeRootContinuations.get(0), alternativeChain, alternativeFirstNodes, nextPosition);
			
			if(numberOfAlternativeTreeRootContinuations > 1){
				AbstractStackNode[] sharedSubPrefix = new AbstractStackNode[position + 1];
				AbstractStackNode[] sharedSubProduction = alternativeFirstNodes.get(alternativeFirstNodes.size() - 1).getProduction();
				System.arraycopy(sharedSubProduction, 0, sharedSubPrefix, 0, position + 1);
				
				for(int i = numberOfAlternativeTreeRootContinuations - 1; i >= 1; --i){
					gatherPrefixSharedAlternatives(sharedSubPrefix, alternativeTreeRootContinuations.get(i), alternativeChain, alternativeFirstNodes, nextPosition);
				}
			}
		}else{
			AbstractStackNode[] production = new AbstractStackNode[position + 1];
			System.arraycopy(sharedPrefix, 0, production, 0, sharedPrefix.length);
			
			BackwardLink<AbstractStackNode> symbolLink = alternativePrefix;
			production[position] = stackNode;
			stackNode.setProduction(production);
			for(int i = position - 1; i >= sharedPrefix.length; --i){
				AbstractStackNode node = symbolLink.element;
				production[i] = node;
				node.setProduction(production);
				
				symbolLink = symbolLink.previous;
			}
			
			sharedPrefix[sharedPrefix.length - 1].addProduction(production);
		}
	}
	
	private void gatherAlternatives(MarkableForwardSplittingLink<AbstractSymbol> alternativeTreeRoot, BackwardLink<AbstractStackNode> alternativePrefix, ArrayList<AbstractStackNode> alternativeFirstNodes, int position){
		AbstractStackNode stackNode = buildStackNode(alternativeTreeRoot.element, position);
		
		if(alternativeTreeRoot.mark) stackNode.markAsEndNode(); // TODO Make constructor arg and move
		
		ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> alternativeTreeRootContinuations = alternativeTreeRoot.nexts;
		int numberOfAlternativeTreeRootContinuations = alternativeTreeRootContinuations.size();
		if(numberOfAlternativeTreeRootContinuations > 0){
			BackwardLink<AbstractStackNode> alternativeChain = new BackwardLink<AbstractStackNode>(alternativePrefix, stackNode);
			
			int nextPosition = position + 1;
			
			gatherAlternatives(alternativeTreeRootContinuations.get(0), alternativeChain, alternativeFirstNodes, nextPosition);
			
			if(numberOfAlternativeTreeRootContinuations > 1){
				AbstractStackNode[] sharedPrefix = new AbstractStackNode[position + 1];
				AbstractStackNode[] sharedProduction = alternativeFirstNodes.get(alternativeFirstNodes.size() - 1).getProduction();
				System.arraycopy(sharedProduction, 0, sharedPrefix, 0, position + 1);
				
				for(int i = numberOfAlternativeTreeRootContinuations - 1; i >= 1; --i){
					gatherPrefixSharedAlternatives(sharedPrefix, alternativeTreeRootContinuations.get(i), alternativeChain, alternativeFirstNodes, nextPosition);
				}
			}
		}else{
			AbstractStackNode[] production = new AbstractStackNode[position + 1];
			BackwardLink<AbstractStackNode> symbolLink = alternativePrefix;
			production[position] = stackNode;
			stackNode.setProduction(production);
			for(int i = position - 1; i >= 0; --i){
				AbstractStackNode node = symbolLink.element;
				production[i] = node;
				node.setProduction(production);
				
				symbolLink = symbolLink.previous;
			}
			
			alternativeFirstNodes.add(production[0]);
		}
	}
	
	private AbstractStackNode[] transformToStackNodes(ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> expectTree){
		ArrayList<AbstractStackNode> alternativeFirstNodes = new ArrayList<AbstractStackNode>();
		for(int i = expectTree.size() - 1; i >= 0; --i){
			MarkableForwardSplittingLink<AbstractSymbol> alternativeTreeRoot = expectTree.get(i);
			gatherAlternatives(alternativeTreeRoot, null, alternativeFirstNodes, 0);
		}
		
		Object[] alternativeFirstNodesBackingArray = alternativeFirstNodes.getBackingArray();
		int numberOfAlternativeFirstNodes = alternativeFirstNodes.size();
		AbstractStackNode[] expects = new AbstractStackNode[numberOfAlternativeFirstNodes];
		System.arraycopy(alternativeFirstNodesBackingArray, 0, expects, 0, numberOfAlternativeFirstNodes);
		
		return expects;
	}

	private static ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> calculateSharing(SortedIntegerObjectList<ArrayList<Alternative>> sortedAlternatives){
		ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> expectTree = new ArrayList<MarkableForwardSplittingLink<AbstractSymbol>>();
		
		for(int i = sortedAlternatives.size() -1; i >= 0; --i){
			ArrayList<Alternative> alternativesList = sortedAlternatives.getValue(i);
			
			for(int j = alternativesList.size() - 1; j >= 0; --j){
				Alternative alternative = alternativesList.get(j);
				
				AbstractSymbol[] symbols = alternative.alternative;
				
				AbstractSymbol firstSymbol = symbols[0];
				MarkableForwardSplittingLink<AbstractSymbol> sharedLink = shareSymbol(firstSymbol, expectTree);
				if(sharedLink == null){
					sharedLink = new MarkableForwardSplittingLink<AbstractSymbol>(firstSymbol);
					expectTree.add(sharedLink);
					
					MarkableForwardSplittingLink<AbstractSymbol> previousLink;
					for(int k = 1; k < symbols.length; ++k){
						previousLink = sharedLink;
						sharedLink = new MarkableForwardSplittingLink<AbstractSymbol>(symbols[k]);
						previousLink.addLink(sharedLink);
					}
					sharedLink.setMark();
				}else{
					int k = 1;
					MarkableForwardSplittingLink<AbstractSymbol> previousLink;
					for(; k < symbols.length; ++k){
						previousLink = sharedLink;
						if((sharedLink = shareSymbol(symbols[k], sharedLink.nexts)) == null){
							sharedLink = previousLink;
							break;
						}
					}
					
					for(; k < symbols.length; ++k){
						previousLink = sharedLink;
						sharedLink = new MarkableForwardSplittingLink<AbstractSymbol>(symbols[k]);
						previousLink.addLink(sharedLink);
					}
					sharedLink.setMark();
				}
			}
		}
		
		return expectTree;
	}
	
	public AbstractStackNode[] buildExpects(){
		SortedIntegerObjectList<ArrayList<Alternative>> sortedAlternatives = sortAlternativesByLength(alternatives);
		
		ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> expectTree = calculateSharing(sortedAlternatives);
		
		return transformToStackNodes(expectTree);
	}
}
