package org.hisrc.jstax.grammar.graph;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.ChVertex;
import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.EmptyVertex;
import org.hisrc.jstax.grammar.gamma.EndVertex;
import org.hisrc.jstax.grammar.gamma.StartVertex;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.grammar.gamma.VertexVisitor;
import org.hisrc.jstax.grammar.gamma.impl.DefaultVertexVisitor;
import org.hisrc.jstax.grammar.state.State;
import org.hisrc.jstax.grammar.state.StateMachine;
import org.jgrapht.DirectedGraph;

public class StateMachineBuilder {

	public StateMachine buildStateMachine(DirectedGraph<Vertex, Edge> graph) {
		Validate.notNull(graph);
		final StateMachine stateMachine = new StateMachine();

		final Map<Vertex, State> vertexStateMap = new IdentityHashMap<Vertex, State>();

		for (Vertex vertex : graph.vertexSet()) {
			vertexStateMap.put(vertex,
					vertex.accept(new VertexVisitor<State>() {

						@Override
						public State visitVertex(EmptyVertex vertex) {
							return stateMachine.createState();
						}

						@Override
						public State visitVertex(ChVertex vertex) {
							return stateMachine.createState();
						}

						@Override
						public State visitVertex(StartVertex vertex) {
							return stateMachine.getInitialState();
						}

						@Override
						public State visitVertex(EndVertex vertex) {
							return stateMachine.getTerminalState();
						}
					}));
		}

		for (Vertex nextVertex : graph.vertexSet()) {
			final State nextState = vertexStateMap.get(nextVertex);
			final Set<Edge> incomingEdgeSet = graph.incomingEdgesOf(nextVertex);
			for (Edge incomingEdge : incomingEdgeSet) {
				final Vertex previousVertex = graph.getEdgeSource(incomingEdge);
				final State previousState = vertexStateMap.get(previousVertex);
				nextVertex.accept(new DefaultVertexVisitor<Void>() {
					@Override
					public Void visitVertex(ChVertex vertex) {
						stateMachine.createTransition(previousState,
								vertex.getContent(), nextState);
						return null;
					}
				});
			}
		}
		return stateMachine;
	}

}