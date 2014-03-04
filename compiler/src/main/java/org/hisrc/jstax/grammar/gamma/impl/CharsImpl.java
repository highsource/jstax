package org.hisrc.jstax.grammar.gamma.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Char;
import org.hisrc.jstax.grammar.gamma.CharRange;
import org.hisrc.jstax.grammar.gamma.Chars;
import org.hisrc.jstax.grammar.gamma.Producer;
import org.hisrc.jstax.io.Input;

public class CharsImpl extends AbstractChImpl implements Chars {

	private final int length;
	private final char[] chs;
	private final List<Char> chars;
	private final List<Char> unmodifiableChars;
	private final List<CharRange> unmodifiableCharRanges;

	public CharsImpl(String str) {
		this(str.toCharArray());
	}

	public CharsImpl(char[] _chars) {
		this(chars(_chars));
	}

	public CharsImpl(Char... _chars) {
		Validate.noNullElements(_chars);
		this.length = _chars.length;
		this.chs = new char[this.length];
		for (int index = 0; index < this.length; index++) {
			this.chs[index] = _chars[index].getChar();
		}
		this.chars = Arrays.asList(_chars);
		this.unmodifiableChars = Collections.unmodifiableList(this.chars);
		final List<CharRange> charRanges = new ArrayList<CharRange>(this.length);
		charRanges.addAll(this.chars);
		this.unmodifiableCharRanges = Collections
				.<CharRange> unmodifiableList(charRanges);
	}

	@Override
	public List<Char> getChars() {
		return this.unmodifiableChars;
	}

	@Override
	public List<CharRange> getCharRanges() {
		return this.unmodifiableCharRanges;
	}

	private static final Char[] chars(char... _chars) {
		Validate.notNull(_chars);
		final Char[] chars = new Char[_chars.length];
		for (int index = 0; index < _chars.length; index++) {
			chars[index] = Producer._char(_chars[index]);
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
			if (ch == this.chs[index]) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return String.valueOf(chs);
	}
}
