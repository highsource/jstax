package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class IgnoreAll extends AbstractOperation {

	@Override
	public void execute(final Result result, XMLStreamScanner scanner) {
		result.clear();
	}

	public static final Operation INSTANCE = new IgnoreAll();

}
