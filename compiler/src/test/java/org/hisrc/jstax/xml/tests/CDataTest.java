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

public class CDataTest {

	@Test
	public void testProcessingInstruction() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.CD_SECT, new StringInput("<![CDATA[.]]>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
		Assert.assertEquals(".", streamReader.getText());
	}

	@Test
	public void testProcessingInstructionBracket() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.CD_SECT, new StringInput("<![CDATA[]]]>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
		Assert.assertEquals("]", streamReader.getText());
	}

	@Test
	public void testProcessingInstructionBracketBracket()
			throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.CD_SECT, new StringInput("<![CDATA[]]]]>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
		Assert.assertEquals("]]", streamReader.getText());
	}

	@Test
	public void testProcessingInstructionBracketAngle()
			throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.CD_SECT, new StringInput("<![CDATA[]>]]>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
		Assert.assertEquals("]>", streamReader.getText());
	}
}
