package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamWriter;

public class Ignore implements Operation {

	@Override
	public void execute(final Result result, XMLStreamWriter writer) {
		result.popChar();
	}

	public static final Operation INSTANCE = new Ignore();

}
