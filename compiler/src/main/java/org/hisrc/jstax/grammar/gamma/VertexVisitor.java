package org.hisrc.jstax.grammar.gamma;

public interface VertexVisitor<R> {

	public R visitVertex(EmptyVertex vertex);

	public R visitVertex(ChVertex vertex);

	public R visitVertex(StartVertex vertex);

	public R visitVertex(EndVertex vertex);

}
