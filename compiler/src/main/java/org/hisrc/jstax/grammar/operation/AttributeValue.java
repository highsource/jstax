package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class AttributeValue extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		// Pop the last quote
		result.popChar();
		streamWriter.writeAttributeValue(result.popString());
	}

	public static final Operation INSTANCE = new AttributeValue();

}
