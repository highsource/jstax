package org.hisrc.jstax.xml.tests;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class CommentTest {

	@Test
	public void testComment00() {
		ProductionParser parser = new ProductionParser(XML.COMMENT,
				new StringInput("<!---->"), ThrowingErrorHandler.INSTANCE);

		parser.parse();
	}

	@Test
	public void testComment01() {
		ProductionParser parser = new ProductionParser(XML.COMMENT,
				new StringInput("<!--*-->"), ThrowingErrorHandler.INSTANCE);

		parser.parse();
	}

	@Test
	public void testComment02() {
		ProductionParser parser = new ProductionParser(XML.COMMENT,
				new StringInput("<!--**-->"), ThrowingErrorHandler.INSTANCE);

		parser.parse();
	}

	@Test
	public void testComment03() {
		ProductionParser parser = new ProductionParser(XML.COMMENT,
				new StringInput("<!---*-->"), ThrowingErrorHandler.INSTANCE);

		parser.parse();
	}

	@Test
	public void testComment04() {
		ProductionParser parser = new ProductionParser(XML.COMMENT,
				new StringInput("<!---**-->"), ThrowingErrorHandler.INSTANCE);

		parser.parse();
	}
}
