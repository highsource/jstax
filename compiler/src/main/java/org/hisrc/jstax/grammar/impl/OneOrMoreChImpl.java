package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.OneOrMoreCh;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public class OneOrMoreChImpl extends OneOrMoreImpl implements OneOrMoreCh {

	private final Ch element;

	public OneOrMoreChImpl(Ch element) {
		super(element);
		this.element = element;
	}

	@Override
	public Ch getElement() {
		return this.element;
	}

	public void read(Input input, Result result, ErrorHandler handler) {
		this.element.read(input, result, handler);
		while (this.element.check(input)) {
			this.element.read(input, result, handler);
		}
	}

}
