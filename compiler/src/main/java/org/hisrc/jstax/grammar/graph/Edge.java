package org.hisrc.jstax.grammar.graph;

import java.util.Collection;

import org.hisrc.jstax.grammar.operation.Operation;

public interface Edge extends Cloneable {

	public Operation getOperation();

	public Edge sequence(Edge that);

	public Edge merge(Collection<Edge> edges);

	public Edge clone();

}
