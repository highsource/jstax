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

public class XMLDeclTest {

	@Test
	public void testProcessingInstruction() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.XML_DECL, new StringInput("<?xml version=\"1.0\" standalone=\"yes\"?>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_DOCUMENT, streamReader.next());
		// TODO
//		Assert.assertEquals("1.0", streamReader.getVersion());
//		Assert.assertEquals(true, streamReader.standaloneSet());
//		Assert.assertEquals(true, streamReader.isStandalone());
	}
}
