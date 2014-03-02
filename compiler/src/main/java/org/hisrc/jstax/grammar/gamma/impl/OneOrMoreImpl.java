package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.OneOrMore;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.jgrapht.DirectedGraph;

public class OneOrMoreImpl extends AbstractSomeImpl implements OneOrMore {

	public OneOrMoreImpl(Production production) {
		super(production);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {
		final Vertex middle = new EmptyVertexImpl();
		graph.addVertex(middle);
		// m -> e
		graph.addEdge(middle, end);
		// m -> s
		graph.addEdge(middle, start);
		// s -> content -> m
		getContent().buildGraph(graph, start, middle);
	}

	@Override
	public Production zeroOrMore() {
		// (A+)* = A*
		return new ZeroOrMoreImpl(getContent());
	}

	@Override
	public Production zeroOrOne() {
		// (A+)? = A*
		return new ZeroOrMoreImpl(getContent());
	}

	@Override
	public Production oneOrMore() {
		// (A+)+ = A+
		return new OneOrMoreImpl(getContent());
	}

	@Override
	public String toString() {
		return getContent().toString() + "+";
	}
}
