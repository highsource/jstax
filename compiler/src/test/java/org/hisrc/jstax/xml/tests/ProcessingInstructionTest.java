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

public class ProcessingInstructionTest {

	@Test
	public void testProcessingInstruction() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(XML.PI,
				new StringInput("<?abc?>"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.PROCESSING_INSTRUCTION,
				streamReader.next());
		Assert.assertEquals("abc", streamReader.getPITarget());
		Assert.assertEquals(null, streamReader.getPIData());
	}

	@Test
	public void testProcessingInstructionTargetData0() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(XML.PI,
				new StringInput("<?abc       ??????>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.PROCESSING_INSTRUCTION,
				streamReader.next());
		Assert.assertEquals("abc", streamReader.getPITarget());
		Assert.assertEquals("?????", streamReader.getPIData());
	}

	@Test
	public void testProcessingInstructionTargetData1() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(XML.PI,
				new StringInput("<?abc       def ?>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.PROCESSING_INSTRUCTION,
				streamReader.next());
		Assert.assertEquals("abc", streamReader.getPITarget());
		Assert.assertEquals("def ", streamReader.getPIData());
	}

	@Test
	public void testProcessingInstructionTargetNullData() throws XMLStreamException {
		XMLStreamReader streamReader = new ProductionXMLStreamReader(XML.PI,
				new StringInput("<?abc       ?>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.PROCESSING_INSTRUCTION,
				streamReader.next());
		Assert.assertEquals("abc", streamReader.getPITarget());
		Assert.assertEquals(null, streamReader.getPIData());
	}

}
