package org.hisrc.jstax.grammar.production;

import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.jgrapht.DirectedGraph;

public interface Production {

	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end);

}
