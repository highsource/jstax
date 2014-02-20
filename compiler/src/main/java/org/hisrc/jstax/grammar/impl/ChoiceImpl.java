package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Choice;
import org.hisrc.jstax.grammar.Production;

public class ChoiceImpl extends AbstractCompositeImpl implements Choice {

	public ChoiceImpl(Production... elements) {
		super(elements);
	}

}
