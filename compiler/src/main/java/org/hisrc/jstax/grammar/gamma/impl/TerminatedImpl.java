package org.hisrc.jstax.grammar.gamma.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Str;
import org.hisrc.jstax.grammar.gamma.Terminated;

public class TerminatedImpl extends SequenceImpl implements Terminated {

	private final Production content;

	private final Str terminator;

	public TerminatedImpl(Production content, Str terminator) {
		super(content);
		Validate.notNull(terminator);
		this.content = content;
		this.terminator = terminator;
	}

	@Override
	public Production getContent() {
		return content;
	}

	@Override
	public Str getTerminator() {
		return this.terminator;
	}
}
