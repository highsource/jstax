package org.hisrc.jstax.grammar;

public interface Delimited extends Sequence {

	public Production getElement();

	public Production getDelimiter();

}
