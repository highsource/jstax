package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.Consumer;

public class StartDocumentStandalone extends AbstractOperation {

	@Override
	public void execute(Result result, Consumer streamWriter) {
		streamWriter.writeStartDocumentEncoding(result.popString());
	}

	public static final Operation INSTANCE = new StartDocumentStandalone();

}
