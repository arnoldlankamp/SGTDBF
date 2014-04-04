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
	private final ArrayList<Alternative> alternatives;
	
	private int idCounter;
	
	public Preprocessor(ArrayList<Alternative> alternatives, int idCounter){
		super();
		
		this.alternatives = alternatives;
		
		this.idCounter = idCounter;
	}
	
	public int getIdCounter(){
		return idCounter;
	}
	
	private static SortedIntegerObjectList<ArrayList<Alternative>> sortAlternativesByLength(ArrayList<Alternative> alternatives){
		SortedIntegerObjectList<ArrayList<Alternative>> sortedAlternatives = new SortedIntegerObjectList<ArrayList<Alternative>>();
		
		for(int i = alternatives.size() - 1; i >= 0; --i){
			Alternative alternative = alternatives.get(i);
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
	
	private AbstractStackNode buildStackNode(AbstractSymbol symbol, boolean endNode){
		if(symbol instanceof Epsilon){
			return new EpsilonStackNode(++idCounter, endNode, symbol.beforeFilters, symbol.afterFilters);
		}else if(symbol instanceof Char){
			return new CharStackNode(++idCounter, endNode, ((Char) symbol).character, symbol.beforeFilters, symbol.afterFilters);
		}else if(symbol instanceof CharRange){
			CharRange charRange = (CharRange) symbol;
			char[][] ranges = new char[1][];
			ranges[0] = new char[2];
			ranges[0][0] = charRange.from;
			ranges[0][1] = charRange.to;
			return new CharRangeStackNode(++idCounter, endNode, charRange.name, ranges, symbol.beforeFilters, symbol.afterFilters);
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
			return new CharRangeStackNode(++idCounter, endNode, charRanges.name, ranges, symbol.beforeFilters, symbol.afterFilters);
		}else if(symbol instanceof Literal){
			return new LiteralStackNode(++idCounter, endNode, ((Literal) symbol).literal.toCharArray(), symbol.beforeFilters, symbol.afterFilters);
		}else if(symbol instanceof CILiteral){
			return new CaseInsensitiveLiteralStackNode(++idCounter, endNode, ((CILiteral) symbol).literal.toCharArray(), symbol.beforeFilters, symbol.afterFilters);
		}else if(symbol instanceof IdentifiedSymbol){
			IdentifiedSymbol identifiedSymbol = (IdentifiedSymbol) symbol;
			int containerId = identifiedSymbol.id;
			AbstractSymbol wrappedSymbol = identifiedSymbol.symbol;
			
			if(wrappedSymbol instanceof Sort){
				String sortName = ((Sort) wrappedSymbol).sortName;
				return new SortStackNode(++idCounter, containerId, endNode, sortName, wrappedSymbol.beforeFilters, wrappedSymbol.afterFilters);
			}else if(wrappedSymbol instanceof Optional){
				Optional optional = (Optional) wrappedSymbol;
				
				AbstractStackNode child = buildStackNode(optional.symbol, true);
				return new OptionalStackNode(++idCounter, containerId, endNode, child, optional.name, wrappedSymbol.beforeFilters, wrappedSymbol.afterFilters);
			}else if(wrappedSymbol instanceof PlusList){
				PlusList plusList = (PlusList) wrappedSymbol;
				
				AbstractStackNode child = buildStackNode(plusList.symbol, true);
				AbstractSymbol[] separatorSymbols = plusList.separators;
				if(separatorSymbols.length == 0){
					return new ListStackNode(++idCounter, containerId, endNode, child, plusList.name, true, wrappedSymbol.beforeFilters, wrappedSymbol.afterFilters);
				}
				
				int numberOfSeparators = separatorSymbols.length;
				AbstractStackNode[] separators = new AbstractStackNode[numberOfSeparators];
				for(int i = 0; i < numberOfSeparators; ++i){
					separators[i] = buildStackNode(separatorSymbols[i], false);
					separators[i].markAsSeparator();
				}
				
				return new SeparatedListStackNode(++idCounter, containerId, endNode, child, separators, plusList.name, true, wrappedSymbol.beforeFilters, wrappedSymbol.afterFilters);
			}else if(wrappedSymbol instanceof StarList){
				StarList starList = (StarList) wrappedSymbol;
				
				AbstractStackNode child = buildStackNode(starList.symbol, true);
				AbstractSymbol[] separatorSymbols = starList.separators;
				if(separatorSymbols.length == 0){
					return new ListStackNode(++idCounter, containerId, endNode, child, starList.name, false, wrappedSymbol.beforeFilters, wrappedSymbol.afterFilters);
				}
				
				int numberOfSeparators = separatorSymbols.length;
				AbstractStackNode[] separators = new AbstractStackNode[numberOfSeparators];
				for(int i = 0; i < numberOfSeparators; ++i){
					separators[i] = buildStackNode(separatorSymbols[i], false);
					separators[i].markAsSeparator();
				}
				
				return new SeparatedListStackNode(++idCounter, containerId, endNode, child, separators, starList.name, false, wrappedSymbol.beforeFilters, wrappedSymbol.afterFilters);
			}else if(wrappedSymbol instanceof Choice){
				Choice choice = (Choice) wrappedSymbol;
				
				AbstractSymbol[] childSymbols = choice.symbols;
				int numberOfChildren = childSymbols.length;
				AbstractStackNode[] children = new AbstractStackNode[numberOfChildren];
				for(int i = 0; i < numberOfChildren; ++i){
					children[i] = buildStackNode(childSymbols[i], true);
				}
				
				return new ChoiceStackNode(++idCounter, containerId, endNode, children, choice.name, wrappedSymbol.beforeFilters, wrappedSymbol.afterFilters);
			}else if(wrappedSymbol instanceof Sequence){
				Sequence sequence = (Sequence) wrappedSymbol;
				
				AbstractSymbol[] childSymbols = sequence.symbols;
				int numberOfChildren = childSymbols.length;
				AbstractStackNode[] children = new AbstractStackNode[numberOfChildren];
				for(int i = 0; i < numberOfChildren - 1; ++i){
					children[i] = buildStackNode(childSymbols[i], false);
				}
				children[numberOfChildren - 1] = buildStackNode(childSymbols[numberOfChildren - 1], true);
				
				return new SequenceStackNode(++idCounter, containerId, endNode, children, sequence.name, wrappedSymbol.beforeFilters, wrappedSymbol.afterFilters);
			}else{
				throw new RuntimeException(String.format("Unsupported identified symbol type: %s", wrappedSymbol.getClass().toString()));
			}
		}else{
			throw new RuntimeException(String.format("Unsupported symbol type: %s", symbol.getClass().toString()));
		}
	}
	
	private void addContinuations(AbstractStackNode node, ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> continuations){
		for(int i = continuations.size() - 1; i >= 0; --i){
			MarkableForwardSplittingLink<AbstractSymbol> continuation = continuations.get(i);
			AbstractStackNode nextNode = buildStackNode(continuation.element, continuation.mark);
			node.addNext(nextNode);
			addContinuations(nextNode, continuation.nexts);
		}
	}
	
	private AbstractStackNode[] transformToStackNodes(ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> expectTree){
		int numberOfExpects = expectTree.size();
		AbstractStackNode[] expects = new AbstractStackNode[numberOfExpects];
		
		for(int i = numberOfExpects - 1; i >= 0; --i){
			MarkableForwardSplittingLink<AbstractSymbol> alternativeTreeRoot = expectTree.get(i);
			AbstractStackNode node = buildStackNode(alternativeTreeRoot.element, alternativeTreeRoot.mark);
			addContinuations(node, alternativeTreeRoot.nexts);
			
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
	
	public AbstractStackNode[] buildExpects(){
		SortedIntegerObjectList<ArrayList<Alternative>> sortedAlternatives = sortAlternativesByLength(alternatives);
		
		ArrayList<MarkableForwardSplittingLink<AbstractSymbol>> expectTree = calculateSharing(sortedAlternatives);
		
		return transformToStackNodes(expectTree);
	}
}
