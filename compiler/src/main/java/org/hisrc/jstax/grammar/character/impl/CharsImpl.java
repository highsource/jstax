package org.hisrc.jstax.grammar.character.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.character.ChVisitor;
import org.hisrc.jstax.grammar.character.Char;
import org.hisrc.jstax.grammar.character.CharRange;
import org.hisrc.jstax.grammar.character.CharRanges;
import org.hisrc.jstax.grammar.character.Chars;
import org.hisrc.jstax.grammar.gamma.Producer;
import org.hisrc.jstax.io.Input;

public class CharsImpl extends AbstractChImpl implements Chars {

	private final int length;
	private final char[] chs;
	private final List<Char> chars;
	private final List<Char> unmodifiableChars;
	private final List<CharRange> unmodifiableCharRanges;

	public CharsImpl(String name, String str) {
		this(Validate.notNull(name), str.toCharArray());
	}

	public CharsImpl(String name, char[] _chars) {
		this(Validate.notNull(name), chars(Validate.notNull(name), _chars));
	}

	public CharsImpl(String name, Char... _chars) {
		super(Validate.notNull(name));
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

	@Override
	public <R> R accept(ChVisitor<R> visitor) {
		Validate.notNull(visitor);
		return visitor.visitCh(this);
	}

	@Override
	public CharRanges minus(String name, Char that) {
		Validate.notNull(name);
		Validate.notNull(that);
		if (!chars.contains(that)) {
			return new CharsImpl(name, chs);
		} else {
			final List<Char> newChars = new ArrayList<Char>(this.chars);
			newChars.remove(that);
			return new CharsImpl(name, newChars.toArray(new Char[newChars
					.size()]));
		}
	}
}
