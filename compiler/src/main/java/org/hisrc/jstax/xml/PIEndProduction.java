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

public class PIEndProduction extends AbstractProduction {

	private final Char q;
	private final Char gt;

	public PIEndProduction(Char q, Char gt) {
		super(None.INSTANCE);
		this.q = Validate.notNull(q);
		this.gt = Validate.notNull(gt);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {

		final Vertex vq = new ChVertexImpl(this.q);
		graph.addVertex(vq);
		final Vertex vgt = new ChVertexImpl(this.gt);
		graph.addVertex(vgt);
		final Vertex vMinusQ = new ChVertexImpl(XML.CHAR.minus(
				"PI_END_CHAR_MINUS_Q", this.q));
		graph.addVertex(vMinusQ);
		final Vertex vMinusGTQ = new ChVertexImpl(XML.CHAR.minus(
				"PI_END_CHAR_MINUS_GT", this.gt).minus(
				"PI_END_CHAR_MINUS_GT_Q", this.q));
		graph.addVertex(vMinusGTQ);

		graph.addEdge(start, vq, new EdgeImpl());
		graph.addEdge(start, vMinusQ, new EdgeImpl());

		graph.addEdge(vq, vgt, new EdgeImpl());
		graph.addEdge(vq, vq, new EdgeImpl());
		graph.addEdge(vq, vMinusGTQ, new EdgeImpl());

		graph.addEdge(vgt, end, new EdgeImpl(this.gt.getOperation()));

		graph.addEdge(vMinusQ, start, new EdgeImpl());
		graph.addEdge(vMinusGTQ, start, new EdgeImpl());

	}

}
