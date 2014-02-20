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

public class NegativeCharsImpl extends AbstractChImpl implements NegativeChars {

	private final Chars negativeChars;
	private final List<CharRange> charRanges;
	private final List<CharRange> unmodifiableCharRanges;

	public NegativeCharsImpl(Char... negativeChars) {
		Validate.noNullElements(negativeChars);
		final Char[] sortedNegativeChars = new Char[negativeChars.length];
		System.arraycopy(negativeChars, 0, sortedNegativeChars, 0,
				negativeChars.length);
		Arrays.sort(sortedNegativeChars);
		this.negativeChars = Grammar.chars(sortedNegativeChars);

		final int negativeCharLength = negativeChars.length;
		this.charRanges = new ArrayList<CharRange>(negativeCharLength + 1);
		for (int index = 0; index <= negativeCharLength; index++) {
			char from = (index == 0 ? CharConstants.MIN.getChar()
					: (char) (negativeChars[index - 1].getChar() + 1));
			char to = (index == (negativeCharLength - 1) ? CharConstants.MAX
					.getChar() : (char) (negativeChars[index].getChar() - 1));
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

}
