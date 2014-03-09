package org.hisrc.jstax.grammar.graph.optimizer;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.VertexVisitor;
import org.jgrapht.DirectedGraph;

public class CompositeGraphOptimizer {

	private final DirectedGraph<Vertex, Edge> graph;

	private final List<VertexVisitor<Boolean>> vertexOptimizers;

	@SuppressWarnings("unchecked")
	public CompositeGraphOptimizer(DirectedGraph<Vertex, Edge> graph) {
		Validate.notNull(graph);
		this.graph = graph;
		this.vertexOptimizers = Arrays.<VertexVisitor<Boolean>> asList(
		//
				new EmptyVertexRemover(graph),
				//
				new DoubledChVertexUnifier(graph),
				//
				new OutgoingEdgeUnifier(graph),
				//
				new NonStartVertexWithNoIncomingEdgesRemover(graph),
				//
				new VertexMerger(graph));
	}

	public boolean optimize() {

		boolean optimized = false;

		for (VertexVisitor<Boolean> optimizer : vertexOptimizers) {
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
