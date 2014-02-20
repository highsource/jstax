package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.OneOrMore;
import org.hisrc.jstax.grammar.Production;

// TODO choice
public class OneOrMoreImpl extends AbstractSomeImpl implements OneOrMore {

	public OneOrMoreImpl(Production element) {
		super(element);
	}

	@Override
	public boolean isOptional() {
		return false;
	}

	@Override
	public boolean isRepeatable() {
		return true;
	}

}
