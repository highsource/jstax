package org.hisrc.jstax.grammar.graph.optimizer;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.EdgeToVertex;
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

		final Set<EdgeToVertex> firstOugoingTransitions = outgoingTransitionsOf(first);
		for (Vertex second : vertices) {
			if (first != second && first.equals(second)) {
				final Set<EdgeToVertex> secondOugoingTransitions = outgoingTransitionsOf(second);
				if (firstOugoingTransitions.equals(secondOugoingTransitions)) {
					final Set<Edge> secondIncomingEdgesSet = graph
							.incomingEdgesOf(second);
					final Edge[] secondIncomingEdges = secondIncomingEdgesSet
							.toArray(new Edge[secondIncomingEdgesSet.size()]);
					for (Edge secondIncomingEdge : secondIncomingEdges) {
						Vertex secondIncomingVertex = graph
								.getEdgeSource(secondIncomingEdge);
						graph.addEdge(secondIncomingVertex, first,
								secondIncomingEdge.clone());
					}
					graph.removeVertex(second);
					return true;
				}
			}
		}
		return false;
	}

	private Set<EdgeToVertex> outgoingTransitionsOf(Vertex vertex) {
		final Set<EdgeToVertex> outgoingTransitionsSet = new HashSet<EdgeToVertex>();
		for (final Edge outgoingEdge : graph.outgoingEdgesOf(vertex)) {
			final Vertex outgoingVertex = graph.getEdgeTarget(outgoingEdge);
			outgoingTransitionsSet.add(new EdgeToVertex(outgoingEdge,
					outgoingVertex));
		}
		return outgoingTransitionsSet;
	}
}
