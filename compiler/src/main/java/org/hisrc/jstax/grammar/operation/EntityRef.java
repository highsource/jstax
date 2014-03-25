package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class EntityRef extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		// Pop the last ";"
		@SuppressWarnings("unused")
		final char popChar = result.popChar();
		streamWriter.writeEntityRef(result.popString());
	}

	public static final Operation INSTANCE = new EntityRef();

}
