package org.hisrc.jstax.grammar.graph;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.impl.DefaultVertexVisitor;
import org.jgrapht.DirectedGraph;

public class EmptyVertexWithOneOutgoingEdgeRemover extends
		DefaultVertexVisitor<Boolean> {

	private final DirectedGraph<Vertex, Edge> graph;

	public EmptyVertexWithOneOutgoingEdgeRemover(
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

		if (outgoingEdgesSet.size() == 1) {
			final Edge outgoingEdge = outgoingEdgesSet.iterator().next();
			final Vertex outgoingVertex = graph.getEdgeTarget(outgoingEdge);
			final Edge[] incomingEdges = incomingEdgesSet
					.toArray(new Edge[incomingEdgesSet.size()]);
			for (Edge incomingEdge : incomingEdges) {
				final Vertex incomingVertex = graph.getEdgeSource(incomingEdge);
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
