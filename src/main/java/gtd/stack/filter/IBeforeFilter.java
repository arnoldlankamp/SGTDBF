package gtd.stack.filter;

public interface IBeforeFilter extends IFilter{
	boolean isFiltered(char[] input, int start);
}
