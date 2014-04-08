SGTDBF
======

This is the prototype implementation of the Scannerless General Top Down Binary Forest parser.


It is not based on any existing algorithm. But it basically works like a 'normal' top down parser, with the difference that it is able to handle ambiguity, attempts to recognize all possible alternatives in parallel (in a breadth-first way) and uses a graph to model the parse stacks (in which the nodes are maximally shared, so it can contain cycles). Besides that there is some trickery in there to handle nullable related nastiness (like hidden-right-recursion), to be able to support the full range of context-free grammars. Furthermore a lot of algorithmic and code optimizations were introduced into the implementation to make it blazingly fast, both in worst and general case scenarios.


It's still a work in progress and the code is fairly ... incomprehensible, so don't hurt yourself trying to read it ;-).


Scaling:
* O(N) for LL grammars.
* O(N) for LR grammars.
* O(N^3) worst case.
* Usually near O(N) on non-ambiguous or locally ambiguous grammars.


Performance:
* Not-all-that-much slower than commonly used non-general parsers written in Java (i.e. the overhead of being 'general' remains relatively in check).
* Somewhere between a lot and multiple orders of magnitude faster than most other general(ized) parsers, depending on the grammar. E.g. 16ms parse time for the 'S ::= SSS | SS | 'a' | epsilon' grammer @ 100 input chars, 396ms @ 300 input chars, 2.3 seconds @ 500 input char; vs somewhere in between 'my machine just exploded' and 'the sun has exploded and I'm still not done'.


Supported 'fancy' features:
* Native support for:
  * Star- and plus-lists.
  * Optionals.
  * Sequences.
  * Choices.
* Production prefix sharing
  * Overlapping productions are automatically merged for improved performance and scalability without affecting the produced AST.
* Build-in filtering support.
  * Scoping (e.g. for encoding priorities).
  * Restrictions (e.g. for encoding associativity).
  * Input based filtering (i.e. restrictions on preceding, following or matched characters), which can easily be extended by the user.


Wishlist:
  * A tree you can work with (right now it's binarized).
  * Better error reporting.
  * A cleaner, more advanced method of defining grammars.
  * Semantic actions to enable more advanced filtering.
  * Even better performance.


Usage:
TODO: Add some more information (like an overview of available constructs).

A basic example (see comments inline):
```java
package gtd.examples;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.Literal;
import gtd.grammar.symbols.PlusList;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class HelloWorld {

	public static Alternative[] Exclamation() {
		return new Alternative[] {
			new Alternative(new Sort("Word"), new PlusList(new Sort("Whitespace")), new Sort("Word"))
		};
	}

	public static Alternative[] Word() {
		return new Alternative[] {
			new Alternative(new Literal("Hello")),
			new Alternative(new Literal("world"))
		};
	}

	public static Alternative[] Whitespace() {
		return new Alternative[]{
			new Alternative(new Char(' '))
		};
	}

	public static void main(String[] args) {
		// Convert the given class into a for the parser usable format (this structure is reusable)
		ParserStructure structure = new FromClassGenerator(HelloWorld.class).generate();

		char[] input = "Hello world".toCharArray();
		// Construct a new parser instance for the given input string
		Parser parser = new Parser(input, structure);

		// Start the parser with the given sort as start symbol
		AbstractNode result = parser.parse("Exclamation");
		System.out.println(result);
	}
}
```

Scoping (can be used for encoding priorities for example):
```java
package gtd.examples;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.IStructure;
import gtd.grammar.structure.Scope;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.Sort;
import gtd.grammar.symbols.TLSort;
import gtd.result.AbstractNode;

public class Priority {

	public static IStructure[] Number() {
		return new IStructure[] {
			new Alternative(new CharRange('0', '9'))
		};
	}

	public static IStructure[] Expr() {
		return new IStructure[] {
			new Alternative(new Sort("Expr"), new Char('+'), new Sort("Expr")),
			new Scope( // Recursive sorts within a scope can only contain alternative from within that scope and scopes below it in the hierarchy
				new Alternative(new Sort("Expr"), new Char('*'), new Sort("Expr")),
				// The top-level version of the sort can be referenced from within a scope using TLSorts instead of 'normal' ones
				new Alternative(new Char('('), new TLSort("Expr"), new Char(')')),
				new Alternative(new Sort("Number"))
			)
		};
	}

	public static void main(String[] args) {
		ParserStructure structure = new FromClassGenerator(Priority.class).generate();

		char[] input = "1+2*(3*4+5)".toCharArray();
		Parser parser = new Parser(input, structure);

		AbstractNode result = parser.parse("Expr");
		System.out.println(result);
	}
}

```
Note that one can use the 'TLSort' type to reference the top level sort from within a scope. This can, for example, be useful in cases where an alternative has a higher priority, but is guarded on both ends of the production and thus does not lead to ambiguity (e.g. 'A ::= "(" A ")"').

Restricted sorts (can be used to encode associativity):
```java
package gtd.examples;

import gtd.Parser;
import gtd.generator.FromClassGenerator;
import gtd.generator.ParserStructure;
import gtd.grammar.structure.Alternative;
import gtd.grammar.structure.IStructure;
import gtd.grammar.symbols.Char;
import gtd.grammar.symbols.CharRange;
import gtd.grammar.symbols.RSort;
import gtd.grammar.symbols.Sort;
import gtd.result.AbstractNode;

public class Associativity {
	
	public static IStructure[] Number() {
		return new IStructure[] {
			new Alternative(new CharRange('0', '9'))
		};
	}

	public static IStructure[] Expr() {
		return new IStructure[] {
			// Restrict the nesting of the self recursive alternatives of this sort on the right side
			new Alternative(new Sort("Expr"), new Char('+'), new RSort("Expr")),
			new Alternative(new Sort("Expr"), new Char('-'), new RSort("Expr")),
			new Alternative(new Sort("Number"))
		};
	}

	public static void main(String[] args) {
		ParserStructure structure = new FromClassGenerator(Associativity.class).generate();

		char[] input = "1+2-3".toCharArray();
		Parser parser = new Parser(input, structure);

		AbstractNode result = parser.parse("Expr");
		System.out.println(result);
	}
}
```

