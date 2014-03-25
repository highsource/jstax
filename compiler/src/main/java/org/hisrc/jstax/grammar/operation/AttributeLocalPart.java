package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class AttributeLocalPart extends AbstractOperation {

	@Override
	public void execute(Result result, XMLStreamScanner streamWriter) {
		streamWriter.writeAttributeLocalPart(result.popString());
	}

	public static final Operation INSTANCE = new AttributeLocalPart();

}
