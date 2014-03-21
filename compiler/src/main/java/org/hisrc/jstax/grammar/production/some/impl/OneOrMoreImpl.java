package org.hisrc.jstax.grammar.production.some.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.graph.impl.EmptyVertexImpl;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.operation.Operation;
import org.hisrc.jstax.grammar.production.Production;
import org.hisrc.jstax.grammar.production.some.OneOrMore;
import org.jgrapht.DirectedGraph;

public class OneOrMoreImpl extends AbstractSomeImpl implements OneOrMore {

	public OneOrMoreImpl(String name, Production production) {
		this(None.INSTANCE, name, production);
	}

	public OneOrMoreImpl(Operation operation, String name, Production production) {
		super(Validate.notNull(operation), Validate.notNull(name), production);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {
		final Vertex middle = new EmptyVertexImpl();
		graph.addVertex(middle);
		// m -> e
		graph.addEdge(middle, end, new EdgeImpl());
		// m -> s
		graph.addEdge(middle, start, new EdgeImpl());
		// s -> content -> m
		getContent().buildGraph(graph, start, middle);
	}

	@Override
	public String toString() {
		return getContent().toString() + "+";
	}
}
