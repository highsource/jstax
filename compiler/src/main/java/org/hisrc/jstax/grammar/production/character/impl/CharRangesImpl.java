package org.hisrc.jstax.grammar.production.character.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.production.character.ChVisitor;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.character.CharRange;
import org.hisrc.jstax.grammar.production.character.CharRanges;
import org.hisrc.jstax.io.Input;

public class CharRangesImpl extends AbstractChImpl implements CharRanges {

	private final int length;
	private final char[][] chs;
	private final List<CharRange> charRanges;
	private final List<CharRange> unmodifiableCharRanges;

	public CharRangesImpl(String name, CharRanges... elements) {
		super(Validate.notNull(name));
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
	public CharRanges minus(String name, Char that) {
		Validate.notNull(name);
		final List<CharRange> charRanges = getCharRanges();
		if (!this.intersects(that)) {
			return new CharRangesImpl(name,
					charRanges.toArray(new CharRanges[charRanges.size()]));
		} else {
			final List<CharRange> newCharRanges = new ArrayList<CharRange>(
					charRanges.size() + 1);
			int index = 0;
			for (final CharRange charRange : charRanges) {
				final CharRanges newCharRange = charRange.minus(
						(name + "_" + index++), that);
				if (newCharRange != null) {
					newCharRanges.addAll(newCharRange.getCharRanges());
				}
			}
			if (newCharRanges.size() == 0) {
				return null;
			} else if (newCharRanges.size() == 1) {
				final CharRange newCharRange = newCharRanges.get(0);
				return new CharRangeImpl(name, newCharRange.getFrom(),
						newCharRange.getTo());
			} else {
				return new CharRangesImpl(name,
						newCharRanges.toArray(new CharRange[newCharRanges
								.size()]));
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(chs);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharRangesImpl other = (CharRangesImpl) obj;
		if (!Arrays.deepEquals(chs, other.chs))
			return false;
		return true;
	}
	
	
	
}
