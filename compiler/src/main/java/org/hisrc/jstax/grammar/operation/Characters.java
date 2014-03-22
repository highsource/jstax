package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.Consumer;

public class Characters extends AbstractOperation {

	@Override
	public void execute(Result result, Consumer streamWriter) {
		result.popChars(streamWriter);
	}

	public static final Operation INSTANCE = new Characters();

}
