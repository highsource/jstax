package org.hisrc.jstax.grammar;

// Not including terminator
public interface Terminated extends Sequence {

	public Production getContent();

	public Str getTerminator();

}
