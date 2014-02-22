package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Delimited;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.Production;

public class DelimitedImpl extends SequenceImpl implements Delimited {

	private final Production element;
	private final Production delimiter;

	// Delimited ::= element (delimiter element)*
	public DelimitedImpl(Production element, Production delimiter) {
		super(element, Grammar.zeroOrMore(Grammar.sequence(delimiter, element)));
		this.element = element;
		this.delimiter = delimiter;
	}

	@Override
	public Production getElement() {
		return element;
	}

	@Override
	public Production getDelimiter() {
		return delimiter;
	}
}
