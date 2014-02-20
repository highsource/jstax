package org.hisrc.jstax.grammar;

public interface Excluding extends Production {

	public Production getContent();

	public Production getExclusion();

}
