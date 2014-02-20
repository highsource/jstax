package org.hisrc.jstax.grammar.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.CharRange;
import org.hisrc.jstax.grammar.CharRanges;

public class CharRangesImpl extends AbstractChImpl implements CharRanges {

	private final List<CharRange> charRanges;
	private final List<CharRange> unmodifiableCharRanges;

	public CharRangesImpl(CharRanges... elements) {
		Validate.noNullElements(elements);
		this.charRanges = new ArrayList<CharRange>(elements.length);
		for (CharRanges charRanges : elements) {
			this.charRanges.addAll(charRanges.getCharRanges());
		}
		this.unmodifiableCharRanges = Collections
				.<CharRange> unmodifiableList(this.charRanges);
	}

	@Override
	public List<CharRange> getCharRanges() {
		return this.unmodifiableCharRanges;
	}

}
