package org.hisrc.jstax.grammar.production.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.operation.Operation;
import org.hisrc.jstax.grammar.production.Production;

public abstract class AbstractProduction implements Production {

	private final Operation operation;
	private final String name;

	public AbstractProduction(Operation operation, String name) {
		this.operation = Validate.notNull(operation);
		this.name = Validate.notNull(name);
	}

	public AbstractProduction(String name) {
		this(None.INSTANCE, Validate.notNull(name));
	}

	public Operation getOperation() {
		return operation;
	}

	public String getIdentifierName() {
		return name;
	}
}
