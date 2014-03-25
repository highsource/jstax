package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.Result.CharsDestination;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class Characters extends AbstractOperation {

	@Override
	public void execute(Result result, final XMLStreamScanner streamWriter) {
		result.popChars(new CharsDestination<RuntimeException>() {

			@Override
			public void writeCharacters(char[] text, int start, int len)
					throws RuntimeException {
				streamWriter.writeCharacters(text, start, len);
			}
		});
	}

	public static final Operation INSTANCE = new Characters();

}
