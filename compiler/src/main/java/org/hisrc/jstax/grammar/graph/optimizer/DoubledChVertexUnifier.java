package org.hisrc.jstax.grammar.graph.optimizer;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.ChVertex;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.DefaultVertexVisitor;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.jgrapht.DirectedGraph;

public class DoubledChVertexUnifier extends DefaultVertexVisitor<Boolean> {

	private final DirectedGraph<Vertex, Edge> graph;

	public DoubledChVertexUnifier(DirectedGraph<Vertex, Edge> graph) {
		Validate.notNull(graph);
		this.graph = graph;
	}

	@Override
	public Boolean visitVertex(Vertex vertex) {
		return false;
	}

	@Override
	public Boolean visitVertex(final ChVertex level0Vertex) {
		Validate.notNull(level0Vertex);
		Validate.isTrue(graph.containsVertex(level0Vertex));
		boolean optimized = false;

		if (graph.containsEdge(level0Vertex, level0Vertex)) {

			final Set<Edge> level0EdgesSet = graph
					.outgoingEdgesOf(level0Vertex);
			final Edge[] level0Edges = level0EdgesSet
					.toArray(new Edge[level0EdgesSet.size()]);

			for (final Edge level0Edge : level0Edges) {
				final Vertex outgoingVertex = graph.getEdgeTarget(level0Edge);

				optimized = optimized
						| outgoingVertex
								.accept(new DefaultVertexVisitor<Boolean>() {
									@Override
									public Boolean visitVertex(Vertex vertex) {
										return false;
									}
									public Boolean visitVertex(
											ChVertex level1Vertex) {

										if (
												level0Vertex != level1Vertex && 
												level0Vertex.getContent().equals(
												level1Vertex.getContent())) {

											final Set<Edge> level1EdgesSet = graph
													.outgoingEdgesOf(level1Vertex);
											final Edge[] level1Edges = level0EdgesSet
													.toArray(new Edge[level1EdgesSet
															.size()]);

											for (final Edge level1Edge : level1Edges) {
												final Vertex level2Vertex = graph
														.getEdgeTarget(level1Edge);

												if (level2Vertex != level1Vertex) {
													graph.addEdge(level0Vertex,
															level2Vertex, level0Edge.sequence(level1Edge));
												}
											}
											graph.removeEdge(level0Edge);
											return true;
										} else {
											return false;
										}
									}
								});
			}
		}
		return optimized;
	}

}
