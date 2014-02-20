package org.hisrc.jstax.grammar.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Excluding;
import org.hisrc.jstax.grammar.Production;

public class ExcludingImpl implements Excluding {

	private final Production content;
	private final Production exclusion;

	public ExcludingImpl(Production content, Production exclusion) {
		Validate.notNull(content);
		Validate.notNull(exclusion);
		this.content = content;
		this.exclusion = exclusion;
	}

	public Production getContent() {
		return content;
	}

	public Production getExclusion() {
		return exclusion;
	}

}
