package org.hisrc.jstax.grammar;

public interface Surrounded extends PrefixedSequence {

	public Str getStart();

	public Production getContent();

	public Str getEnd();

}
