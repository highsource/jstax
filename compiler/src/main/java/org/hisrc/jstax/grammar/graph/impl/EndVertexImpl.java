package org.hisrc.jstax.grammar.graph.impl;

import org.hisrc.jstax.grammar.graph.EndVertex;
import org.hisrc.jstax.grammar.graph.VertexVisitor;

public class EndVertexImpl extends AbstractVertexImpl implements EndVertex {

	public EndVertexImpl() {
		super("END");
	}

	@Override
	public String getName() {
		return "END";
	}

	@Override
	public <R> R accept(VertexVisitor<R> visitor) {
		return visitor.visitVertex(this);
	}

}
