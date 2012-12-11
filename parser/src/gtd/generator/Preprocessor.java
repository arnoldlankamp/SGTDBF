package gtd.generator;

import gtd.grammar.structure.Alternative;
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
	
	private AbstractStackNode buildStackNode(AbstractSymbol symbol, boolean endNode){
		if(symbol instanceof Sort){
			return new NonTerminalStackNode(++idCounter, endNode, ((Sort) symbol).sortName);
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
			AbstractStackNode child = buildStackNode(optional.symbol, true);
			return new OptionalStackNode(++idCounter, endNode, child, optional.name);
		}else if(symbol instanceof PlusList){
			PlusList plusList = (PlusList) symbol;
			AbstractStackNode child = buildStackNode(plusList.symbol, true);
			AbstractSymbol[] separatorSymbols = plusList.separators;
			if(separatorSymbols == null || separatorSymbols.length == 0){
				return new ListStackNode(++idCounter, endNode, child, plusList.name, true);
			}
			
			int numberOfSeparators = separatorSymbols.length;
			AbstractStackNode[] separators = new AbstractStackNode[numberOfSeparators];
			for(int i = 0; i < numberOfSeparators; ++i){
				separators[i] = buildStackNode(separatorSymbols[i], false);
				separators[i].markAsSeparator();
			}
			
			return new SeparatedListStackNode(++idCounter, endNode, child, separators, plusList.name, true);
		}else if(symbol instanceof StarList){
			StarList starList = (StarList) symbol;
			AbstractStackNode child = buildStackNode(starList.symbol, true);
			AbstractSymbol[] separatorSymbols = starList.separators;
			if(separatorSymbols == null || separatorSymbols.length == 0){
				return new ListStackNode(++idCounter, endNode, child, starList.name, false);
			}
			
			int numberOfSeparators = separatorSymbols.length;
			AbstractStackNode[] separators = new AbstractStackNode[numberOfSeparators];
			for(int i = 0; i < numberOfSeparators; ++i){
				separators[i] = buildStackNode(separatorSymbols[i], false);
				separators[i].markAsSeparator();
			}
			
			return new SeparatedListStackNode(++idCounter, endNode, child, separators, starList.name, false);
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
