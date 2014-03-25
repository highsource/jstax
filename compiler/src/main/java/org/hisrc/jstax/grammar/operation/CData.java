package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.Result.CharsDestination;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class CData extends AbstractOperation {

	@Override
	public void execute(Result result, final XMLStreamScanner scanner) {
		// Pop the last two "]]>"
		result.popChar();
		result.popChar();
		result.popChar();
		result.popChars(new CharsDestination<RuntimeException>() {

			@Override
			public void writeCharacters(char[] text, int start, int len) {
				scanner.writeCDATA(text, start, len);
			}
		});
	}

	public static final Operation INSTANCE = new CData();

}
