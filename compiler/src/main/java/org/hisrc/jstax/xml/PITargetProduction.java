package org.hisrc.jstax.xml;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.ChVertex;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.ChVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.production.Producer;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.impl.AbstractProduction;
import org.jgrapht.DirectedGraph;

public class PITargetProduction extends AbstractProduction {

	private final Ch nameStartChar;
	private final Ch nameChar;

	public PITargetProduction( Ch nameStartChar, Ch nameChar) {
		this.nameStartChar = Validate.notNull(nameStartChar);
		this.nameChar = Validate.notNull(nameChar);
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {

		final Char x = Producer._char("x", 'x');
		final Char X = Producer._char("X", 'X');
		final Char m = Producer._char("m", 'm');
		final Char M = Producer._char("M", 'M');
		final Char l = Producer._char("l", 'l');
		final Char L = Producer._char("L", 'L');

		final ChVertex vertex_NSC_Xx = new ChVertexImpl(nameStartChar.minus(
				"NAME_START_CHAR_MINUS_X", X).minus(
				"NAME_START_CHAR_MINUS_X_x", x));
		graph.addVertex(vertex_NSC_Xx);

		final ChVertex vertex_X = new ChVertexImpl(X);
		graph.addVertex(vertex_X);
		final ChVertex vertex_x = new ChVertexImpl(x);
		graph.addVertex(vertex_x);

		final ChVertex vertex_NC_Mm = new ChVertexImpl(nameChar.minus(
				"NAME_CHAR_MINUS_M", M).minus("NAME_CHAR_MINUS_M_m", m));
		graph.addVertex(vertex_NC_Mm);

		final ChVertex vertex_M = new ChVertexImpl(M);
		graph.addVertex(vertex_M);
		final ChVertex vertex_m = new ChVertexImpl(m);
		graph.addVertex(vertex_m);
		final ChVertex vertex_NC_Ll = new ChVertexImpl(nameChar.minus(
				"NAME_CHAR_MINUS_L", L).minus("NAME_CHAR_MINUS_L_l", l));
		graph.addVertex(vertex_NC_Ll);
		final ChVertex vertex_L = new ChVertexImpl(L);
		graph.addVertex(vertex_L);
		final ChVertex vertex_l = new ChVertexImpl(l);
		graph.addVertex(vertex_l);
		final ChVertex vertex_NC = new ChVertexImpl(nameChar);
		graph.addVertex(vertex_NC);

		// Level 0
		graph.addEdge(start, vertex_NSC_Xx, new EdgeImpl());
		graph.addEdge(start, vertex_X, new EdgeImpl());
		graph.addEdge(start, vertex_x, new EdgeImpl());

		// Level 1
		graph.addEdge(vertex_NSC_Xx, vertex_NC_Mm, new EdgeImpl());
		graph.addEdge(vertex_NSC_Xx, vertex_M, new EdgeImpl());
		graph.addEdge(vertex_NSC_Xx, vertex_m, new EdgeImpl());
		graph.addEdge(vertex_NSC_Xx, end, new EdgeImpl());

		graph.addEdge(vertex_X, vertex_NC_Mm, new EdgeImpl());
		graph.addEdge(vertex_X, vertex_M, new EdgeImpl());
		graph.addEdge(vertex_X, vertex_m, new EdgeImpl());
		graph.addEdge(vertex_X, end, new EdgeImpl());

		graph.addEdge(vertex_x, vertex_NC_Mm, new EdgeImpl());
		graph.addEdge(vertex_x, vertex_M, new EdgeImpl());
		graph.addEdge(vertex_x, vertex_m, new EdgeImpl());
		graph.addEdge(vertex_x, end, new EdgeImpl());

		// Level 2
		graph.addEdge(vertex_NC_Mm, vertex_NC_Ll, new EdgeImpl());
		graph.addEdge(vertex_NC_Mm, vertex_L, new EdgeImpl());
		graph.addEdge(vertex_NC_Mm, vertex_l, new EdgeImpl());
		graph.addEdge(vertex_NC_Mm, end, new EdgeImpl());

		graph.addEdge(vertex_M, vertex_NC_Ll, new EdgeImpl());
		graph.addEdge(vertex_M, end, new EdgeImpl());

		graph.addEdge(vertex_m, vertex_NC_Ll, new EdgeImpl());
		graph.addEdge(vertex_m, end, new EdgeImpl());

		// Level 3
		graph.addEdge(vertex_NC_Ll, vertex_NC, new EdgeImpl());
		graph.addEdge(vertex_NC_Ll, end, new EdgeImpl());

		graph.addEdge(vertex_L, vertex_NC, new EdgeImpl());
		graph.addEdge(vertex_L, end, new EdgeImpl());

		graph.addEdge(vertex_l, vertex_NC, new EdgeImpl());
		graph.addEdge(vertex_l, end, new EdgeImpl());

		// Level 4
		graph.addEdge(vertex_NC, vertex_NC, new EdgeImpl());
		graph.addEdge(vertex_NC, end, new EdgeImpl());

		// TODO Auto-generated method stub

	}

}
