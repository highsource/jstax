package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class CharDataError extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner scanner) {
		// TODO Exception
		throw new RuntimeException(
				"Character data must not contain substring \"]]>\".");
	}
	public static final Operation INSTANCE = new CharDataError();

}
