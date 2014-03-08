package org.hisrc.jstax.xml.tests;

import java.io.File;
import java.io.IOException;

import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.grammar.gamma.impl.EdgeImpl;
import org.hisrc.jstax.grammar.gamma.impl.EndVertexImpl;
import org.hisrc.jstax.grammar.gamma.impl.StartVertexImpl;
import org.hisrc.jstax.grammar.graph.GraphOptimizer;
import org.hisrc.jstax.grammar.graph.StateMachineBuilder;
import org.hisrc.jstax.grammar.parser.StateMachineParser;
import org.hisrc.jstax.grammar.state.StateMachine;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.StringResult;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.jgrapht.ext.GMLGraphExporter;
import org.hisrc.jstax.jgrapht.ext.GraphExporter;
import org.hisrc.jstax.xml.XML;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.ext.VertexNameProvider;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class XMLGraphTest {

	@Test
	public void test() throws IOException {
		final DirectedGraph<Vertex, Edge> graph = new DefaultDirectedGraph<Vertex, Edge>(
				new EdgeFactory<Vertex, Edge>() {
					@Override
					public Edge createEdge(Vertex sourceVertex,
							Vertex targetVertex) {
						return new EdgeImpl();
					}
				});

		final Vertex start = new StartVertexImpl();
//		final Vertex end = new ChVertexImpl(new CharImpl(CharConstants.EOF));
		final Vertex end = new EndVertexImpl();
		graph.addVertex(start);
//		graph.addVertex(last);
		graph.addVertex(end);
//		graph.addEdge(last, end);
//		XML.COMMENT.buildGraph(graph, start, end);
//		XML.CHAR_DATA.buildGraph(graph, start, end);
		XML.PROLOG.buildGraph(graph, start, end);

		new GraphOptimizer(graph).optimize();

		GraphExporter<Vertex, Edge> graphExporter = new GMLGraphExporter<Vertex, Edge>();

		graphExporter.exportGraph(
				graph,
				new VertexNameProvider<Vertex>() {

					@Override
					public String getVertexName(Vertex vertex) {
						return vertex.getName().replace("\"", "\'\'")
								.replace("&", "AMP");
					}
				}, new File("C:\\Projects\\workspaces\\jstax\\jstax\\xml.gml"),
				LoggerFactory.getLogger(getClass()));

		final StateMachine stateMachine = new StateMachineBuilder()
				.buildStateMachine(graph);
		final StateMachineParser parser = new StateMachineParser(stateMachine);

//		final Input input = new StringInput("<!-- Comment -->");
		final Input input = new StringInput("<?xml    version=\"1.0\"    encoding=\"utf-8\"    standalone=\"yes\"  ?>");
		final Result result = new StringResult();
		final ErrorHandler errorHandler = ThrowingErrorHandler.INSTANCE;
		parser.parse(input, result, errorHandler);

	}

}
