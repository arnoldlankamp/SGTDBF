package gtd.generator;

import gtd.generator.IdentifiedSymbol.Key;
import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.IStructure;
import gtd.grammar.structure.Scope;
import gtd.grammar.symbols.AbstractConstruct;
import gtd.grammar.symbols.AbstractSymbol;
import gtd.grammar.symbols.Choice;
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
	private final ArrayList<Key> containerIndexMap;
	private int scopeIndexCounter;
	
	public GrammarEncoder(ArrayList<Key> containerIndexMap) {
		super();
		
		this.containerIndexMap = containerIndexMap;
		this.scopeIndexCounter = 0;
	}
	
	private int getContainerIndex(Key identifiedSymbolKey){
		int sortIndex = containerIndexMap.find(identifiedSymbolKey);
		if(sortIndex == -1){
			sortIndex = containerIndexMap.size();
			containerIndexMap.add(identifiedSymbolKey);
		}
		return sortIndex;
	}
	
	private int getNextFreeScopeIndex(){
		return ++scopeIndexCounter;
	}
	
	private static boolean isSelfRecursiveSort(AbstractSymbol identifiedSymbol, String sortName){
		if(identifiedSymbol instanceof IdentifiedSymbol){
			AbstractSymbol symbol = ((IdentifiedSymbol) identifiedSymbol).symbol;
			if(symbol instanceof Sort){
				if(symbol instanceof TLSort) return false;
				
				Sort sort = (Sort) symbol;
				return sort.sortName.equals(sortName);
			}
		}
		return false;
	}
	
	private static boolean containsSelfRecursiveSort(AbstractSymbol[] symbols, String sortName){
		for(int i = symbols.length - 1; i >= 0; --i){
			if(isSelfRecursiveSort(symbols[i], sortName)) return true;
		}
		return false;
	}
	
	private static boolean isRestrictedSort(AbstractSymbol symbol){
		if(symbol instanceof IdentifiedSymbol){
			return ((IdentifiedSymbol) symbol).symbol instanceof RSort;
		}
		return symbol instanceof RSort;
	}
	
	private static boolean containsRestrictedSort(AbstractSymbol[] symbols){
		for(int i = symbols.length - 1; i >= 0; --i){
			if(isRestrictedSort(symbols[i])) return true;
		}
		return false;
	}
	
	private IdentifiedSymbol identifyConstruct(AbstractConstruct construct, String sortName, int scopeId){
		if(construct instanceof Optional){
			Optional optional = (Optional) construct;
			AbstractSymbol symbol = optional.symbol;
			AbstractSymbol identifiedSymbol = identifySymbol(symbol, sortName, scopeId);
			Optional identifiedOptional = new Optional(identifiedSymbol);
			boolean containsRestrictedSort = isRestrictedSort(symbol);
			boolean containsSelfRecursiveSort = isSelfRecursiveSort(identifiedSymbol, sortName);
			return new IdentifiedSymbol(identifiedOptional, getContainerIndex(new Key(optional.name, containsSelfRecursiveSort ? scopeId : 0, containsRestrictedSort)), containsRestrictedSort);
		}else if(construct instanceof PlusList){
			PlusList plusList = (PlusList) construct;
			AbstractSymbol symbol = plusList.symbol;
			AbstractSymbol identifiedSymbol = identifySymbol(symbol, sortName, scopeId);
			AbstractSymbol[] separators = plusList.separators;
			AbstractSymbol[] identifiedSeparators = identifySymbols(separators, sortName, scopeId);
			
			PlusList identifiedPlusList = (PlusList) new PlusList(identifiedSymbol, identifiedSeparators).withBeforeFilters(plusList.beforeFilters).withAfterFilters(plusList.afterFilters);
			boolean containsRestrictedSort = isRestrictedSort(symbol) || containsRestrictedSort(separators);
			boolean containsSelfRecursiveSort = isSelfRecursiveSort(identifiedSymbol, sortName) || containsSelfRecursiveSort(identifiedSeparators, sortName);
			return new IdentifiedSymbol(identifiedPlusList, getContainerIndex(new Key(plusList.name, containsSelfRecursiveSort ? scopeId : 0, containsRestrictedSort)), containsRestrictedSort);
		}else if(construct instanceof StarList){
			StarList starList = (StarList) construct;
			AbstractSymbol symbol = starList.symbol;
			AbstractSymbol identifiedSymbol = identifySymbol(symbol, sortName, scopeId);
			AbstractSymbol[] separators = starList.separators;
			AbstractSymbol[] identifiedSeparators = identifySymbols(separators, sortName, scopeId);
			
			StarList identifiedStarList = (StarList) new StarList(identifiedSymbol, identifiedSeparators).withBeforeFilters(starList.beforeFilters).withAfterFilters(starList.afterFilters);
			boolean containsRestrictedSort = isRestrictedSort(symbol) || containsRestrictedSort(separators);
			boolean containsSelfRecursiveSort = isSelfRecursiveSort(identifiedSymbol, sortName) || containsSelfRecursiveSort(identifiedSeparators, sortName);
			return new IdentifiedSymbol(identifiedStarList, getContainerIndex(new Key(starList.name, containsSelfRecursiveSort ? scopeId : 0, containsRestrictedSort)), containsRestrictedSort);
		}else if(construct instanceof Choice){
			Choice choice = (Choice) construct;
			AbstractSymbol[] symbols = choice.symbols;
			AbstractSymbol[] identifiedSymbols = identifySymbols(symbols, sortName, scopeId);
			
			Choice identifiedChoice = new Choice(identifiedSymbols);
			boolean containsRestrictedSort = containsRestrictedSort(symbols);
			boolean containsSelfRecursiveSort = containsSelfRecursiveSort(identifiedSymbols, sortName);
			return new IdentifiedSymbol(identifiedChoice, getContainerIndex(new Key(choice.name, containsSelfRecursiveSort ? scopeId : 0, containsRestrictedSort)), containsRestrictedSort);
		}else if(construct instanceof Sequence){
			Sequence sequence = (Sequence) construct;
			AbstractSymbol[] symbols = sequence.symbols;
			AbstractSymbol[] identifiedSymbols = identifySymbols(symbols, sortName, scopeId);
			
			Sequence identifiedSequence = new Sequence(identifiedSymbols);
			boolean containsRestrictedSort = containsRestrictedSort(symbols);
			boolean containsSelfRecursiveSort = containsSelfRecursiveSort(identifiedSymbols, sortName);
			return new IdentifiedSymbol(identifiedSequence, getContainerIndex(new Key(sequence.name, containsSelfRecursiveSort ? scopeId : 0, containsRestrictedSort)), containsRestrictedSort);
		}else{
			throw new RuntimeException(String.format("Unsupported construct type: %s", construct.getClass().toString()));
		}
	}
	
	private AbstractSymbol identifySymbol(AbstractSymbol symbol, String sortName, int scopeId){
		if(symbol instanceof Sort){
			Sort sort = (Sort) symbol;
			if(sort.sortName.equals(sortName)){
				if(sort instanceof RSort){
					return new IdentifiedSymbol(sort, getContainerIndex(new Key(sort.name, scopeId, true)), true);
				}else if(sort instanceof TLSort){
					return new IdentifiedSymbol(sort, getContainerIndex(new Key(sort.name, 0, false)), false);
				}
				return new IdentifiedSymbol(sort, getContainerIndex(new Key(sort.name, scopeId, false)), false);
			}else{
				if(sort instanceof RSort){
					throw new RuntimeException(String.format("Encountered a sort of type '%s' inside a rule of type '%s'. Restricted sorts with a type other then that of the rule itself make no sense.", sort.sortName, sortName));
				}
				return new IdentifiedSymbol(sort, getContainerIndex(new Key(sort.name, 0, false)), false);
			}
		}else if(symbol instanceof AbstractConstruct){
			return identifyConstruct((AbstractConstruct) symbol, sortName, scopeId);
		}
		return symbol;
	}
	
	private AbstractSymbol[] identifySymbols(AbstractSymbol[] symbols, String sortName, int scopeId){
		AbstractSymbol[] newSymbols = new AbstractSymbol[symbols.length];
		for(int i = symbols.length - 1; i >= 0; --i){
			newSymbols[i] = identifySymbol(symbols[i], sortName, scopeId);
		}
		return newSymbols;
	}
	
	private Alternative identifySortsAndConstructs(String sortName, int scopeId, Alternative alternative){
		return new Alternative(identifySymbols(alternative.alternative, sortName, scopeId));
	}
	
	private ArrayList<Alternative> encodeAlternatives(String sortName, int scopeId, IStructure[] structures, IntegerKeyedHashMap<ArrayList<Alternative>> groupedAlternatives){
		ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
		ArrayList<Alternative> nonRestrictedSelfRecursiveAlternatives = new ArrayList<Alternative>();
		boolean restrictedSortEncountered = false;
		for(int i = structures.length - 1; i >= 0; --i){
			IStructure structure = structures[i];
			if(structure instanceof Scope){
				ArrayList<Alternative> scopedAlternatives = encodeAlternatives(sortName, getNextFreeScopeIndex(), ((Scope) structure).alternatives, groupedAlternatives);
				Object[] scopedAlternativesBackingArray = scopedAlternatives.getBackingArray();
				alternatives.addFromArray(scopedAlternativesBackingArray, 0, scopedAlternatives.size());
				nonRestrictedSelfRecursiveAlternatives.addFromArray(scopedAlternativesBackingArray, 0, scopedAlternatives.size());
			}else{
				Alternative alternative = (Alternative) structure;
				Alternative identifiedAlternative = identifySortsAndConstructs(sortName, scopeId, alternative);
				alternatives.add(identifiedAlternative);
				if(!containsSelfRecursiveSort(identifiedAlternative.alternative, sortName)){
					nonRestrictedSelfRecursiveAlternatives.add(identifiedAlternative);
				}else if(containsRestrictedSort(identifiedAlternative.alternative)){
					restrictedSortEncountered = true;
				}
			}
		}
		
		groupedAlternatives.putUnsafe(getContainerIndex(new Key(sortName, scopeId, false)), alternatives);
		if(restrictedSortEncountered) groupedAlternatives.putUnsafe(getContainerIndex(new Key(sortName, scopeId, true)), nonRestrictedSelfRecursiveAlternatives);
		
		return alternatives;
	}
	
	public IntegerKeyedHashMap<ArrayList<Alternative>> flatten(String sortName, IStructure[] structures){
		IntegerKeyedHashMap<ArrayList<Alternative>> groupedAlternatives = new IntegerKeyedHashMap<ArrayList<Alternative>>();
		
		encodeAlternatives(sortName, 0, structures, groupedAlternatives);
		
		return groupedAlternatives;
	}
}
