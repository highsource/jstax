package org.hisrc.jstax.xml.tests;

import junit.framework.Assert;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ConsumerConstants;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class ElementTest {

	@Test
	public void testStartTagPart() {
		ProductionParser streamReader = new ProductionParser(XML.START_TAG_PART,
				new StringInput("<a"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(ConsumerConstants.START_ELEMENT_NAME, streamReader.next());
		Assert.assertEquals("a", streamReader.getLocalName());
	}
	
	@Test
	public void testEmptyElement() {
		ProductionParser streamReader = new ProductionParser(XML.ELEMENT,
				new StringInput("<a/>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(ConsumerConstants.START_ELEMENT_NAME, streamReader.next());
		Assert.assertEquals("a", streamReader.getLocalName());
		Assert.assertEquals(ConsumerConstants.START_ELEMENT, streamReader.next());
		Assert.assertEquals("a", streamReader.getLocalName());
		Assert.assertEquals(ConsumerConstants.END_ELEMENT, streamReader.next());
		Assert.assertEquals("a", streamReader.getLocalName());
	}
	
}
