package org.hisrc.jstax.grammar.production.structure.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.ChVertex;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.ChVertexImpl;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.impl.AbstractProduction;
import org.jgrapht.DirectedGraph;

public class Terminated1Impl extends AbstractProduction {

	private final Ch _char;

	private final Char[] terminator;

	public Terminated1Impl(String name, Ch content, Char... terminator) {
		super(Validate.notNull(name));
		Validate.notNull(content);
		Validate.notEmpty(terminator);
		Validate.noNullElements(terminator);
		this._char = content;
		this.terminator = terminator;
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {
		Validate.notNull(_char);
		Validate.notNull(terminator);
		final Char[] elements = this.terminator;

		Vertex current = start;
		ChVertex firstTerminatorCharVertex = null;
		for (int index = 0; index < elements.length; index++) {
			final Char terminatorChar = elements[index];
			final ChVertex terminatorCharVertex = new ChVertexImpl(
					terminatorChar);
			if (index == 0) {
				firstTerminatorCharVertex = terminatorCharVertex;
			}

			final Ch nonTerminatorChar;
			nonTerminatorChar = _char.minus(
					_char.getIdentifierName() + "_MINUS_"
							+ terminatorChar.getIdentifierName(),
					terminatorChar).minus(
					_char.getIdentifierName() + "_MINUS_"
							+ terminatorChar.getIdentifierName() + "_"
							+ elements[0].getIdentifierName(), elements[0]);

			final ChVertex nonTerminatorCharVertex = new ChVertexImpl(
					nonTerminatorChar);
			graph.addVertex(terminatorCharVertex);
			graph.addVertex(nonTerminatorCharVertex);
			graph.addEdge(current, terminatorCharVertex);
			graph.addEdge(current, nonTerminatorCharVertex);
			graph.addEdge(nonTerminatorCharVertex, start);

			if (index == (elements.length - 1)) {
				graph.addEdge(terminatorCharVertex, end);
				graph.addEdge(current, firstTerminatorCharVertex);
			}
			current = terminatorCharVertex;
		}

	}

}
