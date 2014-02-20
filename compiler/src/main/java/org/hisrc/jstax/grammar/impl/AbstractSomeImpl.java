package org.hisrc.jstax.grammar.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Production;
import org.hisrc.jstax.grammar.Some;

public abstract class AbstractSomeImpl implements Some {

	private final Production element;

	public AbstractSomeImpl(Production element) {
		Validate.notNull(element);
		this.element = element;
	}

	@Override
	public Production getElement() {
		return element;
	}

}
