package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamWriter;

public class ProcessingInstructionData implements Operation {

	@Override
	public void execute(Result result, XMLStreamWriter streamWriter) {
		// Pop the last two "?>"
		result.popChar();
		result.popChar();
		streamWriter.writeProcessingInstructionData(result.popString());
	}

	public static final Operation INSTANCE = new ProcessingInstructionData();

}
