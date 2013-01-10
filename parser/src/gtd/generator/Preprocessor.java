package gtd.generator;

import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.AbstractSymbol;
import gtd.grammar.symbols.CILiteral;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.CharRanges;
import gtd.grammar.symbols.Choice;
import gtd.grammar.symbols.Epsilon;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Optional;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sequence;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.StarList;
import gtd.stack.AbstractStackNode;
import gtd.stack.CaseInsensitiveLiteralStackNode;
import gtd.stack.CharRangeStackNode;
import gtd.stack.CharStackNode;
import gtd.stack.ChoiceStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.OptionalStackNode;
import gtd.stack.SeparatedListStackNode;
import gtd.stack.SequenceStackNode;
import gtd.stack.SortStackNode;
import gtd.util.ArrayList;
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

	static int getContainerIndex(ArrayList<String> sortIndexMap, String sortName){
		int sortIndex = sortIndexMap.find(sortName);
		if(sortIndex == -1){
			sortIndex = sortIndexMap.size();
			sortIndexMap.add(sortName);
		}
		return sortIndex;
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
	
	private AbstractStackNode buildStackNode(AbstractSymbol symbol, boolean endNode, ArrayList<String> containerIndexMap){
		if(symbol instanceof Sort){
			String sortName = ((Sort) symbol).sortName;
			int containerIndex = getContainerIndex(containerIndexMap, sortName);
			return new SortStackNode(++idCounter, containerIndex, endNode, sortName);
		}else if(symbol instanceof Epsilon){
			return new EpsilonStackNode(++idCounter, endNode);
		}else if(symbol instanceof Char){
			return new CharStackNode(++idCounter, endNode, ((Char) symbol).character);
		}else if(symbol instanceof CharRange){
			CharRange charRange = (CharRange) symbol;
			char[][] ranges = new char[1][];
			ranges[0] = new char[2];
			ranges[0][0] = charRange.from;
			ranges[0][1] = charRange.to;
			return new CharRangeStackNode(++idCounter, endNode, charRange.name, ranges);
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
			return new CharRangeStackNode(++idCounter, endNode, charRanges.name, ranges);
		}else if(symbol instanceof Literal){
			return new LiteralStackNode(++idCounter, endNode, ((Literal) symbol).literal.toCharArray());
		}else if(symbol instanceof CILiteral){
			return new CaseInsensitiveLiteralStackNode(++idCounter, endNode, ((CILiteral) symbol).literal.toCharArray());
		}else if(symbol instanceof Optional){
			Optional optional = (Optional) symbol;
			int containerIndex = getContainerIndex(containerIndexMap, optional.name);
			
			AbstractStackNode child = buildStackNode(optional.symbol, true, containerIndexMap);
			return new OptionalStackNode(++idCounter, containerIndex, endNode, child, optional.name);
		}else if(symbol instanceof PlusList){
			PlusList plusList = (PlusList) symbol;
			int containerIndex = getContainerIndex(containerIndexMap, plusList.name);
			
			AbstractStackNode child = buildStackNode(plusList.symbol, true, containerIndexMap);
			AbstractSymbol[] separatorSymbols = plusList.separators;
			if(separatorSymbols == null || separatorSymbols.length == 0){
				return new ListStackNode(++idCounter, containerIndex, endNode, child, plusList.name, true);
			}
			
			int numberOfSeparators = separatorSymbols.length;
			AbstractStackNode[] separators = new AbstractStackNode[numberOfSeparators];
			for(int i = 0; i < numberOfSeparators; ++i){
				separators[i] = buildStackNode(separatorSymbols[i], false, containerIndexMap);
				separators[i].markAsSeparator();
			}
			
			return new SeparatedListStackNode(++idCounter, containerIndex, endNode, child, separators, plusList.name, true);
		}else if(symbol instanceof StarList){
			StarList starList = (StarList) symbol;
			int containerIndex = getContainerIndex(containerIndexMap, starList.name);
			
			AbstractStackNode child = buildStackNode(starList.symbol, true, containerIndexMap);
			AbstractSymbol[] separatorSymbols = starList.separators;
			if(separatorSymbols == null || separatorSymbols.length == 0){
				return new ListStackNode(++idCounter, containerIndex, endNode, child, starList.name, false);
			}
			
			int numberOfSeparators = separatorSymbols.length;
			AbstractStackNode[] separators = new AbstractStackNode[numberOfSeparators];
			for(int i = 0; i < numberOfSeparators; ++i){
				separators[i] = buildStackNode(separatorSymbols[i], false, containerIndexMap);
				separators[i].markAsSeparator();
			}
			
			return new SeparatedListStackNode(++idCounter, containerIndex, endNode, child, separators, starList.name, false);
		}else if(symbol instanceof Choice){
			Choice choice = (Choice) symbol;
			int containerIndex = getContainerIndex(containerIndexMap, choice.name);
			
			AbstractSymbol[] childSymbols = choice.symbols;
			int numberOfChildren = childSymbols.length;
			AbstractStackNode[] children = new AbstractStackNode[numberOfChildren];
			for(int i = 0; i < numberOfChildren; ++i){
				children[i] = buildStackNode(childSymbols[i], true, containerIndexMap);
			}
			
			return new ChoiceStackNode(++idCounter, containerIndex, endNode, children, choice.name);
		}else if(symbol instanceof Sequence){
			Sequence sequence = (Sequence) symbol;
			int containerIndex = getContainerIndex(containerIndexMap, sequence.name);
			
			AbstractSymbol[] childSymbols = sequence.symbols;
			int numberOfChildren = childSymbols.length;
			AbstractStackNode[] children = new AbstractStackNode[numberOfChildren];
			for(int i = 0; i < numberOfChildren - 1; ++i){
				children[i] = buildStackNode(childSymbols[i], false, containerIndexMap);
			}
			children[numberOfChildren - 1] = buildStackNode(childSymbols[numberOfChildren - 1], true, containerIndexMap);
			
			return new SequenceStackNode(++idCounter, containerIndex, endNode, children, sequence.name);
		}else{
			throw new RuntimeException(String.format("Unsupported symbol type: %s", symbol.getClass().toString()));
		}
	}
	
	private void addContinuations(AbstractStackNode node, ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> continuations, ArrayList<String> containerIndexMap){
		for(int i = continuations.size() - 1; i >= 0; --i){
			MarkableForwardSplittingLink<AbstractSymbol> continuation = continuations.get(i);
			AbstractStackNode nextNode = buildStackNode(continuation.element, continuation.mark, containerIndexMap);
			node.addNext(nextNode);
			addContinuations(nextNode, continuation.nexts, containerIndexMap);
		}
	}
	
	private AbstractStackNode[] transformToStackNodes(ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> expectTree, ArrayList<String> containerIndexMap){
		int numberOfExpects = expectTree.size();
		AbstractStackNode[] expects = new AbstractStackNode[numberOfExpects];
		
		for(int i = numberOfExpects - 1; i >= 0; --i){
			MarkableForwardSplittingLink<AbstractSymbol> alternativeTreeRoot = expectTree.get(i);
			AbstractStackNode node = buildStackNode(alternativeTreeRoot.element, alternativeTreeRoot.mark, containerIndexMap);
			addContinuations(node, alternativeTreeRoot.nexts, containerIndexMap);
			
			expects[i] = node;
		}
		
		return expects;
	}

	private static ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> calculateSharing(SortedIntegerObjectList<ArrayList<Alternative>> sortedAlternatives){
		ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> expectTree = new ArrayList<MarkableForwardSplittingLink<AbstractSymbol>>();
		
		for(int i = sortedAlternatives.size() - 1; i >= 0; --i){
			ArrayList<Alternative> alternativesList = sortedAlternatives.getValue(i);
			
			for(int j = alternativesList.size() - 1; j >= 0; --j){
				Alternative alternative = alternativesList.get(j);
				
				AbstractSymbol[] symbols = alternative.alternative;
				if(symbols.length == 0){
					throw new EmptyAlternativeException();
				}
				
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
	
	public AbstractStackNode[] buildExpects(ArrayList<String> sortIndexMap){
		SortedIntegerObjectList<ArrayList<Alternative>> sortedAlternatives = sortAlternativesByLength(alternatives);
		
		ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> expectTree = calculateSharing(sortedAlternatives);
		
		return transformToStackNodes(expectTree, sortIndexMap);
	}
}
