package org.hisrc.jstax.grammar.gamma;

// Not including terminator
public interface Terminated extends Sequence {

	public Production getContent();

	public Str getTerminator();

}
