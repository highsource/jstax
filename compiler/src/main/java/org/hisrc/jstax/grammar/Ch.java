package org.hisrc.jstax.grammar;

import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public interface Ch extends Production, Str {

	public void read(Input input, Result result, ErrorHandler errorHandler);

}
