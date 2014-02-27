package org.hisrc.jstax.grammar.impl;

import org.hisrc.jstax.grammar.OptionalPrefixedChoice;
import org.hisrc.jstax.grammar.PrefixedChoice;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public class OptionalPrefixedChoiceImpl implements OptionalPrefixedChoice {

	private final PrefixedChoice element;

	public OptionalPrefixedChoiceImpl(PrefixedChoice element) {
		this.element = element;
	}

	@Override
	public PrefixedChoice getElement() {
		return this.element;
	}
	
	@Override
	public void read(Input input, Result result, ErrorHandler errorHandler) {
		if (this.element.matches(input))
		{
			this.element.read(input, result, errorHandler);
		}
		
	}

}
