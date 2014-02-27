package org.hisrc.jstax.grammar.impl;

import java.util.Collections;
import java.util.List;

import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.NotIncluding;
import org.hisrc.jstax.grammar.Prefix;
import org.hisrc.jstax.grammar.Str;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public class NotIncludingImpl extends ExcludingImpl implements NotIncluding,
		Prefix {

	private final Ch element;
	private final Str exclusion;
	private final List<? extends Prefix> elements;

	// element * - (element* exclusion element*)
	public NotIncludingImpl(Ch element, Str exclusion) {
		super(Grammar.zeroOrMore(element), exclusion);
		this.element = element;
		this.exclusion = exclusion;
		this.elements = Collections.singletonList(this);
	}

	public Ch getElement() {
		return this.element;
	}

	@Override
	public Str getExclusion() {
		return exclusion;
	}

	@Override
	public Ch getFirst() {
		return element;
	}

	public boolean matches(Input input) {
		return this.element.matches(input) && !this.exclusion.matches(input);
	}

	@Override
	public void read(Input input, Result result, ErrorHandler errorHandler) {
		while (!this.exclusion.matches(input)) {
			this.element.read(input, result, errorHandler);
		}
	}

	@Override
	public List<? extends Prefix> getElements() {
		return this.elements;
	}
}
