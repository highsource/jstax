package org.hisrc.jstax.grammar.gamma;

public interface Vertex {

	public String getIdentifierName();

	public String getName();

	public <R> R accept(VertexVisitor<R> visitor);
}
