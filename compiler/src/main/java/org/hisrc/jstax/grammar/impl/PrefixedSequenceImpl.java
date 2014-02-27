package org.hisrc.jstax.grammar.impl;

import java.util.Collections;
import java.util.List;

import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.Prefix;
import org.hisrc.jstax.grammar.PrefixedSequence;
import org.hisrc.jstax.grammar.Production;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public class PrefixedSequenceImpl extends SequenceImpl implements
		PrefixedSequence {

	private final Ch first;

	private final List<? extends Prefix> elements;

	public PrefixedSequenceImpl(Prefix first, Production... elements) {
		super(sequence(first, elements));
		this.first = first.getFirst();
		this.elements = Collections.singletonList(this);
	}

	private static Production[] sequence(Prefix first, Production... elements) {
		final Production[] sequence = new Production[elements.length + 1];
		sequence[0] = first;
		System.arraycopy(elements, 0, sequence, 1, elements.length);
		return sequence;
	}

	@Override
	public Ch getFirst() {
		return first;
	}

	@Override
	public boolean matches(Input input) {
		return this.first.matches(input);
	}

	@Override
	public List<? extends Prefix> getElements() {
		return this.elements;
	}

	@Override
	public void read(Input input, Result result, ErrorHandler errorHandler) {
		for (Prefix element : getElements()) {
			element.read(input, result, errorHandler);
		}
	}

}
