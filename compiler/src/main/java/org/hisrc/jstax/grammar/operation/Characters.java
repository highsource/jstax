package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class Characters extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		result.popChars(streamWriter);
	}

	public static final Operation INSTANCE = new Characters();

}
