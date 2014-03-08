package org.hisrc.jstax.grammar.gamma.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.EmptyVertex;
import org.hisrc.jstax.grammar.gamma.VertexVisitor;

public class EmptyVertexImpl extends AbstractVertexImpl implements EmptyVertex {

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
