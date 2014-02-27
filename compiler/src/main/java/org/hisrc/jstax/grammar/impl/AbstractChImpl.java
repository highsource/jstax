package org.hisrc.jstax.grammar.impl;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.io.CharConstants;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Locator;
import org.hisrc.jstax.io.ParseException;
import org.hisrc.jstax.io.Result;

public abstract class AbstractChImpl implements Ch {

	private final List<Ch> unmodifiableElements;

	public AbstractChImpl() {
		this.unmodifiableElements = Collections.unmodifiableList(Collections
				.<Ch> singletonList(this));
	}

	@Override
	public List<Ch> getElements() {
		return this.unmodifiableElements;
	}

	@Override
	public void read(Input input, Result result, ErrorHandler handler) {
		final char ch = input.peekChar();
		if (ch == CharConstants.EOF) {
			final Locator locator = input.getLocator();
			handler.error(new ParseException(
					"Could not read the next character, the stream is empty.",
					locator));
		} else {
			if (matches(input)) {
				result.pushChar(input.readChar());
			} else {
				final Locator locator = input.getLocator();
				handler.error(new ParseException(
						MessageFormat
								.format("Unexpected character [{0}] in input stream at [{1}, {2}].",
										ch, locator.getLineNumber(),
										locator.getColumnNumber()), locator));
			}
		}
	}

	@Override
	public boolean matches(Input input) {
		return matches(input);
	}

	@Override
	public Ch getFirst() {
		return this;
	}
}
