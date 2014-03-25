package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class StartDocument extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		result.popChar();
		result.popChar();
		streamWriter.writeStartDocument();
	}

	public static final Operation INSTANCE = new StartDocument();

}
