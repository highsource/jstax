package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.NotIncluding;
import org.hisrc.jstax.grammar.Str;

public class NotIncludingImpl extends ExcludingImpl implements NotIncluding {

	private final Ch element;
	private final Str exclusion;

	public NotIncludingImpl(Ch element, Str exclusion) {
		super(Grammar.zeroOrMore(element), exclusion);
		this.element = element;
		this.exclusion = exclusion;
	}

	public Ch getElement() {
		return this.element;
	}

	@Override
	public Str getExclusion() {
		return exclusion;
	}
}
