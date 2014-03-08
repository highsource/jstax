package org.hisrc.jstax.grammar.some.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.EmptyVertexImpl;
import org.hisrc.jstax.grammar.some.ZeroOrMore;
import org.jgrapht.DirectedGraph;

public class ZeroOrMoreImpl extends AbstractSomeImpl implements ZeroOrMore {

	public ZeroOrMoreImpl(String name, Production production) {
		super(Validate.notNull(name), production);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {
		final Vertex middle = new EmptyVertexImpl();
		graph.addVertex(middle);
		// s -> e
		graph.addEdge(start, end);
		// m -> e
		graph.addEdge(middle, end);
		// m -> s
		graph.addEdge(middle, start);
		// s -> content -> m
		getContent().buildGraph(graph, start, middle);
	}

	@Override
	public String toString() {
		return getContent().toString() + "*";
	}
}