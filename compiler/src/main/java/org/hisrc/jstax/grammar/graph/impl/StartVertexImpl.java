package org.hisrc.jstax.grammar.graph.impl;

import org.hisrc.jstax.grammar.graph.StartVertex;
import org.hisrc.jstax.grammar.graph.VertexVisitor;

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
