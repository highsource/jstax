package org.hisrc.jstax.grammar.impl;

import java.util.Collections;
import java.util.List;

import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.Char;
import org.hisrc.jstax.grammar.CharRange;

public class CharImpl extends AbstractChImpl implements Char {

	private final char ch;
	private final List<CharRange> charRanges;
	private final List<CharRange> unmodifiableCharRanges;

	public CharImpl(char ch) {
		this.ch = ch;
		this.charRanges = Collections
				.<CharRange> singletonList(new CharRangeImpl(ch, ch));
		this.unmodifiableCharRanges = Collections
				.<CharRange> unmodifiableList(charRanges);
	}
	
	@Override
	public char getFrom() {
		return ch;
	}

	@Override
	public char getTo() {
		return ch;
	}

	@Override
	public char getChar() {
		return ch;
	}

	@Override
	public List<CharRange> getCharRanges() {
		return unmodifiableCharRanges;
	}

	@Override
	public int compareTo(Char that) {
		if (that == null) {
			return 1;
		}
		return this.getChar() - that.getChar();
	}

}
