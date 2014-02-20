package org.hisrc.jstax.grammar;

public interface Surrounded extends Sequence {

	public Str getStart();

	public Production getContent();

	public Str getEnd();

}
