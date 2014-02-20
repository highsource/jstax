package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.OneOrMoreCh;

public class OneOrMoreChImpl extends OneOrMoreImpl implements OneOrMoreCh {

	private final Ch element;

	public OneOrMoreChImpl(Ch element) {
		super(element);
		this.element = element;
	}

	@Override
	public Ch getElement() {
		return this.element;
	}

}
