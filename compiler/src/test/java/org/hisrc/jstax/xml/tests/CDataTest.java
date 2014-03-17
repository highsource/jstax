package org.hisrc.jstax.xml.tests;

import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionParser;
import org.junit.Test;

public class CDataTest {

	@Test
	public void testProcessingInstruction() {
		ProductionParser parser = new ProductionParser(XML.CD_SECT, new StringInput(
				"<![CDATA[.]]>"), ThrowingErrorHandler.INSTANCE);
		parser.parse();
	}

	@Test
	public void testProcessingInstructionBracket() {
		ProductionParser parser = new ProductionParser(XML.CD_SECT, new StringInput(
				"<![CDATA[]]]>"), ThrowingErrorHandler.INSTANCE);
		parser.parse();
	}
	@Test
	public void testProcessingInstructionBracketBracket() {
		ProductionParser parser = new ProductionParser(XML.CD_SECT, new StringInput(
				"<![CDATA[]]]]>"), ThrowingErrorHandler.INSTANCE);
		parser.parse();
	}
	@Test
	public void testProcessingInstructionBracketAngle() {
		ProductionParser parser = new ProductionParser(XML.CD_SECT, new StringInput(
				"<![CDATA[]>]]>"), ThrowingErrorHandler.INSTANCE);
		parser.parse();
	}
}
