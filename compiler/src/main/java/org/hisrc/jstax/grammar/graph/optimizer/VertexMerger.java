package org.hisrc.jstax.grammar.graph.optimizer;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.ChVertex;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.DefaultVertexVisitor;
import org.jgrapht.DirectedGraph;

public class VertexMerger extends DefaultVertexVisitor<Boolean> {
	private final DirectedGraph<Vertex, Edge> graph;

	public VertexMerger(DirectedGraph<Vertex, Edge> graph) {
		this.graph = Validate.notNull(graph);
	}
	
	@Override
	public Boolean visitVertex(Vertex first) {

		final Set<Vertex> vertexSet = graph.vertexSet();
		final Vertex[] vertices = vertexSet
				.toArray(new Vertex[vertexSet.size()]);

		final Set<Vertex> firstOutgoingVertices = outgoingVerticesOf(first);
		for (Vertex second : vertices) {
			if (first != second && first.equals(second)) {
				final Set<Vertex> secondOutgoingVertices = outgoingVerticesOf(second);
				if (firstOutgoingVertices.equals(secondOutgoingVertices)) {
					final Set<Edge> secondIncomingEdgesSet = graph
							.incomingEdgesOf(second);
					final Edge[] secondIncomingEdges = secondIncomingEdgesSet
							.toArray(new Edge[secondIncomingEdgesSet.size()]);
					for (Edge secondIncomingEdge : secondIncomingEdges) {
						Vertex secondIncomingVertex = graph
								.getEdgeSource(secondIncomingEdge);
						graph.addEdge(secondIncomingVertex, first);
					}
					graph.removeVertex(second);
					return true;
				}
			}
		}
		return false;
	}

	private Set<Vertex> outgoingVerticesOf(Vertex vertex) {
		final Set<Vertex> outgoingVertices = new HashSet<Vertex>();
		for (final Edge outgoingEdge : graph.outgoingEdgesOf(vertex)) {
			final Vertex outgoingVertex = graph.getEdgeTarget(outgoingEdge);
			outgoingVertices.add(outgoingVertex);
		}
		return outgoingVertices;
	}
}
