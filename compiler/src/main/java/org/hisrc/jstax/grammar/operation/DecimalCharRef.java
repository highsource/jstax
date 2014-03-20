package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.Consumer;

public class DecimalCharRef implements Operation {

	@Override
	public void execute(Result result, Consumer streamWriter) {
		// Pop the last ";"
		result.popChar();
		streamWriter.writeDecimalCharRef(result.popString());
	}

	public static final Operation INSTANCE = new DecimalCharRef();

}
