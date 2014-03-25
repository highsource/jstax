package org.hisrc.jstax.io.impl;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.io.CharConstants;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Locator;

public class StringInput implements Input {

	private final char[] str;
	private final int length;
	private final int length_1;

	private int current = 0;
	private int next = 1;
	private boolean finished = false;
	private boolean last = false;
	private boolean lastCharWasEOL = false;
	private int lineNumber;
	private int columnNumber;

	private final Locator locator = new Locator() {

		@Override
		public int getLineNumber() {
			return StringInput.this.lineNumber;
		}

		@Override
		public int getColumnNumber() {
			return StringInput.this.columnNumber;
		}
	};

	public StringInput(String str) {
		this(Validate.notNull(str).toCharArray());
	}

	public StringInput(char[] str) {
		Validate.notNull(str);
		this.str = str;
		this.length = this.str.length;
		this.length_1 = this.length - 1;
		this.finished = (this.length == 0);
		this.last = (this.length == 1);
		this.lineNumber = 1;
		this.columnNumber = 0;
	}

	@Override
	public char readChar() {
		if (finished) {
			return (char) -1;
		} else {
			final char ch = this.str[this.current];
			this.current++;
			this.next++;
			this.finished = this.current == this.length;
			this.last = this.current == this.length_1;
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
	}

	@Override
	public char peekChar() {

		if (this.finished) {
			return CharConstants.EOF;
		} else {
			return this.str[this.current];
		}
	}

	@Override
	public Locator getLocator() {
		return this.locator;
	}

}
