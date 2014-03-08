package org.hisrc.jstax.grammar.gamma.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.grammar.gamma.ZeroOrOne;
import org.jgrapht.DirectedGraph;

public class ZeroOrOneImpl extends AbstractSomeImpl implements ZeroOrOne {

	public ZeroOrOneImpl(String name, Production production) {
		super(Validate.notNull(name), production);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {
		graph.addEdge(start, end);
		getContent().buildGraph(graph, start, end);
	}

	@Override
	public String toString() {
		return getContent().toString() + "?";
	}

}
