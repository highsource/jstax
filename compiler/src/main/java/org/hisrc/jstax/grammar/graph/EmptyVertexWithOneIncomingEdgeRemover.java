package org.hisrc.jstax.grammar.graph;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.EmptyVertex;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.grammar.gamma.impl.DefaultVertexVisitor;
import org.jgrapht.DirectedGraph;

public class EmptyVertexWithOneIncomingEdgeRemover extends
		DefaultVertexVisitor<Boolean> {

	private final DirectedGraph<Vertex, Edge> graph;

	public EmptyVertexWithOneIncomingEdgeRemover(
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
		final Set<Edge> incomingEdgesSet = graph.incomingEdgesOf(vertex);
		final Set<Edge> outgoingEdgesSet = graph.outgoingEdgesOf(vertex);

		if (incomingEdgesSet.size() == 1) {
			final Edge incomingEdge = incomingEdgesSet.iterator().next();
			final Vertex incomingVertex = graph.getEdgeSource(incomingEdge);
			final Edge[] outgoingEdges = outgoingEdgesSet
					.toArray(new Edge[outgoingEdgesSet.size()]);
			for (Edge outgoingEdge : outgoingEdges) {
				final Vertex outgoingVertex = graph.getEdgeTarget(outgoingEdge);
				graph.addEdge(incomingVertex, outgoingVertex);
			}
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
