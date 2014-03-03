package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.StartVertex;
import org.hisrc.jstax.grammar.gamma.VertexVisitor;

public class StartVertexImpl extends AbstractVertexImpl implements StartVertex {

	@Override
	public String getName() {
		return "START";
	}

	@Override
	public <R> R accept(VertexVisitor<R> visitor) {
		return visitor.visitVertex(this);
	}

}
