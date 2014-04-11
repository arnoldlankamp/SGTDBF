package gtd.grammar.symbols;

import gtd.stack.filter.IAfterFilter;
import gtd.stack.filter.IBeforeFilter;

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
}
