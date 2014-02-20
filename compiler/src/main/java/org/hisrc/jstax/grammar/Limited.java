package org.hisrc.jstax.grammar;

public interface Limited extends Sequence {

	public Production getStart();

	public Production getContent();

	public Production getEnd();
}
