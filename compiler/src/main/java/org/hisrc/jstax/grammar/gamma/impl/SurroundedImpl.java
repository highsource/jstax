package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.GammaProducer;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Str;
import org.hisrc.jstax.grammar.gamma.Surrounded;

public class SurroundedImpl extends SequenceImpl implements Surrounded {

	private final Str start;
	private final Production content;
	private final Str end;

	public SurroundedImpl(Str start, Production content, Str end) {
		super(start, GammaProducer.terminated(content, end), end);
		this.start = start;
		this.content = content;
		this.end = end;
	}

	@Override
	public Str getStart() {
		return start;
	}

	@Override
	public Production getContent() {
		return content;
	}

	@Override
	public Str getEnd() {
		return end;
	}
}
