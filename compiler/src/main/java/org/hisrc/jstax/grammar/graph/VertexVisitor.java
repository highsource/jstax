package org.hisrc.jstax.grammar.graph;

public interface VertexVisitor<R> {

	public R visitVertex(EmptyVertex vertex);

	public R visitVertex(ChVertex vertex);
}
