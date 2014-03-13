package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamWriter;

public class None implements Operation {

	@Override
	public void execute(final Result result, XMLStreamWriter writer) {
	}

	public static final Operation INSTANCE = new None();

}
