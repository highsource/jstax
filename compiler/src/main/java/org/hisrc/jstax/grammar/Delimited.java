package org.hisrc.jstax.grammar;

public interface Delimited extends Sequence {

	public Prefix getElement();

	public Prefix getDelimiter();

}
