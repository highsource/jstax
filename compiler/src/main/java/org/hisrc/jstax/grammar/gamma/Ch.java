package org.hisrc.jstax.grammar.gamma;

import java.util.List;

import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;

public interface Ch extends Str {

	public void read(Input input, Result result, ErrorHandler handler);

	public boolean startsInput(Input input);

	public boolean matches(char _char);

	public String toString();

	public <R> R accept(ChVisitor<R> visitor);

	public List<CharRange> getCharRanges();

	public boolean intersects(Ch that);

	public CharRanges minus(Char that);
}
