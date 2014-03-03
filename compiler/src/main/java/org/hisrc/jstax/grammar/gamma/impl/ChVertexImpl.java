package org.hisrc.jstax.grammar.gamma.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Ch;
import org.hisrc.jstax.grammar.gamma.ChVertex;
import org.hisrc.jstax.grammar.gamma.VertexVisitor;

public class ChVertexImpl extends AbstractVertexImpl implements ChVertex {

	private final Ch content;

	public ChVertexImpl(Ch content) {
		Validate.notNull(content);
		this.content = content;
	}

	@Override
	public Ch getContent() {
		return this.content;
	}

	@Override
	public String getName() {
		return "[" + getContent().toString() + "]";
	}
	
	@Override
	public <R> R accept(VertexVisitor<R> visitor) {
		Validate.notNull(visitor);
		return visitor.visitVertex(this);
	}

}
