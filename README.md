SGTDBF
======

This is the prototype implementation of the Scannerless General Top Down Binary Forest parser.

It's still a work in progress (and the code looks horrible), so beware.

Scaling:
O(N) for LL and LR grammers.
O(N^3) worst case.
Usually near O(N) on non-ambiguous or locally ambiguous grammars.

Performance:
~30-40% slower compared to commonly used LL and (LA)LR parsers that are written in Java for non-ambiguous grammars.
Somewhere between a lot and multiple orders of magnitude faster than most other general(ized) parsers, depending on the grammar. E.g. 16ms parse time for the 'S ::= SSS | SS | 'a' | epsilon' grammer @ 100 input chars; 396ms @ 300 input chars.

Usage:
For now look at the 'tests' for examples.

Supported 'fancy' features:
-Native support for:
  -Star- and plus-lists.
  -Optionals.
  -Sequences.
  -Choices.
-Production prefix sharing
  -Overlapping productions are automatically merged for improved performance and scalability without affecting the produced AST.
-Build-in filtering support.
  -Scoping (e.g. for encoding priorities).
  -Restrictions (e.g. for encoding associativity).
  -Input based filtering (i.e. restrictions on preceding, following or matched characters), which can easily be extended by the user.

Work in progress:
  -A tree you can work with (right now it's binarized).
  -Semantic actions to enable more advanced filtering.

