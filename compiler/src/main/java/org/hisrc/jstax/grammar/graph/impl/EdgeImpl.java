package org.hisrc.jstax.grammar.graph.impl;

import java.util.Collection;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.operation.Operation;

public class EdgeImpl implements Edge {

	private final Operation operation;

	public EdgeImpl() {
		this(None.INSTANCE);
	}

	public EdgeImpl(final Operation operation) {
		this.operation = Validate.notNull(operation);
	}

	@Override
	public Operation getOperation() {
		return this.operation;
	}

	@Override
	public Edge sequence(Edge that) {
		return new EdgeImpl(this.getOperation().join(that.getOperation()));
	}

	@Override
	public Edge merge(Collection<Edge> edges) {
		final Operation operation = getOperation();
		for (Edge edge : edges) {
			if (!operation.equals(edge.getOperation())) {
				throw new UnsupportedOperationException(
						"Different operations can not be joined.");
			}
		}
		return new EdgeImpl(operation);
	}

	@Override
	public Edge clone() {
		return new EdgeImpl(getOperation());
	}

}
