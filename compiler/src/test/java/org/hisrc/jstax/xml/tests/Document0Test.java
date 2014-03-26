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

public class Document0Test {

	@Test
	public void testEmptyElement() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.DOCUMENT, new StringInput("<a/>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_DOCUMENT, streamReader.next());
	}
}
