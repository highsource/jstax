package org.hisrc.jstax.grammar.gamma.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.ChVisitor;
import org.hisrc.jstax.grammar.gamma.Char;
import org.hisrc.jstax.grammar.gamma.CharConstants;
import org.hisrc.jstax.grammar.gamma.CharRange;
import org.hisrc.jstax.grammar.gamma.CharRanges;
import org.hisrc.jstax.grammar.gamma.Chars;
import org.hisrc.jstax.grammar.gamma.NegativeChars;
import org.hisrc.jstax.grammar.gamma.Producer;
import org.hisrc.jstax.io.Input;

public class NegativeCharsImpl extends AbstractChImpl implements NegativeChars {

	private final int length;
	private final char[] negativeChs;
	private final Chars negativeChars;
	private final List<CharRange> charRanges;
	private final List<CharRange> unmodifiableCharRanges;

	public NegativeCharsImpl(String name, String str) {
		this(Validate.notNull(name), str.toCharArray());
	}

	public NegativeCharsImpl(String name, char[] _chars) {
		this(Validate.notNull(name), chars(Validate.notNull(name), _chars));
	}

	public NegativeCharsImpl(String name, Char... negativeChars) {
		super(Validate.notNull(name));
		Validate.noNullElements(negativeChars);
		this.length = negativeChars.length;
		this.negativeChs = new char[this.length];
		for (int index = 0; index < this.length; index++) {
			this.negativeChs[index] = negativeChars[index].getChar();
		}
		final Char[] sortedNegativeChars = new Char[this.length];
		System.arraycopy(negativeChars, 0, sortedNegativeChars, 0, this.length);
		Arrays.sort(sortedNegativeChars);
		negativeChars = sortedNegativeChars;
		this.negativeChars = Producer.chars(name + "_NEGATIVE_CHARS",
				sortedNegativeChars);

		this.charRanges = new ArrayList<CharRange>(this.length + 1);
		for (int index = 0; index <= this.length; index++) {
			final char from = (index == 0 ? CharConstants.MIN.getChar()
					: (char) (negativeChars[index - 1].getChar() + 1));
			final char to = (index == this.length ? CharConstants.MAX.getChar()
					: (char) (negativeChars[index].getChar() - 1));
			if (from <= to) {
				this.charRanges.add(Producer.charRange(name + "_" + index,
						from, to));
			}
		}
		this.unmodifiableCharRanges = Collections
				.unmodifiableList(this.charRanges);
	}

	@Override
	public List<CharRange> getCharRanges() {
		return this.unmodifiableCharRanges;
	}

	@Override
	public Chars getNegativeChars() {
		return this.negativeChars;
	}

	private static final Char[] chars(String name, char... _chars) {
		Validate.notNull(_chars);
		final Char[] chars = new Char[_chars.length];
		for (int index = 0; index < _chars.length; index++) {
			chars[index] = Producer._char(name + "_" + index, _chars[index]);
		}
		return chars;
	}

	@Override
	public boolean startsInput(Input input) {
		final char ch = input.peekChar();
		return matches(ch);
	}

	@Override
	public boolean matches(final char ch) {
		for (int index = 0; index < this.length; index++) {
			if (ch == this.negativeChs[index]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "^" + String.valueOf(negativeChs);
	}

	@Override
	public <R> R accept(ChVisitor<R> visitor) {
		Validate.notNull(visitor);
		return visitor.visitCh(this);
	}

	@Override
	public CharRanges minus(String name, Char that) {
		Validate.notNull(name);
		Validate.notNull(that);
		if (this.negativeChars.intersects(that)) {
			return this;
		} else {
			final List<Char> newChars = new ArrayList<Char>(
					this.negativeChars.getChars());
			newChars.add(that);
			return new NegativeCharsImpl(name,
					newChars.toArray(new Char[newChars.size()]));
		}
	}
}
