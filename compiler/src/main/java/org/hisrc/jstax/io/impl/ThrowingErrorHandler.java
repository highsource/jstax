package org.hisrc.jstax.io.impl;

import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.ParseException;

public class ThrowingErrorHandler implements ErrorHandler {

	@Override
	public void error(ParseException pex) {
		throw new RuntimeException(pex);

	}

	public static final ErrorHandler INSTANCE = new ThrowingErrorHandler();
}
