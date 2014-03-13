package org.hisrc.jstax.io;

public interface Result {

	public void pushChar(char ch);

	public char popChar();

	public String popString();

}
