package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.Consumer;

public interface Operation {

	public void execute(Result result, Consumer writer);

	public Operation join(Operation that);

	public boolean isTrivial();

}
