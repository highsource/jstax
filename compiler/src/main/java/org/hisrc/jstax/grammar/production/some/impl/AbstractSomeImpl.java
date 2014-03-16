package org.hisrc.jstax.grammar.production.some.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.operation.Operation;
import org.hisrc.jstax.grammar.production.Production;
import org.hisrc.jstax.grammar.production.impl.AbstractProduction;
import org.hisrc.jstax.grammar.production.some.Some;

public abstract class AbstractSomeImpl extends AbstractProduction implements
		Some {

	private final Production production;

	public AbstractSomeImpl(String name, Production production) {
		this(None.INSTANCE, name, production);
	}

	public AbstractSomeImpl(Operation operation, String name,
			Production production) {
		super(Validate.notNull(operation), Validate.notNull(name));
		this.production = Validate.notNull(production);
	}

	@Override
	public Production getContent() {
		return production;
	}

}
