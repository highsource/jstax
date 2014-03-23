package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class None extends AbstractOperation {

	@Override
	public boolean isTrivial() {
		return true;
	}

	@Override
	public void execute(final Result result, XMLStreamScanner scanner) {
	}

	public static final Operation INSTANCE = new None();

}
