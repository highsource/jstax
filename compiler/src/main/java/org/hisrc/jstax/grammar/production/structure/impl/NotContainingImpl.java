package org.hisrc.jstax.grammar.production.structure.impl;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.ChVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.graph.impl.EmptyVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.ErrorVertexImpl;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.impl.AbstractProduction;
import org.hisrc.jstax.grammar.production.structure.Str;
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
		graph.addEdge(start, mid, new EdgeImpl());
		graph.addEdge(mid, end, new EdgeImpl());

		final Vertex error = new ErrorVertexImpl(MessageFormat.format(
				"Met the unexpected substring [{0}].", terminator.toString()));
		graph.addVertex(error);
		final List<Ch> elements = terminator.getElements();

		Vertex current = mid;
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
			graph.addEdge(current, terminatorCharVertex, new EdgeImpl());
			graph.addEdge(current, nonTerminatorCharVertex, new EdgeImpl());
			graph.addEdge(nonTerminatorCharVertex, mid, new EdgeImpl());

			if (index == (elements.size() - 1)) {
				graph.addEdge(terminatorCharVertex, error, new EdgeImpl());
			}
			current = terminatorCharVertex;
		}

	}
}
