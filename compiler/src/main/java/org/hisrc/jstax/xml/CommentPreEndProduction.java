package org.hisrc.jstax.xml;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.ChVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.impl.AbstractProduction;
import org.jgrapht.DirectedGraph;

public class CommentPreEndProduction extends AbstractProduction {

	private final Char m0;
	private final Char m1;

	public CommentPreEndProduction(String name, Char m0, Char m1) {
		super(None.INSTANCE, name);
		this.m0 = Validate.notNull(m0);
		this.m1 = Validate.notNull(m1);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {

		final Vertex vm0 = new ChVertexImpl(this.m0);
		graph.addVertex(vm0);
		final Vertex vm1 = new ChVertexImpl(this.m1);
		graph.addVertex(vm1);
		final Vertex vMinusM0 = new ChVertexImpl(XML.CHAR.minus(
				"COMMENT_END_CHAR_MINUS_M0", this.m0));
		graph.addVertex(vMinusM0);
		final Vertex vMinusM1 = new ChVertexImpl(XML.CHAR.minus(
				"COMMENT_END_CHAR_MINUS_M1", this.m1));
		graph.addVertex(vMinusM1);

		graph.addEdge(start, vm0, new EdgeImpl());
		graph.addEdge(start, vMinusM0, new EdgeImpl());

		graph.addEdge(vm0, vm1, new EdgeImpl());
		graph.addEdge(vm0, vMinusM1, new EdgeImpl());

		graph.addEdge(vm1, end, new EdgeImpl());

		graph.addEdge(vMinusM0, start, new EdgeImpl());
		graph.addEdge(vMinusM1, start, new EdgeImpl());
	}

}
