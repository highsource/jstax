package org.hisrc.jstax.grammar.gamma.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Choice;
import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.jgrapht.DirectedGraph;

public class ChoiceImpl extends AbstractProduction implements Choice {

	private final List<Production> options;
	private final List<Production> unmodifiableOptions;

	public ChoiceImpl(String name, Production... options) {
		super(Validate.notNull(name));
		Validate.noNullElements(options);
		this.options = new ArrayList<Production>(Arrays.asList(options));
		this.unmodifiableOptions = Collections.unmodifiableList(this.options);
	}

	public List<Production> getOptions() {
		return this.unmodifiableOptions;
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {
		for (Production option : this.options) {
			option.buildGraph(graph, start, end);
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (Iterator<Production> iterator = this.options.iterator(); iterator
				.hasNext();) {
			Production option = iterator.next();
			sb.append(option);
			if (iterator.hasNext()) {
				sb.append(" | ");
			}
		}
		return sb.toString();
	}

}
