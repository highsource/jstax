package org.hisrc.jstax.xml.tests;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import junit.framework.Assert;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionXMLStreamReader;
import org.junit.Test;

public class CommentTest {

	@Test
	public void testComment00() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.COMMENT, new StringInput("<!---->"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals("", streamReader.getText());
	}

	@Test
	public void testComment01() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.COMMENT, new StringInput("<!--*-->"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals("*", streamReader.getText());
	}

	@Test
	public void testComment02() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.COMMENT, new StringInput("<!--**-->"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals("**", streamReader.getText());
	}

	@Test
	public void testComment03() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.COMMENT, new StringInput("<!---*-->"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals("-*", streamReader.getText());
	}

	@Test
	public void testComment04() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.COMMENT, new StringInput("<!---**-->"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.COMMENT, streamReader.next());
		Assert.assertEquals("-**", streamReader.getText());
	}
}
