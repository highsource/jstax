package org.hisrc.jstax.grammar.graph;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.impl.DefaultVertexVisitor;
import org.hisrc.jstax.grammar.production.character.impl.CharImpl;
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
							return stateMachine.createState(vertex
									.getIdentifierName());
						}

						@Override
						public State visitVertex(StartVertex vertex) {
							return stateMachine.getInitialState();
						}

						@Override
						public State visitVertex(EndVertex vertex) {
							return stateMachine.getTerminalState();
						}

						@Override
						public State visitVertex(ErrorVertex vertex) {
							return stateMachine.createState(vertex
									.getIdentifierName());
						}
					}));
		}

		for (Vertex nextVertex : graph.vertexSet()) {
			final State nextState = vertexStateMap.get(nextVertex);
			final Set<Edge> incomingEdgeSet = graph.incomingEdgesOf(nextVertex);
			for (final Edge incomingEdge : incomingEdgeSet) {
				final Vertex previousVertex = graph.getEdgeSource(incomingEdge);
				final State previousState = vertexStateMap.get(previousVertex);
				nextVertex.accept(new DefaultVertexVisitor<Void>() {
					@Override
					public Void visitVertex(ChVertex vertex) {
						stateMachine.createTransition(previousState,
								vertex.getContent(),
								incomingEdge.getOperation(), nextState);
						return null;
					}

					@Override
					public Void visitVertex(EndVertex vertex) {
						stateMachine.createTransition(previousState,
								new CharImpl("EOF",
										org.hisrc.jstax.io.CharConstants.EOF),
								incomingEdge.getOperation(), nextState);
						return null;
					}

				});
			}
		}
		return stateMachine;
	}

}
