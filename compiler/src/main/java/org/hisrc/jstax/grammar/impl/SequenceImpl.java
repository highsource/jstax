package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.Production;
import org.hisrc.jstax.grammar.Sequence;

public class SequenceImpl extends AbstractCompositeImpl implements Sequence {

	public SequenceImpl(Production... elements) {
		super(elements);
	}

}
