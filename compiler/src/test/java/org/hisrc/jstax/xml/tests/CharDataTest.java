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

public class CharDataTest {

	@Test
	public void testCharData() throws XMLStreamException {
		// check("");
		check("a");
		check("a]");
		check("a]]");
		check("a]]]");
		check("]");
		check("]a");
		check("]a]");
		check("]a]]");
		check("]a]]]");
		check("]]");
		check("]]a");
		check("]]a]");
		check("]]a]]");
		check("]]]");
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.CHAR_DATA, new StringInput("Test"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CHARACTERS, streamReader.next());
		Assert.assertEquals("Test", streamReader.getText());
	}

	// @Test
	@Test(expected = RuntimeException.class)
	public void testCharDataError0() throws XMLStreamException {
		check("]]>");
	}

	// @Test
	@Test(expected = RuntimeException.class)
	public void testCharDataError1() throws XMLStreamException {
		check("]]>a");
	}

	// @Test
	@Test(expected = RuntimeException.class)
	public void testCharDataError2() throws XMLStreamException {
		check("a]]>");
	}

	// @Test
	@Test(expected = RuntimeException.class)
	public void testCharDataError3() throws XMLStreamException {
		check("]]>c");
	}

	public void check(String test) throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(
				XML.CHAR_DATA, new StringInput(test),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CHARACTERS, streamReader.next());
		Assert.assertEquals(test, streamReader.getText());
	}

	// @Test
	// public void testProcessingInstructionBracket() throws XMLStreamException
	// {
	// XMLStreamReader streamReader = new ProductionXMLStreamReader(
	// XML.CHAR_DATA, new StringInput("<![CDATA[]]]>"),
	// ThrowingErrorHandler.INSTANCE);
	// Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
	// Assert.assertEquals("]", streamReader.getText());
	// }
	//
	// @Test
	// public void testProcessingInstructionBracketBracket()
	// throws XMLStreamException {
	// XMLStreamReader streamReader = new ProductionXMLStreamReader(
	// XML.CHAR_DATA, new StringInput("<![CDATA[]]]]>"),
	// ThrowingErrorHandler.INSTANCE);
	// Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
	// Assert.assertEquals("]]", streamReader.getText());
	// }
	//
	// @Test
	// public void testProcessingInstructionBracketAngle()
	// throws XMLStreamException {
	// XMLStreamReader streamReader = new ProductionXMLStreamReader(
	// XML.CD_SECT, new StringInput("<![CDATA[]>]]>"),
	// ThrowingErrorHandler.INSTANCE);
	// Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
	// Assert.assertEquals("]>", streamReader.getText());
	// }
}
