package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.Consumer;

public class StartDocumentVersion extends AbstractOperation {

	@Override
	public void execute(Result result, Consumer streamWriter) {
		streamWriter.writeStartDocumentVersion(result.popString());
	}

	public static final Operation INSTANCE = new StartDocumentVersion();

}
