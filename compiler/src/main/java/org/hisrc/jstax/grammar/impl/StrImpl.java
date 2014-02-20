package org.hisrc.jstax.grammar.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.Char;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.Str;

public class StrImpl extends SequenceImpl implements Str {

	private final String str;

	public StrImpl(String str) {
		super(sequence(str));
		this.str = str;
	}

	public String getStr() {
		return str;
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

}
