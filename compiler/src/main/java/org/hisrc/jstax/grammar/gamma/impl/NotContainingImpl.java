package org.hisrc.jstax.grammar.gamma.impl;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.character.Ch;
import org.hisrc.jstax.grammar.character.Char;
import org.hisrc.jstax.grammar.gamma.Str;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.ChVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.EmptyVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.ErrorVertexImpl;
import org.jgrapht.DirectedGraph;

public class NotContainingImpl extends AbstractProduction {

	private final Ch _char;

	private final Str terminator;

	public NotContainingImpl(String name, Ch content, Str terminator) {
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
		// TODO
		final Vertex mid = new EmptyVertexImpl();
		graph.addVertex(mid);
		graph.addEdge(start, mid);
		graph.addEdge(mid, end);

		final Vertex error = new ErrorVertexImpl(MessageFormat.format(
				"Met the unexpected substring [{0}].", terminator.toString()));
		graph.addVertex(error);
		final List<Ch> elements = terminator.getElements();

		Vertex current = mid;
		for (int index = 0; index < elements.size(); index++) {
			final Char terminatorChar = (Char) elements.get(index);
			final Ch nonTerminatorChar = _char.minus(
					_char.getIdentifierName() + "_MINUS_"
							+ terminatorChar.getIdentifierName(),
					terminatorChar);

			final Vertex terminatorCharVertex = new ChVertexImpl(terminatorChar);
			final Vertex nonTerminatorCharVertex = new ChVertexImpl(
					nonTerminatorChar);
			graph.addVertex(terminatorCharVertex);
			graph.addVertex(nonTerminatorCharVertex);
			graph.addEdge(current, terminatorCharVertex);
			graph.addEdge(current, nonTerminatorCharVertex);
			graph.addEdge(nonTerminatorCharVertex, mid);

			if (index == (elements.size() - 1)) {
				graph.addEdge(terminatorCharVertex, error);
			}
			current = terminatorCharVertex;
		}

	}
}
