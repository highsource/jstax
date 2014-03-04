package org.hisrc.jstax.grammar.graph;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.EmptyVertex;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.grammar.gamma.impl.DefaultVertexVisitor;
import org.jgrapht.DirectedGraph;

public class EmptyVertexWithNoIncomingEdgesRemover extends
		DefaultVertexVisitor<Boolean> {

	private final DirectedGraph<Vertex, Edge> graph;

	public EmptyVertexWithNoIncomingEdgesRemover(
			DirectedGraph<Vertex, Edge> graph) {
		Validate.notNull(graph);
		this.graph = graph;
	}

	@Override
	public Boolean visitVertex(Vertex vertex) {
		return Boolean.FALSE;
	}

	@Override
	public Boolean visitVertex(EmptyVertex vertex) {
		Validate.notNull(vertex);
		final DirectedGraph<Vertex, Edge> graph = getGraph();
		Validate.isTrue(graph.containsVertex(vertex));
		if (graph.inDegreeOf(vertex) == 0) {
			graph.removeVertex(vertex);
			return true;
		} else {
			return false;
		}
	}

	public DirectedGraph<Vertex, Edge> getGraph() {
		return graph;
	}

}
