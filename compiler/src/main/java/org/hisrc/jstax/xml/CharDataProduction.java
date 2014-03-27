package org.hisrc.jstax.xml;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.ChVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.graph.impl.EmptyVertexImpl;
import org.hisrc.jstax.grammar.operation.CharDataError;
import org.hisrc.jstax.grammar.operation.Characters;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.impl.AbstractProduction;
import org.jgrapht.DirectedGraph;

public class CharDataProduction extends AbstractProduction {

	private final Ch content;
	private final Char rsb0;
	private final Char rsb1;
	private final Char gt;

	public CharDataProduction(Ch content, Char rsb0, Char rsb1,
			Char gt) {
		super(None.INSTANCE);
		this.content = content;
		this.rsb0 = Validate.notNull(rsb0);
		this.rsb1 = Validate.notNull(rsb1);
		this.gt = Validate.notNull(gt);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {

		final Vertex s = new EmptyVertexImpl();
		graph.addVertex(s);
		graph.addEdge(start, s, new EdgeImpl());
		final Vertex e = new EmptyVertexImpl();
		graph.addVertex(e);
		graph.addEdge(e, end, new EdgeImpl());

		final Vertex vrsb0 = new ChVertexImpl(this.rsb0);
		graph.addVertex(vrsb0);
		final Vertex vrsb1 = new ChVertexImpl(this.rsb1);
		graph.addVertex(vrsb1);
		final Vertex vgt = new ChVertexImpl(this.gt);
		graph.addVertex(vgt);

		final Vertex vMinusRSB0 = new ChVertexImpl(content.minus(
				"CHAR_DATA_END_CHAR_MINUS_RSB0", this.rsb0));
		graph.addVertex(vMinusRSB0);
		final Vertex vMinusRSB1 = new ChVertexImpl(content.minus(
				"CHAR_DATA_CHAR_MINUS_RSB1", this.rsb1));
		graph.addVertex(vMinusRSB1);
		final Vertex vMinusGTRSB1 = new ChVertexImpl(content.minus(
				"CHAR_DATA_CHAR_MINUS_GT", this.gt).minus(
				"CHAR_DATA_CHAR_MINUS_GT_RSB1", this.rsb1));
		graph.addVertex(vMinusGTRSB1);

		graph.addEdge(s, vrsb0, new EdgeImpl());
		graph.addEdge(s, vMinusRSB0, new EdgeImpl());

		graph.addEdge(vrsb0, vrsb1, new EdgeImpl());
		graph.addEdge(vrsb0, vMinusRSB1, new EdgeImpl());
		graph.addEdge(vrsb0, e, new EdgeImpl(Characters.INSTANCE));

		graph.addEdge(vrsb1, vgt, new EdgeImpl());
		graph.addEdge(vrsb1, vMinusGTRSB1, new EdgeImpl());
		graph.addEdge(vrsb1, vrsb1, new EdgeImpl());
		graph.addEdge(vrsb1, e, new EdgeImpl(Characters.INSTANCE));

		graph.addEdge(vgt, e, new EdgeImpl(CharDataError.INSTANCE));

		graph.addEdge(vMinusRSB0, s, new EdgeImpl());
		graph.addEdge(vMinusRSB1, s, new EdgeImpl());
		graph.addEdge(vMinusGTRSB1, s, new EdgeImpl());

		graph.addEdge(vMinusRSB0, e, new EdgeImpl(Characters.INSTANCE));
		graph.addEdge(vMinusRSB1, e, new EdgeImpl(Characters.INSTANCE));
		graph.addEdge(vMinusGTRSB1, e, new EdgeImpl(Characters.INSTANCE));
	}

}
