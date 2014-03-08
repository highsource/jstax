package org.hisrc.jstax.io;

public interface Input extends Located {

	public boolean hasNextChar();
	
	public char readChar();

	public char peekChar();
	
	public char peekChar(int offset);

}
