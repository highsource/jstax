package org.hisrc.jstax.grammar.production.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.operation.Operation;
import org.hisrc.jstax.grammar.production.Production;

public abstract class AbstractProduction implements Production {

	private final Operation operation;

	public AbstractProduction(Operation operation) {
		this.operation = Validate.notNull(operation);
	}

	public AbstractProduction() {
		this(None.INSTANCE);
	}

	public Operation getOperation() {
		return operation;
	}
}
