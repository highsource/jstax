package org.hisrc.jstax.grammar.graph.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.EmptyVertex;
import org.hisrc.jstax.grammar.graph.VertexVisitor;

public class EmptyVertexImpl extends AbstractVertexImpl implements EmptyVertex {

	public EmptyVertexImpl() {
		super("EMPTY");
	}

	@Override
	public String getName() {
		return "EMPTY";
	}

	@Override
	public <R> R accept(VertexVisitor<R> visitor) {
		Validate.notNull(visitor);
		return visitor.visitVertex(this);
	}

}
