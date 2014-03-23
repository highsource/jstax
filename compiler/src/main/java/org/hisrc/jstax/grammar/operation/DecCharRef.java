package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class DecCharRef extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		// Pop the last ";"
		result.popChar();
		streamWriter.writeDecCharRef(result.popString());
	}

	public static final Operation INSTANCE = new DecCharRef();

}
