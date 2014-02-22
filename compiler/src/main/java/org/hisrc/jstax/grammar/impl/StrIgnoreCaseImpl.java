package org.hisrc.jstax.grammar.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.Chars;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.Str;

public class StrIgnoreCaseImpl extends SequenceImpl implements Str{

	public StrIgnoreCaseImpl(String str) {
		super(sequence(str));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ch> getElements() {
		return (List<Ch>) super.getElements();
	}
	
	private static final Chars[] sequence(String str) {
		Validate.notNull(str);
		final char[] lowerCaseChars = str.toLowerCase().toCharArray();
		final char[] upperCaseChars = str.toUpperCase().toCharArray();
		final int length = str.length();
		final Chars[] chars = new Chars[length];
		for (int index = 0; index < length; index++) {
			chars[index] = Grammar.chars(lowerCaseChars[index], upperCaseChars[index]);
		}
		return chars;
	}

}
