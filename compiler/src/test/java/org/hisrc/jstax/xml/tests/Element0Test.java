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

public class Element0Test {

	@Test
	public void testEmptyElement() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.ELEMENT, new StringInput("<a/>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
	}

	@Test
	public void testElement0() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.ELEMENT, new StringInput("<a></a>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
	}

	@Test
	public void testElement1() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.ELEMENT, new StringInput("<a><b/></a>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
	}

	@Test
	public void testElement2() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.ELEMENT, new StringInput("<a><b></b></a>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
	}

	@Test
	public void testElement3() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.ELEMENT, new StringInput("<a><b><c/></b></a>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
	}

	@Test
	public void testElement4() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.ELEMENT, new StringInput("<site> <regions> <africa> </africa> </regions> </site>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.START_ELEMENT,
				streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
		Assert.assertEquals(XMLStreamConstants.END_ELEMENT, streamReader.next());
	}
}
