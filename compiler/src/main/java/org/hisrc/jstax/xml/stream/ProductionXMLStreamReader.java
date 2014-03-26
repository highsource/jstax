package org.hisrc.jstax.xml.stream;

import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.StateMachineBuilder;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.ChVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.graph.impl.StartVertexImpl;
import org.hisrc.jstax.grammar.graph.optimizer.CompositeGraphOptimizer;
import org.hisrc.jstax.grammar.operation.Ignore;
import org.hisrc.jstax.grammar.production.Production;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.production.character.impl.CharImpl;
import org.hisrc.jstax.grammar.state.State;
import org.hisrc.jstax.grammar.state.StateMachine;
import org.hisrc.jstax.grammar.state.Transition;
import org.hisrc.jstax.io.CharConstants;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Locator;
import org.hisrc.jstax.io.ParseException;
import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.impl.StringResult;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;

public class ProductionXMLStreamReader extends XMLStreamReaderImpl {

	private final StateMachine stateMachine;
	private State currentState;
	private final State terminalState;
	private final Input input;
	private final Result result;
	private final ErrorHandler errorHandler;
	private final XMLStreamScanner scanner;

	public ProductionXMLStreamReader(final Production production,
			final Input input, final ErrorHandler errorHandler) {
		Validate.notNull(production);
		this.errorHandler = Validate.notNull(errorHandler);
		this.input = Validate.notNull(input);

		final DirectedGraph<Vertex, Edge> graph = new DefaultDirectedGraph<Vertex, Edge>(
				new EdgeFactory<Vertex, Edge>() {
					@Override
					public Edge createEdge(Vertex sourceVertex,
							Vertex targetVertex) {
						return new EdgeImpl();
					}
				});

		final Vertex start = new StartVertexImpl();
		// final Vertex end = new EndVertexImpl();
		final Vertex end = new ChVertexImpl(new CharImpl(Ignore.INSTANCE,
				"END", CharConstants.EOF));
		graph.addVertex(start);
		graph.addVertex(end);
		production.buildGraph(graph, start, end);
		new CompositeGraphOptimizer(graph).optimize();
		this.stateMachine = new StateMachineBuilder().buildStateMachine(graph);
		this.currentState = this.stateMachine.getInitialState();
		this.terminalState = this.stateMachine.getTerminalState();
		this.result = new StringResult();

		this.scanner = new XMLStreamScannerImpl(this);
	}

	public void scan() {
		while (!newEvent && currentState != terminalState) {
			final char _char = input.peekChar();
			final Transition transition = this.stateMachine.getTransition(
					currentState, _char);
			if (transition == null) {
				this.stateMachine.getTransition(currentState, _char);
				// TODO
				final Locator locator = input.getLocator();
				errorHandler
						.error(new ParseException(
								MessageFormat
										.format("Could not detect the next state from the state [{0}] for the character [{1}] at [{2},{3}].",
												currentState, _char,
												locator.getLineNumber(),
												locator.getColumnNumber()),
								locator));
			}
			transition.getOperation().execute(result, scanner);
			final Ch ch = transition.getCh();
			ch.read(input, result, errorHandler);
			currentState = transition.getNextState();
		}
	}
}
