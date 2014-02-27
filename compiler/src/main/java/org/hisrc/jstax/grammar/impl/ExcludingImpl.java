package org.hisrc.jstax.grammar.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Excluding;
import org.hisrc.jstax.grammar.Prefix;
import org.hisrc.jstax.grammar.PrefixedChoice;
import org.hisrc.jstax.grammar.Production;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public class ExcludingImpl implements Excluding {

	private final PrefixedChoice content;
	private final PrefixedChoice exclusion;
	private final List<? extends Prefix> elements;

	public ExcludingImpl(PrefixedChoice content, PrefixedChoice exclusion) {
		Validate.notNull(content);
		Validate.notNull(exclusion);
		this.content = content;
		this.exclusion = exclusion;
		this.elements = content.getElements();
	}

	public PrefixedChoice getContent() {
		return content;
	}

	public PrefixedChoice getExclusion() {
		return exclusion;
	}

	@Override
	public List<? extends Prefix> getElements() {
		return this.elements;
	}

	@Override
	public boolean matches(Input input) {
		return this.content.matches(input) && !exclusion.matches(input);
	}

	@Override
	public void read(Input input, Result result, ErrorHandler errorHandler) {
		this.content.read(input, result, errorHandler);
	}

}
