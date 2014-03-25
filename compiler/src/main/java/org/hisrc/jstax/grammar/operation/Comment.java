package org.hisrc.jstax.grammar.operation;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.Result.CharsDestination;
import org.hisrc.jstax.xml.stream.XMLStreamScanner;

public class Comment extends AbstractOperation {

	@Override
	public void execute(Result result, final XMLStreamScanner streamWriter) {
		// Pop the last two "--"
		result.popChar();
		result.popChar();
		result.popChars(new CharsDestination<RuntimeException>() {
			@Override
			public void writeCharacters(char[] text, int start, int len)
					throws RuntimeException {
				streamWriter.writeComment(text, start, len);
			}
		});
	}

	public static final Operation INSTANCE = new Comment();

}
