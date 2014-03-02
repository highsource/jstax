package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.Delimited;
import org.hisrc.jstax.grammar.gamma.Production;

public class DelimitedImpl extends SequenceImpl implements Delimited {

	private final Production element;
	private final Production delimiter;

	public DelimitedImpl(Production element, Production delimiter) {
		super(element, delimiter.followedBy(element).zeroOrMore());
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
