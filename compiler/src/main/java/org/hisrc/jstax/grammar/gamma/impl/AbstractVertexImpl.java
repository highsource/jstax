package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.Vertex;

public abstract class AbstractVertexImpl implements Vertex {

	private final String identifierName;

	public AbstractVertexImpl() {
		this(null);
	}

	public AbstractVertexImpl(String identifierName) {
		this.identifierName = identifierName;
	}

	@Override
	public String getIdentifierName() {
		return this.identifierName;
	}
}
