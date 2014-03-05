//package org.hisrc.jstax.grammar.gamma.impl;
//
//import org.apache.commons.lang3.Validate;
//import org.hisrc.jstax.grammar.gamma.Edge;
//import org.hisrc.jstax.grammar.gamma.ButNot;
//import org.hisrc.jstax.grammar.gamma.Production;
//import org.hisrc.jstax.grammar.gamma.Vertex;
//import org.jgrapht.DirectedGraph;
//
//public class ButNotImpl extends AbstractProduction implements ButNot {
//
//	private final Production content;
//
//	private final Production negativeContent;
//
//	public ButNotImpl(Production content, Production negativeContent) {
//		Validate.notNull(content);
//		Validate.notNull(negativeContent);
//		this.content = content;
//		this.negativeContent = negativeContent;
//	}
//
//	@Override
//	public Production getContent() {
//		return this.content;
//	}
//
//	@Override
//	public Production getNegativeContent() {
//		return this.negativeContent;
//	}
//
//	@Override
//	public Production butNot(Production that) {
//		return new ButNotImpl(getContent(), getNegativeContent().or(that));
//	}
//
//	@Override
//	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
//			Vertex end) {
//		// TODO Consider butNot
//		getContent().buildGraph(graph, start, end);
////		throw new UnsupportedOperationException();
//
//	}
//	
//	@Override
//	public String toString() {
//		return getContent().toString() + " - " + getNegativeContent().toString();
//	}
//
//}
