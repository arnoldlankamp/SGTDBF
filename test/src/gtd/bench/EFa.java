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
S ::= E
E ::= E + F | F
F ::= a | ( E )
*/
public class EFa{
	
	public static Alternative[] S(){
		return new Alternative[]{
			new Alternative(new Sort("E"))
		};
	}
	
	public static Alternative[] E(){
		return new Alternative[]{
			new Alternative(new Sort("E"), new Char('+'), new Sort("F")),
			new Alternative(new Sort("F"))
		};
	}
	
	public static Alternative[] F(){
		return new Alternative[]{
			new Alternative(new Char('a')),
			new Alternative(new Char('('), new Sort("E"), new Char(')'))
		};
	}
	
	private final static int ITERATIONS = 3;
	
	private static char[] createInput(int size){
		int depth = (size - 3) / 4;
		
		char[] input = new char[((depth + 1) * 4) + 3];
		int index = 0;
		
		input[index++] = 'a';
		input[index++] = '+';
		
		for(int i = depth; i >= 0; --i){
			input[index++] = '(';
			input[index++] = 'a';
			input[index++] = '+';
		}
		
		input[index++] = 'a';
		
		for(int i = depth; i >= 0; --i){
			input[index++] = ')';
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
		long totalReal = 0;
		long lowest = Long.MAX_VALUE;
		long lowestReal = Long.MAX_VALUE;
		for(int i = ITERATIONS - 1; i >= 0; --i){
			cleanup();
			
			long startReal = System.nanoTime();
			long start = tmxb.getCurrentThreadCpuTime();
			Parser eFa = new Parser(input, structure);
			eFa.parse("S");
			long end = tmxb.getCurrentThreadCpuTime();
			long endReal = System.nanoTime();
			
			long time = (end - start) / 1000000;
			long timeReal = (endReal - startReal) / 1000000;
			total += time;
			totalReal += timeReal;
			lowest = (time < lowest) ? time : lowest;
			lowestReal = (timeReal < lowestReal) ? timeReal : lowestReal;
		}
		System.out.println(input.length+": avgCPU="+(total / ITERATIONS)+"ms, lowestCPU="+lowest+"ms");
		System.out.println(input.length+": avgReal="+(totalReal / ITERATIONS)+"ms, lowestReal="+lowestReal+"ms");
	}
	
	public static void main(String[] args) throws Exception{
		ParserStructure structure = new FromClassGenerator(EFa.class).generate();
		
		char[] input = createInput(5);
		
		Parser testOut = new Parser(input, structure);
		if(testOut.parse("S") != null) System.out.println("WARNING: Running in parser instead of recognizer mode.");
		
		// Warmup.
		for(int i = 9999; i >= 0; --i){
			Parser eFa = new Parser(input, structure);
			eFa.parse("S");
		}
		
		for(int i = 200001; i <= 1000001; i += 200000){
			input = createInput(i);
			Parser eFa = new Parser(input, structure);
			eFa.parse("S");
		}
		
		// The benchmarks.
		for(int i = 200001; i <= 1000001; i += 200000){
			input = createInput(i);
			runTest(input, structure);
		}
	}
}
