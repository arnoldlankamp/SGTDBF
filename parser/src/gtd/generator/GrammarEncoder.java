package gtd.generator;

import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.IStructure;
import gtd.grammar.structure.Scope;
import gtd.grammar.symbols.AbstractConstruct;
import gtd.grammar.symbols.AbstractSymbol;
import gtd.grammar.symbols.CILiteral;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.Choice;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.Optional;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.RSort;
import gtd.grammar.symbols.Sequence;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.StarList;
import gtd.grammar.symbols.TLSort;
import gtd.util.ArrayList;
import gtd.util.IntegerKeyedHashMap;

public class GrammarEncoder{
	private final ArrayList<String> sortIndexMap;
	
	public GrammarEncoder(ArrayList<String> sortIndexMap) {
		super();
		
		this.sortIndexMap = sortIndexMap;
	}
	
	private int getContainerIndex(String sortName){
		int sortIndex = sortIndexMap.find(sortName);
		if(sortIndex == -1){
			sortIndex = sortIndexMap.size();
			sortIndexMap.add(sortName);
		}
		return sortIndex;
	}
	
	private int getNextFreeSortIndex() {
		int sortIndex = sortIndexMap.size();
		sortIndexMap.add(null);
		return sortIndex;
	}
	
	private static PlusList constructIdentifiedPlusList(AbstractSymbol idenfitiedSymbol, AbstractSymbol[] identifiedSeparators){
		if(idenfitiedSymbol instanceof Char){
			return new PlusList((Char) idenfitiedSymbol, identifiedSeparators);
		}else if(idenfitiedSymbol instanceof CharRange){
			return new PlusList((CharRange) idenfitiedSymbol, identifiedSeparators);
		}else if(idenfitiedSymbol instanceof Literal){
			return new PlusList((Literal) idenfitiedSymbol, identifiedSeparators);
		}else if(idenfitiedSymbol instanceof CILiteral){
			return new PlusList((CILiteral) idenfitiedSymbol, identifiedSeparators);
		}else if(idenfitiedSymbol instanceof IdentifiedSymbol){
			return new PlusList((IdentifiedSymbol) idenfitiedSymbol, identifiedSeparators);
		}else{
			throw new RuntimeException(String.format("Unsupported plus list symbol type: %s", idenfitiedSymbol.getClass().toString()));
		}
	}
	
	private static StarList constructIdentifiedStarList(AbstractSymbol idenfitiedSymbol, AbstractSymbol[] identifiedSeparators){
		if(idenfitiedSymbol instanceof Char){
			return new StarList((Char) idenfitiedSymbol, identifiedSeparators);
		}else if(idenfitiedSymbol instanceof CharRange){
			return new StarList((CharRange) idenfitiedSymbol, identifiedSeparators);
		}else if(idenfitiedSymbol instanceof Literal){
			return new StarList((Literal) idenfitiedSymbol, identifiedSeparators);
		}else if(idenfitiedSymbol instanceof CILiteral){
			return new StarList((CILiteral) idenfitiedSymbol, identifiedSeparators);
		}else if(idenfitiedSymbol instanceof IdentifiedSymbol){
			return new StarList((IdentifiedSymbol) idenfitiedSymbol, identifiedSeparators);
		}else{
			throw new RuntimeException(String.format("Unsupported star list symbol type: %s", idenfitiedSymbol.getClass().toString()));
		}
	}
	
	private static boolean isRestrictedSort(AbstractSymbol symbol){
		if(symbol instanceof IdentifiedSymbol){
			return ((IdentifiedSymbol) symbol).restricted;
		}
		return false;
	}
	
	private static boolean containsRestrictedSorts(AbstractSymbol[] symbols){
		for(int i = symbols.length - 1; i >= 0; --i){
			if(isRestrictedSort(symbols[i])) return true;
		}
		return false;
	}
	
