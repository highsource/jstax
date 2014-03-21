package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.Consumer;

public class EntityRef extends AbstractOperation {

	@Override
	public void execute(Result result, Consumer streamWriter) {
		// Pop the last ";"
		result.popChar();
		streamWriter.writeEntityRef(result.popString());
	}

	public static final Operation INSTANCE = new EntityRef();

}
