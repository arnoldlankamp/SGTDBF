package gtd.tests;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.IStructure;
import gtd.grammar.structure.Scope;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.RSort;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.TLSort;
import gtd.result.AbstractNode;

public class ScopedWithTopLevelSortReference{
	
	public static IStructure[] S(){
		return new IStructure[]{
			new Alternative(new Sort("A"))
		};
	}
	
	public static IStructure[] A(){
		return new IStructure[]{
			new Alternative(new Sort("A"), new Char('+'), new RSort("A")),
			new Scope(
				new Alternative(new Sort("A"), new Char('*'), new RSort("A")),
				new Alternative(new Char('|'), new TLSort("A"), new Char('|')),
				new Alternative(new Char('a'))
			)
		};
	}
	
	public static void main(String[] args){
		ParserStructure structure = new FromClassGenerator(ScopedWithTopLevelSortReference.class).generate();
		Parser swtlsr = new Parser("a*|a+a|*a".toCharArray(), structure);
		AbstractNode result = swtlsr.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(A(A('a'),'*',A('|',A(A('a'),'+',A('a')),'|')),'*',A('a'))) <- good");
	}
}
