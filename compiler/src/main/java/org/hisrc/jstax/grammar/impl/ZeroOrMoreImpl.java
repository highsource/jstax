package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Production;
import org.hisrc.jstax.grammar.ZeroOrMore;

//TODO choice
public class ZeroOrMoreImpl extends AbstractSomeImpl implements ZeroOrMore {

	public ZeroOrMoreImpl(Production element) {
		super(element);
	}

	@Override
	public boolean isOptional() {
		return true;
	}

	@Override
	public boolean isRepeatable() {
		return true;
	}

}
