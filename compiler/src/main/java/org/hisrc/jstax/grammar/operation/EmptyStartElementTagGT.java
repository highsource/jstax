package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.Consumer;

public class EmptyStartElementTagGT extends AbstractOperation {

	@Override
	public void execute(Result result, Consumer streamWriter) {
		streamWriter.writeEmptyStartElementTagGT();
	}

	public static final Operation INSTANCE = new EmptyStartElementTagGT();

}
