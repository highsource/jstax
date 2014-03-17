package org.hisrc.jstax.xml.tests;

import javax.xml.stream.XMLStreamConstants;

import junit.framework.Assert;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class CDataTest {

	@Test
	public void testProcessingInstruction() {
		ProductionParser streamReader = new ProductionParser(XML.CD_SECT,
				new StringInput("<![CDATA[.]]>"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
		Assert.assertEquals(".", streamReader.getText());
	}

	@Test
	public void testProcessingInstructionBracket() {
		ProductionParser streamReader = new ProductionParser(XML.CD_SECT,
				new StringInput("<![CDATA[]]]>"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
		Assert.assertEquals("]", streamReader.getText());
	}

	@Test
	public void testProcessingInstructionBracketBracket() {
		ProductionParser streamReader = new ProductionParser(XML.CD_SECT,
				new StringInput("<![CDATA[]]]]>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
		Assert.assertEquals("]]", streamReader.getText());
	}

	@Test
	public void testProcessingInstructionBracketAngle() {
		ProductionParser streamReader = new ProductionParser(XML.CD_SECT,
				new StringInput("<![CDATA[]>]]>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CDATA, streamReader.next());
		Assert.assertEquals("]>", streamReader.getText());
	}
}
