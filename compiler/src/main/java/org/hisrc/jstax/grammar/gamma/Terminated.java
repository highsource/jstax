package org.hisrc.jstax.grammar.gamma;

// Including terminator
public interface Terminated extends Sequence {

	public Production getContent();

	public Str getTerminator();

}
