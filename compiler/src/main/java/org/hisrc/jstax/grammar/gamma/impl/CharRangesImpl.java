package org.hisrc.jstax.grammar.gamma.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.ChVisitor;
import org.hisrc.jstax.grammar.gamma.Char;
import org.hisrc.jstax.grammar.gamma.CharRange;
import org.hisrc.jstax.grammar.gamma.CharRanges;
import org.hisrc.jstax.io.Input;

public class CharRangesImpl extends AbstractChImpl implements CharRanges {

	private final int length;
	private final char[][] chs;
	private final List<CharRange> charRanges;
	private final List<CharRange> unmodifiableCharRanges;

	public CharRangesImpl(CharRanges... elements) {
		Validate.noNullElements(elements);
		this.charRanges = new ArrayList<CharRange>(elements.length);
		for (CharRanges charRanges : elements) {
			this.charRanges.addAll(charRanges.getCharRanges());
		}
		this.unmodifiableCharRanges = Collections
				.<CharRange> unmodifiableList(this.charRanges);
		this.length = this.charRanges.size();
		this.chs = new char[this.length][2];
		int index = 0;
		for (CharRange ch : this.charRanges) {
			this.chs[index][0] = ch.getFrom();
			this.chs[index][1] = ch.getTo();
			index++;
		}

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
		for (int index = 0; index < this.length; index++) {
			final char[] range = this.chs[index];
			final char from = range[0];
			final char to = range[1];
			if (ch >= from && ch <= to) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (CharRange ch : getCharRanges()) {
			sb.append(ch.toString());
		}
		return sb.toString();
	}

	@Override
	public <R> R accept(ChVisitor<R> visitor) {
		Validate.notNull(visitor);
		return visitor.visitCh(this);
	}

	@Override
	public CharRanges minus(Char that) {
		if (!this.intersects(that)) {
			return this;
		} else {
			final List<CharRange> newCharRanges = new ArrayList<CharRange>(
					this.charRanges.size() + 1);
			for (final CharRange charRange : this.charRanges) {
				final CharRanges newCharRange = charRange.minus(that);
				if (newCharRanges != null) {
					newCharRanges.addAll(newCharRange.getCharRanges());
				}
			}
			if (newCharRanges.size() == 0) {
				return null;
			} else if (newCharRanges.size() == 1) {
				return newCharRanges.get(0);
			} else {
				return new CharRangesImpl(
						newCharRanges.toArray(new CharRange[newCharRanges
								.size()]));
			}
		}
	}
}
