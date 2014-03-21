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

public class CDEndProduction extends AbstractProduction {

	private final Char rsb0;
	private final Char rsb1;
	private final Char gt;

	public CDEndProduction(String name, Char rsb0, Char rsb1, Char gt) {
		super(None.INSTANCE, name);
		this.rsb0 = Validate.notNull(rsb0);
		this.rsb1 = Validate.notNull(rsb1);
		this.gt = Validate.notNull(gt);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {

		final Vertex vrsb0 = new ChVertexImpl(this.rsb0);
		graph.addVertex(vrsb0);
		final Vertex vrsb1 = new ChVertexImpl(this.rsb1);
		graph.addVertex(vrsb1);
		final Vertex vgt = new ChVertexImpl(this.gt);
		graph.addVertex(vgt);

		final Vertex vMinusRSB0 = new ChVertexImpl(XML.CHAR.minus(
				"CD_END_CHAR_MINUS_RSB0", this.rsb0));
		graph.addVertex(vMinusRSB0);
		final Vertex vMinusRSB1 = new ChVertexImpl(XML.CHAR.minus(
				"CD_END_CHAR_MINUS_RSB1", this.rsb1));
		graph.addVertex(vMinusRSB1);
		final Vertex vMinusGTRSB1 = new ChVertexImpl(XML.CHAR.minus(
				"CD_END_CHAR_MINUS_GT", this.gt).minus(
				"CD_END_CHAR_MINUS_GT_RSB1", this.rsb1));
		graph.addVertex(vMinusGTRSB1);

		graph.addEdge(start, vrsb0, new EdgeImpl());
		graph.addEdge(start, vMinusRSB0, new EdgeImpl());

		graph.addEdge(vrsb0, vrsb1, new EdgeImpl());
		graph.addEdge(vrsb0, vMinusRSB1, new EdgeImpl());

		graph.addEdge(vrsb1, vgt, new EdgeImpl());
		graph.addEdge(vrsb1, vMinusGTRSB1, new EdgeImpl());
		graph.addEdge(vrsb1, vrsb1, new EdgeImpl());

		graph.addEdge(vgt, end, new EdgeImpl());

		graph.addEdge(vMinusRSB0, start, new EdgeImpl());
		graph.addEdge(vMinusRSB1, start, new EdgeImpl());
		graph.addEdge(vMinusGTRSB1, start, new EdgeImpl());

	}

}
