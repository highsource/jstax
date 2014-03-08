package org.hisrc.jstax.grammar.gamma.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Production;

public abstract class AbstractProduction implements Production {

	private final String name;

	public AbstractProduction(String name) {
		Validate.notNull(name);
		this.name = name;
	}

	public String getIdentifierName() {
		return name;
	}
}
