package org.hisrc.jstax.grammar.gamma;

public interface Surrounded extends Sequence {

	public Str getStart();

	public Production getContent();

	public Str getEnd();

}
