package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.CharRanges;
import org.hisrc.jstax.grammar.ExcludingCharRanges;
import org.hisrc.jstax.grammar.PrefixedChoice;

public class ExcludingCharRangesImpl extends ExcludingImpl implements
		ExcludingCharRanges {

	private final CharRanges excluding;

	public ExcludingCharRangesImpl(PrefixedChoice content, CharRanges excluding) {
		super(content, excluding);
		this.excluding = excluding;
	}

	@Override
	public CharRanges getExclusion() {
		return excluding;
	}

}
