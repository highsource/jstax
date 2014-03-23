package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class StartElementLocalName extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		streamWriter.writeStartElementLocalName(result.popString());
	}

	public static final Operation INSTANCE = new StartElementLocalName();

}
