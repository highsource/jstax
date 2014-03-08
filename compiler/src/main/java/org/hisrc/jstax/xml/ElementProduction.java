package org.hisrc.jstax.xml;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.EmptyVertexImpl;
import org.hisrc.jstax.grammar.production.Production;
import org.hisrc.jstax.grammar.production.impl.AbstractProduction;
import org.jgrapht.DirectedGraph;

public class ElementProduction extends AbstractProduction {

	private final Production startTagPart;
	private final Production emptyElementStartTagEnd;
	private final Production nonEmptyElementStartTagEnd;
	private final Production charData;
	private final Production nonCharacterContent;
	private final Production endTag;

	public ElementProduction(String name, Production startTagPart,
			Production emptyElementStartTagEnd,
			Production nonEmptyElementStartTagEnd, Production charData,
			Production nonCharacterContent, Production endTag) {
		super(Validate.notNull(name));
		Validate.notNull(startTagPart);
		Validate.notNull(emptyElementStartTagEnd);
		Validate.notNull(nonEmptyElementStartTagEnd);
		Validate.notNull(charData);
		Validate.notNull(nonCharacterContent);
		Validate.notNull(endTag);
		this.startTagPart = startTagPart;
		this.emptyElementStartTagEnd = emptyElementStartTagEnd;
		this.nonEmptyElementStartTagEnd = nonEmptyElementStartTagEnd;
		this.charData = charData;
		this.nonCharacterContent = nonCharacterContent;
		this.endTag = endTag;
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {

		final Vertex v0 = new EmptyVertexImpl();
		graph.addVertex(v0);
		final Vertex v1 = new EmptyVertexImpl();
		graph.addVertex(v1);
		final Vertex v2 = new EmptyVertexImpl();
		graph.addVertex(v2);
		final Vertex v3 = new EmptyVertexImpl();
		graph.addVertex(v3);
		final Vertex v4 = new EmptyVertexImpl();
		graph.addVertex(v4);
		final Vertex v5 = new EmptyVertexImpl();
		graph.addVertex(v5);
		final Vertex v6 = new EmptyVertexImpl();
		graph.addVertex(v6);

		graph.addEdge(start, v0);
		graph.addEdge(v6, end);

		this.startTagPart.buildGraph(graph, v0, v1);
		this.emptyElementStartTagEnd.buildGraph(graph, v1, v6);

		this.nonEmptyElementStartTagEnd.buildGraph(graph, v1, v2);

		graph.addEdge(v2, v3);
		charData.buildGraph(graph, v2, v3);

		graph.addEdge(v3, v0);

		this.nonCharacterContent.buildGraph(graph, v3, v4);

		graph.addEdge(v4, v5);

		this.charData.buildGraph(graph, v4, v5);

		graph.addEdge(v5, v3);

		this.endTag.buildGraph(graph, v5, v6);

	}

}
