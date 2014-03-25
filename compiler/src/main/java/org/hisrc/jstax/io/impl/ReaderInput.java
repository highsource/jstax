package org.hisrc.jstax.io.impl;

import java.io.IOException;
import java.io.Reader;

import org.hisrc.jstax.io.CharConstants;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Locator;

public class ReaderInput implements Input {

	private final Reader reader;
	private int lineNumber = 1;
	private int columnNumber = 0;
	private boolean lastCharWasEOL = false;
	boolean peeked;
	char nextChar = (char) -1;

	public ReaderInput(Reader reader) {
		super();
		this.reader = reader;
	}

	private final Locator locator = new Locator() {

		@Override
		public int getLineNumber() {
			return ReaderInput.this.lineNumber;
		}

		@Override
		public int getColumnNumber() {
			return ReaderInput.this.columnNumber;
		}
	};

	@Override
	public Locator getLocator() {
		return this.locator;
	}

	@Override
	public char readChar() {
		final char ch;
		if (peeked) {
			peeked = false;
			ch = nextChar;
		} else {
			try {
				ch = (char) this.reader.read();
			} catch (IOException ioex) {
				throw new RuntimeException(ioex);
			}
		}
		if (this.lastCharWasEOL) {
			this.lineNumber++;
			this.columnNumber = 1;
		} else {
			this.columnNumber++;
		}
		if (ch == CharConstants.CR) {
			if (peekChar() == CharConstants.LF) {
				this.lastCharWasEOL = false;
			} else {
				this.lastCharWasEOL = true;
			}
		} else if (ch == CharConstants.LF) {
			this.lastCharWasEOL = true;
		} else {
			this.lastCharWasEOL = false;
		}
		return ch;
	}

	@Override
	public char peekChar() {
		if (!peeked) {
			try {
				nextChar = (char) this.reader.read();
				peeked = true;
			} catch (IOException ioex) {
				// TODO EX
				throw new RuntimeException(ioex);
			}
		}
		return (char) nextChar;
	}
}
