package org.hisrc.jstax.grammar.character.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.character.ChVisitor;
import org.hisrc.jstax.grammar.character.Char;
import org.hisrc.jstax.grammar.character.CharRange;
import org.hisrc.jstax.grammar.character.CharRanges;
import org.hisrc.jstax.io.Input;

public class CharImpl extends AbstractChImpl implements Char {

	private final char ch;
	private final List<CharRange> charRanges;
	private final List<CharRange> unmodifiableCharRanges;

	public CharImpl(String name, char ch) {
		super(name);
		this.ch = ch;
		this.charRanges = Collections
				.<CharRange> singletonList(new CharRangeImpl(name, ch, ch));
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ch;
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
		CharImpl other = (CharImpl) obj;
		if (ch != other.ch)
			return false;
		return true;
	}

	@Override
	public boolean startsInput(Input input) {
		final char ch = input.peekChar();
		return matches(ch);
	}

	@Override
	public boolean matches(final char ch) {
		return this.ch == ch;
	}

	@Override
	public String toString() {
		return String.valueOf(getChar());
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
		if (this.getChar() == that.getChar()) {
			return null;
		} else {
			return new CharImpl(name, this.getChar());
		}
	}
}
