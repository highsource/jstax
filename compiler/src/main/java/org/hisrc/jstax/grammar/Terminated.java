package org.hisrc.jstax.grammar;

public interface Terminated extends Sequence {

	public Production getContent();

	public Str getEnd();

}
