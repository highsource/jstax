package org.hisrc.jstax.grammar.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.Char;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.Str;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.ParseException;
import org.hisrc.jstax.io.Result;

public class StrImpl extends SequenceImpl implements Str {

	private final Ch first;
	private final String str;
	private final char[] chars;
	private final int length;

	public StrImpl(String str) {
		super(sequence(str));
		// TODO Ugly
		this.first = getElements().get(0);
		this.str = str;
		this.chars = str.toCharArray();
		this.length = this.chars.length;
	}

	@Override
	public Ch getFirst() {
		return this.first;
	}

	public String getStr() {
		return this.str;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Ch> getElements() {
		return (List<Ch>) super.getElements();
	}

	private static final Char[] sequence(String str) {
		Validate.notNull(str);
		final char[] _chars = str.toCharArray();
		final int length = _chars.length;
		final Char[] chars = new Char[length];
		for (int index = 0; index < length; index++) {
			chars[index] = Grammar._char(_chars[index]);
		}
		return chars;
	}

	@Override
	public boolean matches(Input input) {
		for (int index = 0; index < this.length; index++) {
			if (this.chars[index] != input.peekChar(index)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void read(Input input, Result result, ErrorHandler errorHandler) {
		for (int index = 0; index < this.length; index++) {
			final char ch = input.peekChar();
			final char expectedCh = this.chars[index];
			if (expectedCh != ch) {
				errorHandler.error(new ParseException("Expected [" + expectedCh
						+ "], got [" + ch + "].", input.getLocator()));
			} else {
				result.pushChar(input.readChar());
			}
		}
	}

}
