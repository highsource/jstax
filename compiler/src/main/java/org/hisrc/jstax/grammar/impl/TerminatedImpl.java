package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Production;
import org.hisrc.jstax.grammar.Str;
import org.hisrc.jstax.grammar.Terminated;

public class TerminatedImpl extends SequenceImpl implements Terminated {

	private final Production content;

	private final Str end;

	public TerminatedImpl(Production content, Str end) {
		super(content, end);
		this.content = content;
		this.end = end;
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
