package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Delimited;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.Prefix;

public class DelimitedImpl extends PrefixedSequenceImpl implements Delimited {

	private final Prefix element;
	private final Prefix delimiter;

	// Delimited ::= element (delimiter element)*
	public DelimitedImpl(Prefix element, Prefix delimiter) {
		super(element, Grammar.zeroOrMore(Grammar.sequence(delimiter, element)));
		this.element = element;
		this.delimiter = delimiter;
	}

	@Override
	public Prefix getElement() {
		return element;
	}

	@Override
	public Prefix getDelimiter() {
		return delimiter;
	}
}
