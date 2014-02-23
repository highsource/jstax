package org.hisrc.jstax.grammar.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.CharRange;
import org.hisrc.jstax.io.Input;

public class CharRangeImpl extends AbstractChImpl implements CharRange {

	private final char from;

	private final char to;

	private final List<CharRange> charRanges;

	private final List<CharRange> unmodifiableCharRanges;

	public CharRangeImpl(char from, char to) {
		Validate.isTrue(from <= to,
				"Character [from] must not be greater that [to].");
		this.from = from;
		this.to = to;
		this.charRanges = Collections.<CharRange> singletonList(this);
		this.unmodifiableCharRanges = Collections
				.unmodifiableList(this.charRanges);
	}

	@Override
	public char getFrom() {
		return from;
	}

	@Override
	public char getTo() {
		return to;
	}

	@Override
	public List<CharRange> getCharRanges() {
		return this.unmodifiableCharRanges;
	}

	@Override
	public boolean check(Input input) {
		final char ch = input.peekChar();
		return ch >= this.from && ch <= this.to;
	}
}
