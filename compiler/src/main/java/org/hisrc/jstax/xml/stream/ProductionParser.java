package org.hisrc.jstax.xml.stream;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.StateMachineBuilder;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.graph.impl.EndVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.StartVertexImpl;
import org.hisrc.jstax.grammar.graph.optimizer.CompositeGraphOptimizer;
import org.hisrc.jstax.grammar.parser.StateMachineParser;
import org.hisrc.jstax.grammar.production.Production;
import org.hisrc.jstax.grammar.state.StateMachine;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.impl.StringResult;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;

public class ProductionParser {

	private StateMachineParser stateMachineParser;
	private Input input;
	private ErrorHandler errorHandler;

	public ProductionParser(final Production production, final Input input,
			final ErrorHandler errorHandler) {
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
		final Vertex end = new EndVertexImpl();
		graph.addVertex(start);
		graph.addVertex(end);
		production.buildGraph(graph, start, end);
		new CompositeGraphOptimizer(graph).optimize();
		final StateMachine stateMachine = new StateMachineBuilder()
				.buildStateMachine(graph);
		stateMachineParser = new StateMachineParser(stateMachine);
	}

	public void parse() {
		stateMachineParser.parse(input, new StringResult(), writer,
				errorHandler);
	}

	private final XMLStreamWriter writer = new XMLStreamWriter() {

		@Override
		public void writeComment(String data) {
			System.out.println("COMMENT [" + data + "]");
		}

		@Override
		public void writeProcessingInstruction(String target) {
			System.out.println("PI [" + target + "]");

		}

		@Override
		public void writeProcessingInstruction(String target, String data) {
			System.out.println("PI [" + target + "] DATA [" + data + "]");

		}

		private String processingInstructionTarget;

		@Override
		public void writeProcessingInstructionTarget(String target) {
			this.processingInstructionTarget = target;
		}

		@Override
		public void writeProcessingInstructionData(String data) {
			if (this.processingInstructionTarget == null) {
				throw new IllegalStateException(
						"Processing instruction target must have been set earlier, but it is null.");
			}
			final String target = this.processingInstructionTarget;
			this.processingInstructionTarget = null;
			if (data != null) {
				this.writeProcessingInstruction(target, data);
			} else {
				this.writeProcessingInstruction(target);
			}
		}

		@Override
		public void writeCData(String data) {
			if (data != null) {
				System.out.println("CDATA [" + data + "]");
			}
		}
	};

}
