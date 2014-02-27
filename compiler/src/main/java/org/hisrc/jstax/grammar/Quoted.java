package org.hisrc.jstax.grammar;

public interface Quoted extends PrefixedChoice {

	public PrefixedChoice getContent();

	public Chars getQuotes();

}
