package org.hisrc.jstax.grammar.gamma;

// Element (Delimiter Element)*
public interface Delimited extends Sequence {

	public Production getElement();

	public Production getDelimiter();

}
