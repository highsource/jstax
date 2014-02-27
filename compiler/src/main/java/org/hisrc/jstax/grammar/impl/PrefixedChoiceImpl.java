package org.hisrc.jstax.grammar.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.Prefix;
import org.hisrc.jstax.grammar.PrefixedChoice;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.ParseException;
import org.hisrc.jstax.io.Result;

public class PrefixedChoiceImpl extends ChoiceImpl implements PrefixedChoice {

	public PrefixedChoiceImpl(PrefixedChoice... elements) {
		super(elements(elements));
	}

	private static Prefix[] elements(PrefixedChoice... elements) {
		Validate.noNullElements(elements);
		final List<Prefix> result = new LinkedList<Prefix>();
		for (PrefixedChoice element : elements) {
			result.addAll(element.getElements());
		}
		return result.toArray(new Prefix[result.size()]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<? extends Prefix> getElements() {
		return (List<? extends Prefix>) super.getElements();
	}

	@Override
	public boolean matches(Input input) {
		for (PrefixedChoice element : getElements()) {
			if (element.matches(input)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void read(Input input, Result result, ErrorHandler errorHandler) {
		for (PrefixedChoice element : getElements()) {
			if (element.matches(input)) {
				element.read(input, result, errorHandler);
			}
		}
		// TODO Better message
		errorHandler.error(new ParseException(
				"None of the choices matched the input.", input.getLocator()));
	}

}
