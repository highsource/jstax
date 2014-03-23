package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class Ignore extends AbstractOperation {

	@Override
	public void execute(final Result result, XMLStreamScanner scanner) {
		result.popChar();
	}

	public static final Operation INSTANCE = new Ignore();

}
