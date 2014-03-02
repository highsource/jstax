package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.grammar.gamma.ZeroOrMore;
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
		graph.addEdge(start, end);
		// m -> e
		graph.addEdge(middle, end);
		// m -> s
		graph.addEdge(middle, start);
		// s -> content -> m
		getContent().buildGraph(graph, start, middle);
	}

	@Override
	public Production zeroOrMore() {
		// (A*)* = A*
		return new ZeroOrMoreImpl(this.getContent());
	}

	@Override
	public Production zeroOrOne() {
		// (A*)? = A*
		return new ZeroOrMoreImpl(this.getContent());
	}

	@Override
	public Production oneOrMore() {
		// (A*)+ = A*
		return new ZeroOrMoreImpl(this.getContent());
	}

	@Override
	public String toString() {
		return getContent().toString() + "*";
	}
}
