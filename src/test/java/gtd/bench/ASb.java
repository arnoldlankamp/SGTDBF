package gtd.bench;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Sort;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/*
S ::= aSb | a 
*/
public class ASb{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Char('a'), new Sort("S"), new Char('b')),
			new Alternative(new Char('a'))
		};
	}
	
	private final static int ITERATIONS = 3;
	
	private static char[] createInput(int size){
		char[] input = new char[size];
		for(int i = size - 1; i >= size / 2; --i){
			input[i] = 'b';
		}
		for(int i = (size / 2); i >= 0; --i){
			input[i] = 'a';
		}
		
		
		return input;
	}
	
	private static void cleanup() throws Exception{
		System.gc();
		System.gc();
		System.gc();
		System.gc();
		System.gc();
		Thread.sleep(1000);
	}
	
	private static void runTest(char[] input, ParserStructure structure) throws Exception{
		ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
		
		long total = 0;
		long lowest = Long.MAX_VALUE;
		for(int i = ITERATIONS - 1; i >= 0; --i){
			cleanup();
			
			long start = tmxb.getCurrentThreadCpuTime();
			Parser aSb = new Parser(input, structure);
			aSb.parse("S");
			long end = tmxb.getCurrentThreadCpuTime();
			
			long time = (end - start) / 1000000;
			total += time;
			lowest = (time < lowest) ? time : lowest;
		}
		System.out.println(input.length+": avg="+(total / ITERATIONS)+"ms, lowest="+lowest+"ms");
	}
	
	public static void main(String[] args) throws Exception{
		ParserStructure structure = new FromClassGenerator(ASb.class).generate();
		
		// Warmup.
		char[] input = createInput(5);
		
		for(int i = 9999; i >= 0; --i){
			Parser aSb = new Parser(input, structure);
			aSb.parse("S");
		}
		
		// The benchmarks.
		for(int i = 50001; i <= 2000001; i += 50000){
			input = createInput(i);
			runTest(input, structure);
		}
	}
}