	private IdentifiedSymbol identifyConstruct(AbstractConstruct construct, String sortName, int scopedSortId, int restrictedSortIndex){
		if(construct instanceof Optional){
			Optional optional = (Optional) construct;
			AbstractSymbol optionalSymbol = identifySymbol(optional.symbol, sortName, scopedSortId, restrictedSortIndex);
			Optional identifiedOptional = new Optional(optionalSymbol);
			return new IdentifiedSymbol(identifiedOptional , getContainerIndex(optional.name), isRestrictedSort(optionalSymbol));
		}else if(construct instanceof PlusList){
			PlusList plusList = (PlusList) construct;
			AbstractSymbol symbol = plusList.symbol;
			AbstractSymbol identifiedSymbol = identifySymbol(symbol, sortName, scopedSortId, restrictedSortIndex);
			AbstractSymbol[] identifiedSeparators = identifySymbols(plusList.separators, sortName, scopedSortId, restrictedSortIndex);
			
			PlusList identifiedPlusList = constructIdentifiedPlusList(identifiedSymbol, identifiedSeparators);
			return new IdentifiedSymbol(identifiedPlusList, getContainerIndex(plusList.name), isRestrictedSort(identifiedSymbol) || containsRestrictedSorts(identifiedSeparators));
		}else if(construct instanceof StarList){
			StarList starList = (StarList) construct;
			AbstractSymbol symbol = starList.symbol;
			AbstractSymbol identifiedSymbol = identifySymbol(symbol, sortName, scopedSortId, restrictedSortIndex);
			AbstractSymbol[] identifiedSeparators = identifySymbols(starList.separators, sortName, scopedSortId, restrictedSortIndex);
			
			StarList identifiedStarList = constructIdentifiedStarList(identifiedSymbol, identifiedSeparators);
			return new IdentifiedSymbol(identifiedStarList, getContainerIndex(starList.name), isRestrictedSort(identifiedSymbol) || containsRestrictedSorts(identifiedSeparators));
		}else if(construct instanceof Choice){
			Choice choice = (Choice) construct;
			AbstractSymbol[] identifiedSymbols = identifySymbols(choice.symbols, sortName, scopedSortId, restrictedSortIndex);
			
			Choice identifiedChoice = new Choice(identifiedSymbols);
			return new IdentifiedSymbol(identifiedChoice, getContainerIndex(choice.name), containsRestrictedSorts(identifiedSymbols));
		}else if(construct instanceof Sequence){
			Sequence sequence = (Sequence) construct;
			AbstractSymbol[] identifiedSymbols = identifySymbols(sequence.symbols, sortName, scopedSortId, restrictedSortIndex);
			
			Sequence identifiedSequence = new Sequence(identifiedSymbols);
			return new IdentifiedSymbol(identifiedSequence, getContainerIndex(sequence.name), containsRestrictedSorts(identifiedSymbols));
		}else{
			throw new RuntimeException(String.format("Unsupported construct type: %s", construct.getClass().toString()));
		}
	}
	
	private AbstractSymbol identifySymbol(AbstractSymbol symbol, String sortName, int scopedSortId, int restrictedSortIndex){
		if(symbol instanceof Sort){
			Sort sort = (Sort) symbol;
			if(sort.sortName.equals(sortName)){
				if(sort instanceof RSort){
					return new IdentifiedSymbol(sort, restrictedSortIndex, true);
				}else if(sort instanceof TLSort){
					return new IdentifiedSymbol(sort, getContainerIndex(sort.sortName), false);
				}
				return new IdentifiedSymbol(sort, scopedSortId, false);
			}else{
				return new IdentifiedSymbol(sort, getContainerIndex(sort.sortName), false);
			}
		}else if(symbol instanceof AbstractConstruct){
			return identifyConstruct((AbstractConstruct) symbol, sortName, scopedSortId, restrictedSortIndex);
		}
		return symbol;
	}
	
	private AbstractSymbol[] identifySymbols(AbstractSymbol[] symbols, String sortName, int scopedSortId, int restrictedSortIndex){
		AbstractSymbol[] newSymbols = new AbstractSymbol[symbols.length];
		for(int i = symbols.length - 1; i >= 0; --i){
			newSymbols[i] = identifySymbol(symbols[i], sortName, scopedSortId, restrictedSortIndex);
		}
		return newSymbols;
	}
	
	private Alternative identifySortsAndConstructs(String sortName, int scopedSortId, int restrictedSortIndex, Alternative alternative){
		return new Alternative(identifySymbols(alternative.alternative, sortName, scopedSortId, restrictedSortIndex));
	}
	
	private ArrayList<Alternative> encodeAlternatives(String sortName, int sortId, int restrictedSortIndex, IStructure[] structures, IntegerKeyedHashMap<ArrayList<Alternative>> groupedAlternatives){
		ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
		ArrayList<Alternative> nonRestrictedAlternatives =  new ArrayList<Alternative>();
		for(int i = structures.length - 1; i >= 0; --i){
			IStructure structure = structures[i];
			if(structure instanceof Scope){
				ArrayList<Alternative> scopedAlternatives = encodeAlternatives(sortName, getNextFreeSortIndex(), getNextFreeSortIndex(), ((Scope) structure).alternatives, groupedAlternatives);
				Object[] scopedAlternativesBackingArray = scopedAlternatives.getBackingArray();
				alternatives.addFromArray(scopedAlternativesBackingArray, 0, scopedAlternatives.size());
				for(int j = scopedAlternatives.size() - 1; j >= 0; --j){
					Alternative scopedAlternative = scopedAlternatives.get(j);
					if(!containsRestrictedSorts(scopedAlternative.alternative)){
						nonRestrictedAlternatives.add(scopedAlternative);
					}
				}
			}else{
				Alternative alternative = (Alternative) structure;
				Alternative identifiedAlternative = identifySortsAndConstructs(sortName, sortId, restrictedSortIndex, alternative);
				alternatives.add(identifiedAlternative);
				if(!containsRestrictedSorts(identifiedAlternative.alternative)){
					nonRestrictedAlternatives.add(identifiedAlternative);
				}
			}
		}
		
		groupedAlternatives.putUnsafe(sortId, alternatives);
		
		groupedAlternatives.putUnsafe(restrictedSortIndex, nonRestrictedAlternatives);
		
		return alternatives;
	}
	
	public IntegerKeyedHashMap<ArrayList<Alternative>> flatten(String sortName, IStructure[] structures){
		IntegerKeyedHashMap<ArrayList<Alternative>> groupedAlternatives = new IntegerKeyedHashMap<ArrayList<Alternative>>();
		
		encodeAlternatives(sortName, getContainerIndex(sortName), getNextFreeSortIndex(), structures, groupedAlternatives);
		
		return groupedAlternatives;
	}
}
