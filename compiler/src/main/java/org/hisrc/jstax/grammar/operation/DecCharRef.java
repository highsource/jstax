package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.Consumer;

public class DecCharRef implements Operation {

	@Override
	public void execute(Result result, Consumer streamWriter) {
		// Pop the last ";"
		result.popChar();
		streamWriter.writeDecCharRef(result.popString());
	}

	public static final Operation INSTANCE = new DecCharRef();

}
