package gtd.stack.filter;

public interface IAfterFilter extends IFilter{
	boolean isFiltered(char[] input, int start, int end);
}
