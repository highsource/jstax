package org.hisrc.jstax.xml.tests;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class ProcessingInstructionTest {

	@Test
	public void testProcessingInstruction() {
		ProductionParser parser = new ProductionParser(XML.PI, new StringInput(
				"<?abc?>"), ThrowingErrorHandler.INSTANCE);

		parser.parse();
	}

	@Test
	public void testProcessingInstructionTargetData0() {
		ProductionParser parser = new ProductionParser(XML.PI, new StringInput(
				"<?abc       ??????>"), ThrowingErrorHandler.INSTANCE);

		parser.parse();
	}
	
	@Test
	public void testProcessingInstructionTargetData1() {
		ProductionParser parser = new ProductionParser(XML.PI, new StringInput(
				"<?abc       def ?>"), ThrowingErrorHandler.INSTANCE);

		parser.parse();
	}
	
	@Test
	public void testProcessingInstructionTargetNullData() {
		ProductionParser parser = new ProductionParser(XML.PI, new StringInput(
				"<?abc       ?>"), ThrowingErrorHandler.INSTANCE);

		parser.parse();
	}

}
