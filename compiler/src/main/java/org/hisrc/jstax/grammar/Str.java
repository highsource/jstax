package org.hisrc.jstax.grammar;

import java.util.List;

import org.hisrc.jstax.io.Input;

public interface Str extends Sequence, Prefix {

	public List<Ch> getElements();

	public boolean matches(Input input);
}
