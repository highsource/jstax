//package org.hisrc.jstax.xml.tests;
//
//import javax.xml.stream.XMLStreamReader;
//
//import junit.framework.Assert;
//
//import org.hisrc.jstax.io.impl.StringInput;
//import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
//import org.hisrc.jstax.xml.XML;
//import org.hisrc.jstax.xml.stream.ProductionXMLStreamReader;
//import org.junit.Test;
//
//public class ElementTest {
//
//	@Test
//	public void testStartTagPart() {
//		XMLStreamReader streamReader = new ProductionXMLStreamReader(
//				XML.START_TAG_PART, new StringInput("<a"),
//				ThrowingErrorHandler.INSTANCE);
//		Assert.assertEquals(ConsumerConstants.START_ELEMENT_NAME,
//				streamReader.next());
//		Assert.assertEquals("a", streamReader.getLocalName());
//	}
//
//	@Test
//	public void testEmptyElement() {
//		XMLStreamReader streamReader = new ProductionXMLStreamReader(
//				XML.ELEMENT, new StringInput("<a/>"),
//				ThrowingErrorHandler.INSTANCE);
//		Assert.assertEquals(ConsumerConstants.START_ELEMENT_NAME,
//				streamReader.next());
//		Assert.assertEquals("a", streamReader.getLocalName());
//		Assert.assertEquals(ConsumerConstants.START_ELEMENT,
//				streamReader.next());
//		Assert.assertEquals("a", streamReader.getLocalName());
//		Assert.assertEquals(ConsumerConstants.END_ELEMENT, streamReader.next());
//		Assert.assertEquals("a", streamReader.getLocalName());
//	}
//
//}
