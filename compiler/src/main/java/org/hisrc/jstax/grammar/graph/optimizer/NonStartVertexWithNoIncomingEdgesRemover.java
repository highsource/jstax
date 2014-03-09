package org.hisrc.jstax.grammar.graph.optimizer;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.EmptyVertex;
import org.hisrc.jstax.grammar.graph.StartVertex;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.DefaultVertexVisitor;
import org.jgrapht.DirectedGraph;

public class NonStartVertexWithNoIncomingEdgesRemover extends
		DefaultVertexVisitor<Boolean> {

	private final DirectedGraph<Vertex, Edge> graph;

	public NonStartVertexWithNoIncomingEdgesRemover(
			DirectedGraph<Vertex, Edge> graph) {
		Validate.notNull(graph);
		this.graph = graph;
	}

	@Override
	public Boolean visitVertex(StartVertex vertex) {
		return Boolean.FALSE;
	}

	@Override
	public Boolean visitVertex(Vertex vertex) {
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
