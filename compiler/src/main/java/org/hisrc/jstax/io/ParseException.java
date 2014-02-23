package org.hisrc.jstax.io;

import org.hisrc.jstax.io.impl.LocatorImpl;

public class ParseException extends Exception {

	private static final long serialVersionUID = 1767487990295343825L;

	private final Locator locator;

	public ParseException(String message, Locator locator) {
		super(message);
		this.locator = new LocatorImpl(locator);
	}

	public ParseException(String message, Locator locator, Throwable cause) {
		super(message, cause);
		this.locator = new LocatorImpl(locator);
	}

	public Locator getLocator() {
		return locator;
	}

}
