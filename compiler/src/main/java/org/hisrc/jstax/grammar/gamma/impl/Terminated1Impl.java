package org.hisrc.jstax.grammar.gamma.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Ch;
import org.hisrc.jstax.grammar.gamma.Char;
import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.Str;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.jgrapht.DirectedGraph;

public class Terminated1Impl extends AbstractProduction {

	private final Ch _char;

	private final Str terminator;

	public Terminated1Impl(String name, Ch content, Str terminator) {
		super(Validate.notNull(name));
		Validate.notNull(content);
		Validate.notNull(terminator);
		this._char = content;
		this.terminator = terminator;
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {
		Validate.notNull(_char);
		Validate.notNull(terminator);
		final List<Ch> elements = terminator.getElements();

		Vertex current = start;
		for (int index = 0; index < elements.size(); index++) {
			final Char terminatorChar = (Char) elements.get(index);
			final Ch nonTerminatorChar = _char.minus(_char.getIdentifierName()
					+ "_MINUS_" + terminatorChar.getIdentifierName(),
					terminatorChar);

			final Vertex terminatorCharVertex = new ChVertexImpl(terminatorChar);
			final Vertex nonTerminatorCharVertex = new ChVertexImpl(
					nonTerminatorChar);
			graph.addVertex(terminatorCharVertex);
			graph.addVertex(nonTerminatorCharVertex);
			graph.addEdge(current, terminatorCharVertex);
			graph.addEdge(current, nonTerminatorCharVertex);
			graph.addEdge(nonTerminatorCharVertex, start);

			if (index == (elements.size() - 1)) {
				graph.addEdge(terminatorCharVertex, end);
			}
			current = terminatorCharVertex;
		}

	}

}
