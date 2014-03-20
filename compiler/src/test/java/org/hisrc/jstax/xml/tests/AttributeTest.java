package org.hisrc.jstax.xml.tests;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;

import junit.framework.Assert;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class AttributeTest {

	@Test
	public void testAttributeQuotes() {
		ProductionParser streamReader = new ProductionParser(XML.ATTRIBUTE,
				new StringInput("a   =    \"b\""),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.ATTRIBUTE, streamReader.next());
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
	}

	@Test
	public void testAttributeApos() {
		ProductionParser streamReader = new ProductionParser(XML.ATTRIBUTE,
				new StringInput("a   =    'b'"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.ATTRIBUTE, streamReader.next());
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
	}
}
