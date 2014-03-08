package org.hisrc.jstax.grammar.gamma.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Some;

public abstract class AbstractSomeImpl extends AbstractProduction implements
		Some {

	private final Production production;

	public AbstractSomeImpl(String name, Production production) {
		super(Validate.notNull(name));
		this.production = Validate.notNull(production);
	}

	@Override
	public Production getContent() {
		return production;
	}

}
