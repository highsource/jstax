package org.hisrc.jstax.xml.tests;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.junit.Test;

public class ReferenceTest {

	@Test
	public void testEmptyElement() throws XMLStreamException {

		final XMLInputFactory factory = XMLInputFactory.newInstance();

		final XMLStreamReader streamReader = factory
				.createXMLStreamReader(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
						"<a:b xmlns:a=\'urn:c\' a:d=\'e\' f=\'g\'>&lt;</a:b>"));

		while (streamReader.hasNext()) {
			final int eventType = streamReader.next();
			System.out.println("=================================================");
			System.out.println("Event type: [" + eventType + "].");

			if (eventType == XMLStreamConstants.START_ELEMENT) {
				System.out.println("Event type: [START_ELEMENT].");
				logElementData(streamReader);
				logAttributeData(streamReader);
				logNamespaceData(streamReader);
			}
			if (eventType == XMLStreamConstants.END_ELEMENT) {
				System.out.println("Event type: [END_ELEMENT].");
				logElementData(streamReader);
				logNamespaceData(streamReader);
//				logAttributeData(streamReader);
			}
			if (eventType == XMLStreamConstants.START_DOCUMENT) {
				System.out.println("Event type: [START_DOCUMENT].");
				System.out.println("Encoding:" + streamReader.getEncoding());
				System.out.println("Version:" + streamReader.getVersion());
				System.out.println("Standalone:" + streamReader.isStandalone());
				System.out.println("Standalone set:"
						+ streamReader.standaloneSet());
				System.out.println("Character encoding scheme:"
						+ streamReader.getCharacterEncodingScheme());
			}
			if (eventType == XMLStreamConstants.ATTRIBUTE) {
				System.out.println("Event type: [ATTRIBUTE].");
				logAttributeData(streamReader);
			}
			if (eventType == XMLStreamConstants.NAMESPACE) {
				System.out.println("Event type: [NAMESPACE].");
				logNamespaceData(streamReader);
			}
			if (eventType == XMLStreamConstants.END_DOCUMENT) {
				System.out.println("Event type: [END_DOCUMENT].");
			}
		}

		// TODO Auto-generated method stub

	}

	public void logElementData(final XMLStreamReader streamReader) {
		System.out.println("Name:" + streamReader.getName());
		System.out.println("Local name:" + streamReader.getLocalName());
		System.out.println("Prefix:" + streamReader.getPrefix());
	}

	public void logNamespaceData(final XMLStreamReader streamReader) {
		System.out.println("Namespace URI:" + streamReader.getNamespaceURI());
		System.out.println("Namespace count:"
				+ streamReader.getNamespaceCount());
		for (int index = 0; index < streamReader.getNamespaceCount(); index++) {
			System.out.println("Namespace prefix [" + index + "]:"
					+ streamReader.getNamespacePrefix(index));
			System.out.println("Namespace URI [" + index + "]:"
					+ streamReader.getNamespaceURI(index));
			System.out.println("Namespace count ["
					+ index
					+ "]:"
					+ streamReader.getNamespaceURI(streamReader
							.getNamespacePrefix(index)));

		}
	}

	public void logAttributeData(final XMLStreamReader streamReader) {
		System.out.println("Attribute count:"
				+ streamReader.getAttributeCount());
		for (int index = 0; index < streamReader.getAttributeCount(); index++) {
			System.out.println("Attribute local name [" + index + "]:"
					+ streamReader.getAttributeLocalName(index));
			System.out.println("Attribute name [" + index + "]:"
					+ streamReader.getAttributeName(index));
			System.out.println("Attribute namespace [" + index + "]:"
					+ streamReader.getAttributeNamespace(index));
			System.out.println("Attribute preifx [" + index + "]:"
					+ streamReader.getAttributePrefix(index));
			System.out.println("Attribute type [" + index + "]:"
					+ streamReader.getAttributeType(index));
			System.out.println("Attribute value [" + index + "]:"
					+ streamReader.getAttributeValue(index));
			System.out.println("Attribute value ["
					+ index
					+ "]:"
					+ streamReader.getAttributeValue(
							streamReader.getAttributeNamespace(index),
							streamReader.getAttributeLocalName(index)));

		}
	}
}
