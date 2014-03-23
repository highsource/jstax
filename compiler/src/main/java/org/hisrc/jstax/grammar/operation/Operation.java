package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public interface Operation {

	public void execute(Result result, XMLStreamScanner scanner);

	public Operation join(Operation that);

	public boolean isTrivial();

}
