package org.hisrc.jstax.grammar.gamma.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.ChVertex;
import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.EmptyVertex;
import org.hisrc.jstax.grammar.gamma.EndVertex;
import org.hisrc.jstax.grammar.gamma.StartVertex;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.grammar.gamma.VertexVisitor;
import org.jgrapht.DirectedGraph;

public class DefaultVertexVisitor<R> implements VertexVisitor<R> {

	private final DirectedGraph<Vertex, Edge> graph;

	public DefaultVertexVisitor(DirectedGraph<Vertex, Edge> graph) {
		Validate.notNull(graph);
		this.graph = graph;
	}

	public DirectedGraph<Vertex, Edge> getGraph() {
		return graph;
	}

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

	@Override
	public R visitVertex(StartVertex vertex) {
		return visitVertex((Vertex) vertex);
	}

	@Override
	public R visitVertex(EndVertex vertex) {
		return visitVertex((Vertex) vertex);
	}

}
