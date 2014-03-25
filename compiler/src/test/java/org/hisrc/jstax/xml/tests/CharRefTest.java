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

public class CharRefTest {

	@Test
	public void testHexCharRef() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.CHAR_REF, new StringInput("&#x0041;"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CHARACTERS, streamReader.next());
		Assert.assertEquals("A", streamReader.getText());
	}

	@Test
	public void testDecCharRef() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.CHAR_REF, new StringInput("&#00065;"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CHARACTERS, streamReader.next());
		Assert.assertEquals("A", streamReader.getText());
	}
}
