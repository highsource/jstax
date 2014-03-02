package org.hisrc.jstax.grammar.gamma;

import org.jgrapht.DirectedGraph;

public interface Production {

	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end);

	// A?
	public Production zeroOrOne();

	// A+
	public Production oneOrMore();

	// A*
	public Production zeroOrMore();

	// A|B
	public Production or(Production that);

	// A B
	public Production followedBy(Production that);

	// A - B
	public Production butNot(Production that);
}
