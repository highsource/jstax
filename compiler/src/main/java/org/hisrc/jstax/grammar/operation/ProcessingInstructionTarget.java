package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamWriter;

public class ProcessingInstructionTarget implements Operation {

	@Override
	public void execute(Result result, XMLStreamWriter streamWriter) {
		// Pop the last whitespace character
		result.popChar();
		streamWriter.writeProcessingInstructionTarget(result.popString());
	}

	public static final Operation INSTANCE = new ProcessingInstructionTarget();

}
