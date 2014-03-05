package org.hisrc.jstax.grammar.gamma.impl;

import org.hisrc.jstax.grammar.gamma.Production;

public abstract class AbstractProduction implements Production {

	@Override
	public Production oneOrMore() {
		return new OneOrMoreImpl(this);
	}

	@Override
	public Production zeroOrMore() {
		return new ZeroOrMoreImpl(this);
	}

	@Override
	public Production zeroOrOne() {
		return new ZeroOrOneImpl(this);
	}

	@Override
	public Production or(Production that) {
		return new ChoiceImpl(this, that);
	}

	@Override
	public Production followedBy(Production that) {
		return new SequenceImpl(this, that);
	}

//	@Override
//	public Production butNot(Production that) {
//		return new ButNotImpl(this, that);
//	}

}
