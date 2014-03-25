package org.hisrc.jstax.io;

public interface Input extends Located {

	public char readChar();

	public char peekChar();

}
