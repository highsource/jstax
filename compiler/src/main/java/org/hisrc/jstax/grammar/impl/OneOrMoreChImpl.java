package org.hisrc.jstax.grammar.impl;

import java.util.Collections;
import java.util.List;

import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.OneOrMoreCh;
import org.hisrc.jstax.grammar.Prefix;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public class OneOrMoreChImpl extends OneOrMoreImpl implements OneOrMoreCh {

	private final Ch element;

	private final List<? extends Prefix> elements;

	public OneOrMoreChImpl(Ch element) {
		super(element);
		this.element = element;
		this.elements = Collections.singletonList(this);
	}

	@Override
	public Ch getElement() {
		return this.element;
	}

	public boolean check(Input input) {
		return this.element.matches(input);
	}

	public void read(Input input, Result result, ErrorHandler handler) {
		this.element.read(input, result, handler);
		while (this.element.matches(input)) {
			this.element.read(input, result, handler);
		}
	}

	@Override
	public Ch getFirst() {
		return this.element;
	}

	@Override
	public boolean matches(Input input) {
		return this.element.matches(input);
	}

	@Override
	public List<? extends Prefix> getElements() {
		return this.elements;
	}

}
