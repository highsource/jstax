package org.hisrc.jstax.xml.stream.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.hisrc.jstax.io.impl.ReaderInput;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.hisrc.jstax.xml.XML;
import org.hisrc.jstax.xml.stream.ProductionXMLStreamReader;
import org.junit.Test;

public class StandardTest {

	public static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;

	@Test
	public void testRead100() throws XMLStreamException, IOException {
		read("Document100.zip");
	}

	@Test
	public void testStreamRead100() throws XMLStreamException, IOException {
		streamRead("Document100.zip");
	}

	@Test
	public void testStateStreamRead100() throws XMLStreamException, IOException {
		stateStreamRead("Document100.zip");
	}

	public void read(String resource) throws XMLStreamException, IOException {

		final InputStream inputStream = getClass()
				.getResourceAsStream(resource);

		final ZipInputStream zis = new ZipInputStream(inputStream);

		for (ZipEntry entry = zis.getNextEntry(); entry != null; entry = zis
				.getNextEntry()) {
			System.out.println("Reading [" + entry.getName() + "].");
			final long start = System.currentTimeMillis();
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			while (zis.read(buffer) != -1) {
			}
			final long end = System.currentTimeMillis();
			System.out.println("Elapsed time [" + (end - start) + "].");
		}
	}

	public void streamRead(String resource) throws XMLStreamException,
			IOException {

		final InputStream inputStream = getClass()
				.getResourceAsStream(resource);

		final XMLInputFactory factory = XMLInputFactory.newInstance();
		final ZipInputStream zis = new ZipInputStream(inputStream);

		for (ZipEntry entry = zis.getNextEntry(); entry != null; entry = zis
				.getNextEntry()) {
			System.out.println("Reading [" + entry.getName() + "].");
			final XMLStreamReader streamReader = factory
					.createXMLStreamReader(zis);
			final long start = System.currentTimeMillis();
			while (streamReader.hasNext()) {
				int eventType = streamReader.next();
				// System.out.println("Read event [" + eventType + "].");
			}
			final long end = System.currentTimeMillis();
			System.out.println("Elapsed time [" + (end - start) + "].");
		}
		//
	}

	public void stateStreamRead(String resource) throws XMLStreamException,
			IOException {

		final InputStream inputStream = getClass()
				.getResourceAsStream(resource);

		final ZipInputStream zis = new ZipInputStream(inputStream);

		for (ZipEntry entry = zis.getNextEntry(); entry != null; entry = zis
				.getNextEntry()) {
			final Reader reader = new InputStreamReader(zis, "UTF-8");
			final XMLStreamReader streamReader = new ProductionXMLStreamReader(
					XML.DOCUMENT, new ReaderInput(new BufferedReader(reader,
							DEFAULT_BUFFER_SIZE)),
					ThrowingErrorHandler.INSTANCE);

			final long start = System.currentTimeMillis();
			while (streamReader.hasNext()) {
				int eventType = streamReader.next();
				// System.out.println("Read event [" + eventType + "].");
			}
			final long end = System.currentTimeMillis();
			System.out.println("Elapsed time [" + (end - start) + "].");
		}
	}
	// final XM
}
