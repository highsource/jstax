package org.hisrc.jstax.xml.tests;

import javax.xml.stream.XMLStreamConstants;

import junit.framework.Assert;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class EntityRefTest {

	@Test
	public void testEntityRef() {
		ProductionParser streamReader = new ProductionParser(XML.ENTITY_REF,
				new StringInput("&lt;"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.ENTITY_REFERENCE,
				streamReader.next());
		Assert.assertEquals("<", streamReader.getText());
		Assert.assertEquals("lt", streamReader.getLocalName());
	}
}
