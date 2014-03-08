package org.hisrc.jstax.grammar.gamma;

import org.jgrapht.DirectedGraph;

public interface Production {

	public String getIdentifierName();

	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end);

}
