package org.hisrc.jstax.grammar.graph.optimizer;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.collections4.map.MultiValueArrayMap;
import org.hisrc.jstax.grammar.graph.ChVertex;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.ChVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.DefaultVertexVisitor;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.jgrapht.DirectedGraph;

public class OutgoingEdgeUnifier extends DefaultVertexVisitor<Boolean> {

	private final DirectedGraph<Vertex, Edge> graph;

	public OutgoingEdgeUnifier(DirectedGraph<Vertex, Edge> graph) {
		Validate.notNull(graph);
		this.graph = graph;
	}

	@Override
	public Boolean visitVertex(Vertex level0Vertex) {
		Validate.notNull(level0Vertex);
		Validate.isTrue(graph.containsVertex(level0Vertex));
		final Set<Edge> level0EdgesSet = graph.outgoingEdgesOf(level0Vertex);

		final MultiMap<Ch, Edge> level0ChEdgesMap = new MultiValueArrayMap<Ch, Edge>();

		for (final Edge level0Edge : level0EdgesSet) {
			final Vertex level1Vertex = graph.getEdgeTarget(level0Edge);

			final ChVertex level1ChVertex = level1Vertex
					.accept(new DefaultVertexVisitor<ChVertex>() {
						@Override
						public ChVertex visitVertex(ChVertex vertex) {
							return vertex;
						}
					});
			if (level1ChVertex != null) {
				level0ChEdgesMap.put(level1ChVertex.getContent(), level0Edge);
			}
		}

		boolean optimized = false;
		for (final Ch level1Ch : level0ChEdgesMap.keySet()) {
			@SuppressWarnings("unchecked")
			final Collection<Edge> level0EdgesForCh = (Collection<Edge>) level0ChEdgesMap
					.get(level1Ch);
			if (level0EdgesForCh != null && level0EdgesForCh.size() > 1) {

				final Edge someLevel0EdgeForCh = level0EdgesForCh.iterator()
						.next();
				final Edge mergedLevel0EdgeForCh = someLevel0EdgeForCh
						.merge(level0EdgesForCh);
				final ChVertex newLevel1VertexForCh = new ChVertexImpl(level1Ch);
				graph.addVertex(newLevel1VertexForCh);
				graph.addEdge(level0Vertex, newLevel1VertexForCh,
						mergedLevel0EdgeForCh);

				for (final Edge level0EdgeForCh : level0EdgesForCh) {
					final Vertex level1VertexForCh = graph
							.getEdgeTarget(level0EdgeForCh);
					final Set<Edge> level1EdgesSetForCh = graph
							.outgoingEdgesOf(level1VertexForCh);
					for (final Edge level1EdgeForCh : level1EdgesSetForCh) {
						final Vertex level2VertexForCh = graph
								.getEdgeTarget(level1EdgeForCh);
						graph.addEdge(newLevel1VertexForCh, level2VertexForCh,
								level1EdgeForCh.clone());
					}
					graph.removeEdge(level0EdgeForCh);
				}
			}
		}

		return optimized;
	}

}
