package org.hisrc.jstax.grammar.gamma;

import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public interface Ch extends Str {

	public void read(Input input, Result result, ErrorHandler handler);

	public boolean startsInput(Input input);

	public boolean matches(char _char);

	public String toString();
}
