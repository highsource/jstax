package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class EmptyStartElementTagSolidus extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		@SuppressWarnings("unused")
		final char ch = result.popChar();
		streamWriter.writeEmptyStartElementTagSolidus();
	}

	public static final Operation INSTANCE = new EmptyStartElementTagSolidus();

}
