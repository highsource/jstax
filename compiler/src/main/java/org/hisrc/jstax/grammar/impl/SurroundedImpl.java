package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.Production;
import org.hisrc.jstax.grammar.Str;
import org.hisrc.jstax.grammar.Surrounded;

public class SurroundedImpl extends SequenceImpl implements Surrounded {

	private final Str start;
	private final Production content;
	private final Str end;

	public SurroundedImpl(Str start, Production content, Str end) {
		super(start, Grammar.terminated(content, end), end);
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
