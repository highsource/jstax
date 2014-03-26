package org.hisrc.jstax.io;

public interface Result {

	public void pushChar(char ch);

	public char popChar();

	public <E extends Throwable> void popChars(CharsDestination<E> destination) throws E;

	public String popString();
	
	public void clear();

	public interface CharsDestination<E extends Throwable> {
		public void writeCharacters(char[] text, int start, int len) throws E;
	}

}
