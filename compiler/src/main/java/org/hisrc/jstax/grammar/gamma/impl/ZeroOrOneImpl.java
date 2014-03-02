package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.grammar.gamma.ZeroOrOne;
import org.jgrapht.DirectedGraph;

public class ZeroOrOneImpl extends AbstractSomeImpl implements ZeroOrOne {

	public ZeroOrOneImpl(Production production) {
		super(production);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start, Vertex end) {
		graph.addEdge(start, end);
		getContent().buildGraph(graph, start, end);
	}

	@Override
	public Production zeroOrMore() {
		// (A?)* = A*
		return new ZeroOrMoreImpl(this.getContent());
	}

	@Override
	public Production zeroOrOne() {
		// (A?)? = A?
		return new ZeroOrOneImpl(this.getContent());
	}

	@Override
	public Production oneOrMore() {
		// (A?)+ = A*
		return new ZeroOrMoreImpl(this.getContent());
	}
	
	@Override
	public String toString() {
		return getContent().toString() + "?"; 
	}

}
