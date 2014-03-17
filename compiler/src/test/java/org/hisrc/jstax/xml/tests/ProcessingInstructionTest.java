package org.hisrc.jstax.xml.tests;

import javax.xml.stream.XMLStreamConstants;

import junit.framework.Assert;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class ProcessingInstructionTest {

	@Test
	public void testProcessingInstruction() {
		ProductionParser streamReader = new ProductionParser(XML.PI,
				new StringInput("<?abc?>"), ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals(XMLStreamConstants.PROCESSING_INSTRUCTION,
				streamReader.next());
		Assert.assertEquals("abc", streamReader.getPITarget());
		Assert.assertEquals(null, streamReader.getPITarget());
	}

	@Test
	public void testProcessingInstructionTargetData0() {
		ProductionParser streamReader = new ProductionParser(XML.PI,
				new StringInput("<?abc       ??????>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals("abc", streamReader.getPITarget());
		Assert.assertEquals("?????", streamReader.getPITarget());
	}

	@Test
	public void testProcessingInstructionTargetData1() {
		ProductionParser streamReader = new ProductionParser(XML.PI,
				new StringInput("<?abc       def ?>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals("abc", streamReader.getPITarget());
		Assert.assertEquals("def ", streamReader.getPITarget());
	}

	@Test
	public void testProcessingInstructionTargetNullData() {
		ProductionParser streamReader = new ProductionParser(XML.PI,
				new StringInput("<?abc       ?>"),
				ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals("abc", streamReader.getPITarget());
		Assert.assertEquals(null, streamReader.getPITarget());
	}

}
