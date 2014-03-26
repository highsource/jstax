package org.hisrc.jstax.grammar.graph.optimizer;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.DefaultVertexVisitor;
import org.jgrapht.DirectedGraph;

public class NonStartVertexWithNoIncomingEdgesRemover extends
		DefaultVertexVisitor<Boolean> {

	private final DirectedGraph<Vertex, Edge> graph;
	private final Vertex start;

	public NonStartVertexWithNoIncomingEdgesRemover(
			DirectedGraph<Vertex, Edge> graph, Vertex start) {
		this.graph = Validate.notNull(graph);
		this.start = Validate.notNull(start);
	}

	@Override
	public Boolean visitVertex(Vertex vertex) {
		if (this.start == vertex) {
			return Boolean.FALSE;
		}
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
