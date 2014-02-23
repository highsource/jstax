package org.hisrc.jstax.io.impl;

import org.hisrc.jstax.io.Result;

public class StringResult implements Result {
	private static final int DEFAULT_CHUNK_SIZE = 1024;

	private char[] buffer = new char[DEFAULT_CHUNK_SIZE];
	private int length = this.buffer.length;
	private int count = 0;

	@Override
	public void pushChar(char ch) {
		if (this.count == this.length) {
			char[] newBuffer = new char[this.length + DEFAULT_CHUNK_SIZE];
			System.arraycopy(this.buffer, 0, newBuffer, 0, this.length);
			this.buffer = newBuffer;
			this.length = this.buffer.length;
		}
		this.buffer[this.count++] = ch;
	}

	public String toString() {
		return String.valueOf(this.buffer, 0, this.count);
	}

}
