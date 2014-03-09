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
import org.hisrc.jstax.grammar.production.character.Ch;
import org.jgrapht.DirectedGraph;

public class OutgoingEdgeUnifier extends DefaultVertexVisitor<Boolean> {

	private final DirectedGraph<Vertex, Edge> graph;

	public OutgoingEdgeUnifier(DirectedGraph<Vertex, Edge> graph) {
		Validate.notNull(graph);
		this.graph = graph;
	}

	@Override
	public Boolean visitVertex(Vertex vertex) {
		Validate.notNull(vertex);
		Validate.isTrue(graph.containsVertex(vertex));
		final Set<Edge> outgoingEdgesSet = graph.outgoingEdgesOf(vertex);

		final MultiMap<Ch, Edge> outgoingChEdges = new MultiValueArrayMap<Ch, Edge>();

		for (Edge outgoingEdge : outgoingEdgesSet) {
			final Vertex outgoingVertex = graph.getEdgeTarget(outgoingEdge);

			final ChVertex chVertex = outgoingVertex
					.accept(new DefaultVertexVisitor<ChVertex>() {
						@Override
						public ChVertex visitVertex(ChVertex vertex) {
							return vertex;
						}
					});
			if (chVertex != null) {
				outgoingChEdges.put(chVertex.getContent(), outgoingEdge);
			}
		}

		boolean optimized = false;
		for (Ch key : outgoingChEdges.keySet()) {
			@SuppressWarnings("unchecked")
			final Collection<Edge> edgesToUnify = (Collection<Edge>) outgoingChEdges
					.get(key);

			if (edgesToUnify != null && edgesToUnify.size() > 1) {
				System.out.println("Level 0:" + vertex);
				System.out.println("Level 1:" + key);
				// System.out.println("Edges to unify:" + key);
				/*
				 * final ChVertex unifiedChVertex = new ChVertexImpl(key);
				 * graph.addVertex(unifiedChVertex); graph.addEdge(vertex,
				 * unifiedChVertex);
				 */
				for (Edge edgeToUnifyLevel1 : edgesToUnify) {

					// for (ChVertex vertexToUnify : edgesToUnify) {
					Vertex vertexToUnify = graph
							.getEdgeTarget(edgeToUnifyLevel1);

					final Set<Edge> edgesToUnifyLevel2 = graph
							.outgoingEdgesOf(vertexToUnify);
					for (final Edge edgeToUnify : edgesToUnifyLevel2) {
						final Vertex targetVertex = graph
								.getEdgeTarget(edgeToUnify);
						System.out.println("Level 2:" + targetVertex);
						// if (targetVertex == vertexToUnify) {
						// graph.addEdge(unifiedChVertex, unifiedChVertex);
						// } else {
						// graph.addEdge(unifiedChVertex, targetVertex);
						// }
					}
				}
				// graph.removeAllVertices(verticesToUnify);
				// graph.removeAllEdges(edgesToUnify);
				// optimized = true;*/

			}
		}

		return optimized;
	}

}
