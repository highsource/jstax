package org.hisrc.jstax.grammar.gamma.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.ChVisitor;
import org.hisrc.jstax.grammar.gamma.Char;
import org.hisrc.jstax.grammar.gamma.CharRange;
import org.hisrc.jstax.grammar.gamma.CharRanges;
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
	public boolean startsInput(Input input) {
		final char ch = input.peekChar();
		return matches(ch);
	}

	@Override
	public boolean matches(final char ch) {
		return ch >= this.from && ch <= this.to;
	}

	@Override
	public String toString() {
		return String.valueOf(getFrom()) + "-" + String.valueOf(getTo());
	}

	@Override
	public <R> R accept(ChVisitor<R> visitor) {
		Validate.notNull(visitor);
		return visitor.visitCh(this);
	}

	@Override
	public CharRanges minus(Char that) {
		Validate.notNull(that);
		final char thatChar = that.getChar();
		final char thisFrom = this.getFrom();
		final char thisTo = this.getTo();
		if (thisFrom > thatChar || this.getTo() < thatChar) {
			return this;
		} else if (thisFrom == thatChar) {
			final char newFrom = (char) (thisFrom + 1);
			if (newFrom == thisTo) {
				return new CharImpl(newFrom);
			} else {
				return new CharRangeImpl(newFrom, thisTo);
			}
		} else if (thisTo == thatChar) {
			final char newTo = (char) (thisTo - 1);
			if (newTo == thisFrom) {
				return new CharImpl(newTo);
			} else {
				return new CharRangeImpl(thisFrom, newTo);
			}
		} else {
			return new CharRangesImpl(new CharRangeImpl(thisFrom,
					(char) (thatChar - 1)), new CharRangeImpl(
					(char) (thatChar + 1), thisTo));
		}
	}
}
