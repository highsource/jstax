package org.hisrc.jstax.xml.tests;

import javax.xml.stream.XMLStreamConstants;

import junit.framework.Assert;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class CharRefTest {

	@Test
	public void testHexCharRef() {
		ProductionParser streamReader = new ProductionParser(XML.CHAR_REF,
				new StringInput("&#x0041;"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CHARACTERS,
				streamReader.next());
		Assert.assertEquals("A", streamReader.getText());
	}

	@Test
	public void testDecimalCharRef() {
		ProductionParser streamReader = new ProductionParser(XML.CHAR_REF,
				new StringInput("&#00065;"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.CHARACTERS,
				streamReader.next());
		Assert.assertEquals("A", streamReader.getText());
	}
}
