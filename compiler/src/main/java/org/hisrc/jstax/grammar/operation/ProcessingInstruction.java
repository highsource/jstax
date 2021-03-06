package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class ProcessingInstruction extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		// Pop the last two "?>"
		result.popChar();
		result.popChar();
		streamWriter.writeProcessingInstruction(result.popString());
	}

	public static final Operation INSTANCE = new ProcessingInstruction();

}
