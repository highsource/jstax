package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.EndVertex;
import org.hisrc.jstax.grammar.gamma.VertexVisitor;

public class EndVertexImpl extends AbstractVertexImpl implements EndVertex {
	
	@Override
	public String getName() {
		return "END";
	}
	
	@Override
	public <R> R accept(VertexVisitor<R> visitor) {
		return visitor.visitVertex(this);
	}

}
