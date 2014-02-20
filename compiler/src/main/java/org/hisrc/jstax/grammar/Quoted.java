package org.hisrc.jstax.grammar;

public interface Quoted extends Choice {

	public Production getContent();

	public Chars getQuotes();

}
