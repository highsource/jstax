package org.hisrc.jstax.io.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.io.Locator;

public class LocatorImpl implements Locator {

	private final int lineNumber;
	private final int columnNumber;

	public LocatorImpl(Locator locator) {
		this(
		//
				Validate.notNull(locator).getLineNumber(),
				//
				Validate.notNull(locator).getColumnNumber());
	}

	public LocatorImpl(int lineNumber, int columnNumber) {
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}

	@Override
	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public int getColumnNumber() {
		return columnNumber;
	}

}
