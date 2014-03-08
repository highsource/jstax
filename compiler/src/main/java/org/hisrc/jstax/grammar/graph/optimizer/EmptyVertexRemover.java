package org.hisrc.jstax.grammar.graph.optimizer;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.EmptyVertex;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.DefaultVertexVisitor;
import org.jgrapht.DirectedGraph;

public class EmptyVertexRemover extends DefaultVertexVisitor<Boolean> {

	private final DirectedGraph<Vertex, Edge> graph;

	public EmptyVertexRemover(DirectedGraph<Vertex, Edge> graph) {
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

		final Edge[] incomingEdges = incomingEdgesSet
				.toArray(new Edge[incomingEdgesSet.size()]);
		final Edge[] outgoingEdges = outgoingEdgesSet
				.toArray(new Edge[outgoingEdgesSet.size()]);
		for (Edge incomingEdge : incomingEdges) {
			final Vertex incomingVertex = graph.getEdgeSource(incomingEdge);
			for (Edge outgoingEdge : outgoingEdges) {
				final Vertex outgoingVertex = graph.getEdgeTarget(outgoingEdge);
				graph.addEdge(incomingVertex, outgoingVertex);
			}
		}
		graph.removeVertex(vertex);
		return true;
	}

	public DirectedGraph<Vertex, Edge> getGraph() {
		return graph;
	}

}
