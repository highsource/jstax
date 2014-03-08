package org.hisrc.jstax.grammar.gamma.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Sequence;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.EmptyVertexImpl;
import org.jgrapht.DirectedGraph;

public class SequenceImpl extends AbstractProduction implements Sequence {

	private final List<Production> elements;
	private final List<Production> unmodifiableElements;

	public SequenceImpl(String name, Production... elements) {
		super(Validate.notNull(name));
		Validate.noNullElements(elements);
		this.elements = new ArrayList<Production>(Arrays.asList(elements));
		this.unmodifiableElements = Collections.unmodifiableList(this.elements);
	}

	public List<? extends Production> getElements() {
		return this.unmodifiableElements;
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {

		Vertex previous = start;
		Vertex next = null;
		for (Iterator<Production> iterator = this.elements.iterator(); iterator
				.hasNext();) {
			final Production element = iterator.next();
			next = iterator.hasNext() ? new EmptyVertexImpl() : end;
			graph.addVertex(next);
			element.buildGraph(graph, previous, next);
			previous = next;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (Iterator<Production> iterator = this.elements.iterator(); iterator
				.hasNext();) {
			Production option = iterator.next();
			sb.append(option);
			if (iterator.hasNext()) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

}
