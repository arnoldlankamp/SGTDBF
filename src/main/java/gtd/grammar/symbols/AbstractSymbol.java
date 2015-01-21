package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;
import gtd.stack.filter.after.CharFollowRequirement;
import gtd.stack.filter.after.CharFollowRestriction;
import gtd.stack.filter.after.CharMatchRestriction;
import gtd.stack.filter.after.CharPrefixRequirement;
import gtd.stack.filter.after.CharPrefixRestriction;
import gtd.stack.filter.after.CharRangeFollowRequirement;
import gtd.stack.filter.after.CharRangeFollowRestriction;
import gtd.stack.filter.after.CharRangePrefixRequirement;
import gtd.stack.filter.after.CharRangePrefixRestriction;
import gtd.stack.filter.after.StringFollowRequirement;
import gtd.stack.filter.after.StringFollowRestriction;
import gtd.stack.filter.after.StringMatchRestriction;
import gtd.stack.filter.before.CharRangePrecedeRequirement;
import gtd.stack.filter.before.CharRangePrecedeRestriction;
import gtd.stack.filter.before.StringPrecedeRequirement;
import gtd.stack.filter.before.StringPrecedeRestriction;

public abstract class AbstractSymbol{
	public final String name;
	public final IBeforeFilter[] beforeFilters;
	public final IAfterFilter[] afterFilters;

	public AbstractSymbol(String name, IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters){
		super();
		
		this.name = name;
		this.beforeFilters = beforeFilters;
		this.afterFilters = afterFilters;
	}
	
	protected abstract AbstractSymbol cloneWithFilters(IBeforeFilter[] beforeFilters, IAfterFilter[] afterFilters);
	
	public AbstractSymbol withBeforeFilters(IBeforeFilter... beforeFilters){
		IBeforeFilter[] newBeforeFilters = beforeFilters;
		if(this.beforeFilters != null){
			int nrOfExistingFilters = this.beforeFilters.length;
			int nrOfAdditionalFilters = beforeFilters.length;
			newBeforeFilters = new IBeforeFilter[nrOfExistingFilters + nrOfAdditionalFilters];
			System.arraycopy(this.beforeFilters, 0, newBeforeFilters, 0, nrOfExistingFilters);
			System.arraycopy(beforeFilters, 0, newBeforeFilters, nrOfExistingFilters, nrOfAdditionalFilters);
		}
		
		return cloneWithFilters(newBeforeFilters, afterFilters);
	}
	
	public AbstractSymbol withAfterFilters(IAfterFilter... afterFilters){
		IAfterFilter[] newAfterFilters = afterFilters;
		if(this.afterFilters != null){
			int nrOfExistingFilters = this.afterFilters.length;
			int nrOfAdditionalFilters = afterFilters.length;
			newAfterFilters = new IAfterFilter[nrOfExistingFilters + nrOfAdditionalFilters];
			System.arraycopy(this.afterFilters, 0, newAfterFilters, 0, nrOfExistingFilters);
			System.arraycopy(afterFilters, 0, newAfterFilters, nrOfExistingFilters, nrOfAdditionalFilters);
		}
		
		return cloneWithFilters(beforeFilters, newAfterFilters);
	}
	
	public String toString(){
		return name;
	}
	
	public boolean hasEqualFilters(AbstractSymbol symbol){
		if(symbol == this) return true;
		if(symbol == null) return false;
		
		if(beforeFilters != null){
			if(symbol.beforeFilters == null) return false;
			if(symbol.beforeFilters.length != beforeFilters.length) return false;
			
			for(int i = beforeFilters.length - 1; i >= 0; --i){
				if(!beforeFilters[i].equals(symbol.beforeFilters[i])) return false;
			}
		}else if(symbol.beforeFilters != null){
			return false;
		}
		
		if(afterFilters != null){
			if(symbol.afterFilters == null) return false;
			if(symbol.afterFilters.length != afterFilters.length) return false;
			
			for(int i = afterFilters.length - 1; i >= 0; --i){
				if(!afterFilters[i].equals(symbol.afterFilters[i])) return false;
			}
		}else if(symbol.afterFilters != null){
			return false;
		}
		
		return true;
	}
	
	// Convenience methods
	public AbstractSymbol precededBy(char start, char end) {
		return withBeforeFilters(new CharRangePrecedeRequirement(start, end));
	}
	
	public AbstractSymbol precededBy(String string) {
		return withBeforeFilters(new StringPrecedeRequirement(string));
	}
	
	public AbstractSymbol notPrecededBy(char start, char end) {
		return withBeforeFilters(new CharRangePrecedeRestriction(start, end));
	}
	
	public AbstractSymbol notPrecededBy(String string) {
		return withBeforeFilters(new StringPrecedeRestriction(string));
	}
	
	public AbstractSymbol startingWith(char character) {
		return withAfterFilters(new CharPrefixRequirement(character));
	}
	
	public AbstractSymbol startingWith(char start, char end) {
		return withAfterFilters(new CharRangePrefixRequirement(start, end));
	}
	
	public AbstractSymbol notStartingWith(char character) {
		return withAfterFilters(new CharPrefixRestriction(character));
	}
	
	public AbstractSymbol notStartingWith(char start, char end) {
		return withAfterFilters(new CharRangePrefixRestriction(start, end));
	}
	
	public AbstractSymbol followedBy(char character) {
		return withAfterFilters(new CharFollowRequirement(character));
	}
	
	public AbstractSymbol followedBy(char start, char end) {
		return withAfterFilters(new CharRangeFollowRequirement(start, end));
	}
	
	public AbstractSymbol followedBy(String string) {
		return withAfterFilters(new StringFollowRequirement(string));
	}
	
	public AbstractSymbol notFollowedBy(char character) {
		return withAfterFilters(new CharFollowRestriction(character));
	}
	
	public AbstractSymbol notFollowedBy(char start, char end) {
		return withAfterFilters(new CharRangeFollowRestriction(start, end));
	}
	
	public AbstractSymbol notFollowedBy(String string) {
		return withAfterFilters(new StringFollowRestriction(string));
	}
	
	public AbstractSymbol excluding(char character) {
		return withAfterFilters(new CharMatchRestriction(character));
	}
	
	public AbstractSymbol excluding(String string) {
		return withAfterFilters(new StringMatchRestriction(string));
	}
}
