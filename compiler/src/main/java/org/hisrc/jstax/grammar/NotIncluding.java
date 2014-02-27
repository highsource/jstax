package org.hisrc.jstax.grammar;

public interface NotIncluding extends Excluding, Prefix {

	public Ch getElement();

	public Str getExclusion();
}
