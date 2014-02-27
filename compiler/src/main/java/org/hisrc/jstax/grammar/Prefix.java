package org.hisrc.jstax.grammar;

import org.hisrc.jstax.io.Input;

public interface Prefix extends Production, PrefixedChoice {

	public Ch getFirst();

	public boolean matches(Input input);

}
