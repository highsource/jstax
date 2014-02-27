package org.hisrc.jstax.grammar.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Composite;
import org.hisrc.jstax.grammar.Production;

public abstract class AbstractCompositeImpl implements Composite {

	private final List<Production> elements;
	private final List<Production> unmodifiableElements;

	public AbstractCompositeImpl(Production... elements) {
		Validate.noNullElements(elements);
		Validate.notEmpty(elements);
		this.elements = Arrays.asList(elements);
		this.unmodifiableElements = Collections.unmodifiableList(this.elements);
	}

	@Override
	public List<? extends Production> getElements() {
		return this.unmodifiableElements;
	}

}
