package org.hisrc.jstax.xml.tests;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import junit.framework.Assert;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionXMLStreamReader;
import org.junit.Test;

public class AttributeTest {

	@Test
	public void testAttributeQuotes() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.ELEMENT, new StringInput("<a a   =    \"bc\"/>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT, streamReader.next());
		Assert.assertEquals(1, streamReader.getAttributeCount());
		Assert.assertEquals("a", streamReader.getAttributeLocalName(0));
		Assert.assertEquals(XMLConstants.NULL_NS_URI,
				streamReader.getAttributeNamespace(0));
		Assert.assertEquals(XMLConstants.DEFAULT_NS_PREFIX,
				streamReader.getAttributePrefix(0));
		Assert.assertEquals("bc", streamReader.getAttributeValue(0));
		Assert.assertEquals("bc",
				streamReader.getAttributeValue(XMLConstants.NULL_NS_URI, "a"));
		Assert.assertEquals(new QName("a"), streamReader.getAttributeName(0));
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
	}

	@Test
	public void testAttributeApos() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.ELEMENT, new StringInput("<a a   =    'b'/>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT, streamReader.next());
		Assert.assertEquals(1, streamReader.getAttributeCount());
		Assert.assertEquals("a", streamReader.getAttributeLocalName(0));
		Assert.assertEquals(XMLConstants.NULL_NS_URI,
				streamReader.getAttributeNamespace(0));
		Assert.assertEquals(XMLConstants.DEFAULT_NS_PREFIX,
				streamReader.getAttributePrefix(0));
		Assert.assertEquals("b", streamReader.getAttributeValue(0));
		Assert.assertEquals("b",
				streamReader.getAttributeValue(XMLConstants.NULL_NS_URI, "a"));
		Assert.assertEquals(new QName("a"), streamReader.getAttributeName(0));
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
	}
}
