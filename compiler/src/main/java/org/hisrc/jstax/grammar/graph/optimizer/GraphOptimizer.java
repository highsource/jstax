package org.hisrc.jstax.grammar.graph.optimizer;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.VertexVisitor;
import org.jgrapht.DirectedGraph;

public class GraphOptimizer {

	private final DirectedGraph<Vertex, Edge> graph;

	private final List<VertexVisitor<Boolean>> optimizers;

	@SuppressWarnings("unchecked")
	public GraphOptimizer(DirectedGraph<Vertex, Edge> graph) {
		Validate.notNull(graph);
		this.graph = graph;
		this.optimizers = Arrays.<VertexVisitor<Boolean>> asList(
				new EmptyVertexWithNoIncomingEdgesRemover(graph),
				new EmptyVertexWithOneIncomingEdgeRemover(graph),
				new EmptyVertexWithOneOutgoingEdgeRemover(graph),
				new EmptyVertexRemover(graph)
//				,				new OutgoingEdgeUnifier(graph)
				, new DoubledChVertexUnifier(graph)
				);
	}

	public boolean optimize() {

		boolean optimized = false;

		for (VertexVisitor<Boolean> optimizer : optimizers) {
			optimized = optimize(optimizer);
		}
		// If optimized, do it again
		if (optimized) {
			optimize();
		}
		return optimized;
	}

	private boolean optimize(VertexVisitor<Boolean> optimizer) {

		final Set<Vertex> vertexSet = graph.vertexSet();
		final Vertex[] vertices = vertexSet
				.toArray(new Vertex[vertexSet.size()]);

		boolean optimized = false;
		for (Vertex vertex : vertices) {
			if (graph.containsVertex(vertex)) {
				optimized = optimized | vertex.accept(optimizer);
			}
		}
		// If optimized, do it again
		if (optimized) {
			optimize(optimizer);
		}
		return optimized;

	}

}
