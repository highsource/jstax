package org.hisrc.jstax.grammar.graph.impl;

import org.hisrc.jstax.grammar.graph.ChVertex;
import org.hisrc.jstax.grammar.graph.EmptyVertex;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.VertexVisitor;

public class DefaultVertexVisitor<R> implements VertexVisitor<R> {

	public R visitVertex(Vertex vertex) {
		return null;
	}

	@Override
	public R visitVertex(EmptyVertex vertex) {
		return visitVertex((Vertex) vertex);
	}

	@Override
	public R visitVertex(ChVertex vertex) {
		return visitVertex((Vertex) vertex);
	}

}
