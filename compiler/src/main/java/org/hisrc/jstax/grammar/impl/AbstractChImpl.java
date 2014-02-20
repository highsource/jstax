package org.hisrc.jstax.grammar.impl;

import java.util.Collections;
import java.util.List;

import org.hisrc.jstax.grammar.Ch;

public class AbstractChImpl implements Ch {

	private final List<Ch> unmodifiableElements;

	public AbstractChImpl() {
		this.unmodifiableElements = Collections.unmodifiableList(Collections
				.<Ch> singletonList(this));
	}

	@Override
	public List<Ch> getElements() {
		return this.unmodifiableElements;
	}

}
