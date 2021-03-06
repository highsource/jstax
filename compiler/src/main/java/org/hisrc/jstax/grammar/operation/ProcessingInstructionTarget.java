package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class ProcessingInstructionTarget extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		// Pop the last whitespace character
		result.popChar();
		streamWriter.writeProcessingInstructionTarget(result.popString());
	}

	public static final Operation INSTANCE = new ProcessingInstructionTarget();

}
