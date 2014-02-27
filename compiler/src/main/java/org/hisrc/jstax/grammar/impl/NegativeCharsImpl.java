package org.hisrc.jstax.grammar.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Char;
import org.hisrc.jstax.grammar.CharConstants;
import org.hisrc.jstax.grammar.CharRange;
import org.hisrc.jstax.grammar.Chars;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.NegativeChars;
import org.hisrc.jstax.io.Input;

public class NegativeCharsImpl extends AbstractChImpl implements NegativeChars {

	private final int length;
	private final char[] negativeChs;
	private final Chars negativeChars;
	private final List<CharRange> charRanges;
	private final List<CharRange> unmodifiableCharRanges;

	public NegativeCharsImpl(String str) {
		this(str.toCharArray());
	}

	public NegativeCharsImpl(char[] _chars) {
		this(chars(_chars));
	}

	public NegativeCharsImpl(Char... negativeChars) {
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
		this.negativeChars = Grammar.chars(sortedNegativeChars);

		this.charRanges = new ArrayList<CharRange>(this.length + 1);
		for (int index = 0; index <= this.length; index++) {
			final char from = (index == 0 ? CharConstants.MIN.getChar()
					: (char) (negativeChars[index - 1].getChar() + 1));
			final char to = (index == this.length ? CharConstants.MAX.getChar()
					: (char) (negativeChars[index].getChar() - 1));
			this.charRanges.add(Grammar.charRange(from, to));
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

	private static final Char[] chars(char... _chars) {
		Validate.notNull(_chars);
		final Char[] chars = new Char[_chars.length];
		for (int index = 0; index < _chars.length; index++) {
			chars[index] = Grammar._char(_chars[index]);
		}
		return chars;
	}

	@Override
	public boolean matches(Input input) {
		final char ch = input.peekChar();
		for (int index = 0; index < this.length; index++) {
			if (ch == this.negativeChs[index]) {
				return false;
			}
		}
		return true;
	}
}
