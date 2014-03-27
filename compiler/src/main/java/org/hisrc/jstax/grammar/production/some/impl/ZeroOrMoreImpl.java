package org.hisrc.jstax.grammar.production.some.impl;

import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.graph.impl.EmptyVertexImpl;
import org.hisrc.jstax.grammar.production.Production;
import org.hisrc.jstax.grammar.production.some.ZeroOrMore;
import org.jgrapht.DirectedGraph;

public class ZeroOrMoreImpl extends AbstractSomeImpl implements ZeroOrMore {

	public ZeroOrMoreImpl(Production production) {
		super(production);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {
		final Vertex middle = new EmptyVertexImpl();
		graph.addVertex(middle);
		// s -> e
		graph.addEdge(start, end, new EdgeImpl());
		// m -> e
		graph.addEdge(middle, end, new EdgeImpl());
		// m -> s
		graph.addEdge(middle, start, new EdgeImpl());
		// s -> content -> m
		getContent().buildGraph(graph, start, middle);
	}

	@Override
	public String toString() {
		return getContent().toString() + "*";
	}
}
