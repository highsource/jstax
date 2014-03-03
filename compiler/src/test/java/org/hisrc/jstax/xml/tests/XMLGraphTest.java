package org.hisrc.jstax.xml.tests;

import java.io.File;
import java.io.IOException;

import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.grammar.gamma.impl.EdgeImpl;
import org.hisrc.jstax.grammar.gamma.impl.EndVertexImpl;
import org.hisrc.jstax.grammar.gamma.impl.StartVertexImpl;
import org.hisrc.jstax.grammar.graph.GraphOptimizer;
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
		final Vertex end = new EndVertexImpl();

		graph.addVertex(start);
		graph.addVertex(end);
		XML.DOCUMENT.buildGraph(graph, start, end);

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

	}

}
