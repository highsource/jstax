package org.hisrc.jstax.grammar;

public interface Some extends Production {

	public boolean isOptional();

	public boolean isRepeatable();

	public Production getElement();

}
