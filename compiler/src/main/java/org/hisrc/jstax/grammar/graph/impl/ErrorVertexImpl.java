package org.hisrc.jstax.grammar.graph.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.ErrorVertex;
import org.hisrc.jstax.grammar.graph.VertexVisitor;

public class ErrorVertexImpl extends AbstractVertexImpl implements ErrorVertex {

	private final String message;

	public ErrorVertexImpl(String message) {
		super("ERROR");
		Validate.notNull(message);
		this.message = message;
	}

	@Override
	public String getName() {
		return "ERROR";
	}

	public String getMessage() {
		return message;
	}

	@Override
	public <R> R accept(VertexVisitor<R> visitor) {
		return visitor.visitVertex(this);
	}
}
