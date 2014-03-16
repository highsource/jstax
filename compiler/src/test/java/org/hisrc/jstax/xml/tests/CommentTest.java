package org.hisrc.jstax.xml.tests;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class CommentTest {

	@Test
	public void test() {
		ProductionParser parser = new ProductionParser(XML.COMMENT, new StringInput("<!--a-->"),
				ThrowingErrorHandler.INSTANCE);
		
		parser.parse();
	}

}
