package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class EmptyStartElementTagGT extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner scanner) {
		@SuppressWarnings("unused")
		final char ch = result.popChar();
		scanner.writeEmptyStartElementTagGT();
	}

	public static final Operation INSTANCE = new EmptyStartElementTagGT();

}
