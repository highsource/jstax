package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Production;
import org.hisrc.jstax.grammar.ZeroOrOne;

//TODO choice
public class ZeroOrOneImpl extends AbstractSomeImpl implements ZeroOrOne {

	public ZeroOrOneImpl(Production element) {
		super(element);
	}

	@Override
	public boolean isOptional() {
		return true;
	}

	@Override
	public boolean isRepeatable() {
		return false;
	}

}
