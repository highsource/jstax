package org.hisrc.jstax.grammar.graph;

public interface Vertex {

	public String getIdentifierName();

	public String getName();

	public <R> R accept(VertexVisitor<R> visitor);
}
