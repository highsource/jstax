package org.hisrc.jstax.grammar.gamma.impl;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.EmptyVertex;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.jgrapht.DirectedGraph;

public class EmptyVertexImpl extends AbstractVertexImpl implements EmptyVertex {

	@Override
	public boolean optimize(DirectedGraph<Vertex, Edge> graph) {
		Validate.isTrue(graph.containsVertex(this));
		Set<Edge> incomingEdgesSet = graph.incomingEdgesOf(this);
		final Edge[] incomingEdges = incomingEdgesSet
				.toArray(new Edge[incomingEdgesSet.size()]);
		final Set<Edge> outgoingEdgesSet = graph.outgoingEdgesOf(this);
		final Edge[] outgoingEdges = outgoingEdgesSet
				.toArray(new Edge[outgoingEdgesSet.size()]);

		for (Edge incomingEdge : incomingEdges) {
			final Vertex incomingVertex = graph.getEdgeSource(incomingEdge);
			for (Edge outgoingEdge : outgoingEdges) {
				try {
					final Vertex outgoingVertex = graph
							.getEdgeTarget(outgoingEdge);
					graph.addEdge(incomingVertex, outgoingVertex);
					graph.removeEdge(incomingEdge);
					graph.removeEdge(outgoingEdge);
				} catch (NullPointerException npex) {

					npex.printStackTrace();
				}
			}
		}
		graph.removeVertex(this);
		return true;
	}

	@Override
	public String getName() {
		return "EMPTY";
	}
}
