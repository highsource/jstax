package org.hisrc.jstax.grammar;

public interface Excluding extends Production, PrefixedChoice {

	public PrefixedChoice getContent();

	public PrefixedChoice getExclusion();

}
