package org.hisrc.jstax.grammar;

import java.util.List;

import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public interface PrefixedChoice extends Choice {

	@Override
	public List<? extends Prefix> getElements();

	public boolean matches(Input input);

	public void read(Input input, Result result, ErrorHandler errorHandler);
}
