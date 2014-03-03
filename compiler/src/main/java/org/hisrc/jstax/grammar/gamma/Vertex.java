package org.hisrc.jstax.grammar.gamma;

import org.jgrapht.DirectedGraph;

public interface Vertex {

	public String getName();

	public boolean optimize(DirectedGraph<Vertex, Edge> graph);

	public <R> R accept(VertexVisitor<R> visitor);
}
