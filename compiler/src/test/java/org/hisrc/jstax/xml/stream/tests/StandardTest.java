package org.hisrc.jstax.xml.stream.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.hisrc.jstax.io.impl.ReaderInput;
import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionXMLStreamReader;
import org.junit.Test;

public class StandardTest {

	@Test
	public void testDocument000() throws XMLStreamException, IOException {
		checkResource("Document000.zip");
	}

	public void checkResource(String resource) throws XMLStreamException,
			IOException {

		final InputStream inputStream = getClass()
				.getResourceAsStream(resource);

		// final XMLInputFactory factory = XMLInputFactory.newInstance();
		final ZipInputStream zis = new ZipInputStream(inputStream);

		// for (ZipEntry entry = zis.getNextEntry(); entry != null;) {
		// System.out.println("Reading [" + entry.getName() + "].");
		// final XMLStreamReader streamReader = factory
		// .createXMLStreamReader(zis);
		// final long start = System.currentTimeMillis();
		// while (streamReader.hasNext()) {
		// int eventType = streamReader.next();
		// // System.out.println("Read event [" + eventType + "].");
		// }
		// final long end = System.currentTimeMillis();
		// System.out.println("Elapsed time [" + (end - start) + "].");
		// }
		//
		for (ZipEntry entry = zis.getNextEntry(); entry != null; entry = zis
				.getNextEntry()) {
			final Reader reader = new InputStreamReader(zis, "UTF-8");
			final XMLStreamReader streamReader = new ProductionXMLStreamReader(
					XML.DOCUMENT, new ReaderInput(reader),
					ThrowingErrorHandler.INSTANCE);

			final long start = System.currentTimeMillis();
			while (streamReader.hasNext()) {
				int eventType = streamReader.next();
				System.out.println("Read event [" + eventType + "].");
			}
			final long end = System.currentTimeMillis();
			System.out.println("Elapsed time [" + (end - start) + "].");
		}
	}
	// final XM
}
