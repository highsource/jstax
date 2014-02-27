package org.hisrc.jstax.grammar;

import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public interface OptionalPrefixedChoice extends Production {

	public PrefixedChoice getElement();

	public void read(Input input, Result result, ErrorHandler errorHandler);
}
