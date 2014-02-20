package org.hisrc.jstax.grammar.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Char;
import org.hisrc.jstax.grammar.CharRange;
import org.hisrc.jstax.grammar.Chars;
import org.hisrc.jstax.grammar.Grammar;

public class CharsImpl extends AbstractChImpl implements Chars {

	private List<Char> chars;
	private List<Char> unmodifiableChars;
	private List<CharRange> unmodifiableCharRanges;

	public CharsImpl(String str) {
		this(str.toCharArray());
	}

	public CharsImpl(char[] _chars) {
		this(chars(_chars));
	}

	public CharsImpl(Char... _chars) {
		Validate.noNullElements(_chars);
		this.chars = Arrays.asList(_chars);
		this.unmodifiableChars = Collections.unmodifiableList(this.chars);
		final List<CharRange> charRanges = new ArrayList<CharRange>(
				_chars.length);
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
			chars[index] = Grammar._char(_chars[index]);
		}
		return chars;
	}
}
