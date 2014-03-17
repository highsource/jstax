package org.hisrc.jstax.xml.tests;

import javax.xml.stream.XMLStreamConstants;

import junit.framework.Assert;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class CommentTest {

	@Test
	public void testComment00() {
		ProductionParser streamReader = new ProductionParser(XML.COMMENT,
				new StringInput("<!---->"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals(null, streamReader.getText());
	}

	@Test
	public void testComment01() {
		ProductionParser streamReader = new ProductionParser(XML.COMMENT,
				new StringInput("<!--*-->"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals("*", streamReader.getText());
	}

	@Test
	public void testComment02() {
		ProductionParser streamReader = new ProductionParser(XML.COMMENT,
				new StringInput("<!--**-->"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals("**", streamReader.getText());
	}

	@Test
	public void testComment03() {
		ProductionParser streamReader = new ProductionParser(XML.COMMENT,
				new StringInput("<!---*-->"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals("-*", streamReader.getText());
	}

	@Test
	public void testComment04() {
		ProductionParser streamReader = new ProductionParser(XML.COMMENT,
				new StringInput("<!---**-->"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals("-**", streamReader.getText());
	}
}
