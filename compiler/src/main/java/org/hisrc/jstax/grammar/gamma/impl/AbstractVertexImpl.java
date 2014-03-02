package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.jgrapht.DirectedGraph;

public abstract class AbstractVertexImpl implements Vertex {

	@Override
	public boolean optimize(DirectedGraph<Vertex, Edge> graph) {
		return false;
	}
}
