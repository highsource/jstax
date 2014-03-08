package org.hisrc.jstax.grammar.graph.impl;

import org.hisrc.jstax.grammar.graph.Vertex;

public abstract class AbstractVertexImpl implements Vertex {

	private final String identifierName;

	public AbstractVertexImpl(String identifierName) {
		this.identifierName = identifierName;
	}

	@Override
	public String getIdentifierName() {
		return this.identifierName;
	}
}
