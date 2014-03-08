package org.hisrc.jstax.grammar.production.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.production.Production;

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
