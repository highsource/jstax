package org.hisrc.jstax.grammar;

import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public interface ZeroOrMoreCh extends ZeroOrMore {

	@Override
	public Ch getElement();

	public void read(Input input, Result result, ErrorHandler handler);

}