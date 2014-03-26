package org.hisrc.jstax.xml.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang3.Validate;

public abstract class XMLStreamReaderImpl extends AbstractUnXMLStreamReaderImpl
		implements XMLStreamReader, XMLStreamReaderInput {

	protected boolean newEvent = false;
	// Initial state
	private int eventType = -1;

	private char[] text;
	private String piTarget;
	private String piData;
	private String version;
	private String encoding;
	private Boolean standalone;
	private String prefix;
	private String localName;
	private String namespaceURI;
	private QName name;
	private int namespaceCount;
	private Map<String, String> namespaces;
	private List<String> namespacePrefixes;
	private List<String> namespaceURIs;
	private int attributeCount;
	private Map<QName, String> attributes;
	private List<QName> attributeNames;
	private List<String> attributePrefixes;
	private List<String> attributeLocalNames;
	private List<String> attributeNamespaces;
	private List<String> attributeValues;

	public void flush() {
		this.text = null;
		this.piTarget = null;
		this.piData = null;
		this.version = null;
		this.encoding = null;
		this.standalone = null;
		this.prefix = null;
		this.localName = null;
		this.namespaceURI = null;
		this.name = null;
		this.namespaceCount = 0;
		this.namespaces = null;
		this.namespacePrefixes = null;
		this.namespaceURIs = null;
		this.attributeCount = 0;
		this.attributes = null;
		this.attributeNames = null;
		this.attributePrefixes = null;
		this.attributeLocalNames = null;
		this.attributeNamespaces = null;
		this.attributeValues = null;
	}

	@Override
	public void writeStartDocument(String version, String encoding,
			Boolean standalone) {
		Validate.notNull(version);
		this.version = version;
		this.encoding = encoding;
		this.standalone = standalone;
		newEvent(START_DOCUMENT);
	}

	@Override
	public void writeEndDocument() {
		newEvent(END_DOCUMENT);
	}

	@Override
	public void writeStartElement(QName name, Map<QName, String> attributes,
			Map<String, String> namespaces) {
		Validate.notNull(name);
		Validate.notNull(attributes);
		Validate.notNull(namespaces);
		writeName(name);
		this.attributes = attributes;
		this.attributeCount = attributes.size();
		this.attributeNames = new ArrayList<QName>(this.attributeCount);
		this.attributePrefixes = new ArrayList<String>(this.attributeCount);
		this.attributeLocalNames = new ArrayList<String>(this.attributeCount);
		this.attributeNamespaces = new ArrayList<String>(this.attributeCount);
		this.attributeValues = new ArrayList<String>(this.attributeCount);
		for (Entry<QName, String> entry : attributes.entrySet()) {
			final QName attributeName = entry.getKey();
			final String attributeValue = entry.getValue();
			this.attributeNames.add(attributeName);
			this.attributePrefixes.add(attributeName.getPrefix());
			this.attributeNamespaces.add(attributeName.getNamespaceURI());
			this.attributeLocalNames.add(attributeName.getLocalPart());
			this.attributeValues.add(attributeValue);
		}

		writeNamespaces(namespaces);
		newEvent(START_ELEMENT);
	}

	@Override
	public void writeEndElement(QName name, Map<String, String> namespaces) {
		Validate.notNull(name);
		Validate.notNull(namespaces);
		writeName(name);
		writeNamespaces(namespaces);
		newEvent(END_ELEMENT);
	}

	@Override
	public void writeComment(char[] text, int start, int len) {
		flush();
		writeText(text, start, len);
		newEvent(COMMENT);
	}

	@Override
	public void writeProcessingInstruction(String target) {
		Validate.notNull(target);
		flush();
		this.piTarget = target;
		this.piData = null;
		newEvent(PROCESSING_INSTRUCTION);
	}

	@Override
	public void writeProcessingInstruction(String target, String data) {
		Validate.notNull(target);
		Validate.notNull(data);
		flush();
		this.piTarget = target;
		this.piData = data;
		newEvent(PROCESSING_INSTRUCTION);
	}

	@Override
	public void writeCharacters(char[] text, int start, int len) {
		flush();
		writeText(text, start, len);
		newEvent(XMLStreamConstants.CHARACTERS);
	}

	@Override
	public void writeCDATA(char[] text, int start, int len) {
		flush();
		writeText(text, start, len);
		newEvent(XMLStreamConstants.CDATA);
	}

	public void writeEntityReference(String name, char[] text, int start,
			int len) {
		flush();
		writeText(text, start, len);
		this.localName = name;
		newEvent(XMLStreamConstants.ENTITY_REFERENCE);
	}

	private void newEvent(int event) {
		this.eventType = event;
		this.newEvent = true;
	}

	private void writeText(char[] text, int start, int len) {
		this.text = new char[len];
		System.arraycopy(text, start, this.text, 0, len);
	}

	private void writeNamespaces(Map<String, String> namespaces) {
		this.namespaces = namespaces;
		this.namespaceCount = namespaces.size();
		this.namespacePrefixes = new ArrayList<String>(this.namespaceCount);
		this.namespaceURIs = new ArrayList<String>(this.namespaceCount);
		for (Entry<String, String> entry : namespaces.entrySet()) {
			this.namespacePrefixes.add(entry.getKey());
			this.namespaceURIs.add(entry.getValue());
		}
	}

	private void writeName(QName name) {
		this.name = name;
		this.localName = name.getLocalPart();
		this.prefix = name.getPrefix();
		this.namespaceURI = name.getNamespaceURI();
	}

	// ////////////////////////////////

	@Override
	public String getText() {
		return String.valueOf(this.text);
	}

	@Override
	public char[] getTextCharacters() {
		return this.text;
	}

	@Override
	public int getTextCharacters(int sourceStart, char[] target,
			int targetStart, int length) throws XMLStreamException {

		final int numberOfCharactersToBeCopied = this.text.length < length ? this.text.length
				: length;
		System.arraycopy(this.text, sourceStart, target, targetStart,
				numberOfCharactersToBeCopied);
		return numberOfCharactersToBeCopied;
	}

	@Override
	public int getTextLength() {
		return this.text.length;
	}

	@Override
	public int getTextStart() {
		return 0;
	}

	@Override
	public String getPIData() {
		return this.piData;
	}

	@Override
	public String getPITarget() {
		return this.piTarget;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	@Override
	public String getCharacterEncodingScheme() {
		return this.encoding;
	}

	public boolean isStandalone() {
		return standalone == null || standalone.booleanValue();
	}

	@Override
	public boolean standaloneSet() {
		return standalone != null;
	}

	@Override
	public QName getName() {
		return this.name;
	}

	@Override
	public String getPrefix() {
		return this.prefix;
	}

	@Override
	public String getNamespaceURI() {
		return this.namespaceURI;
	}

	@Override
	public String getLocalName() {
		return this.localName;
	}

	@Override
	public String getNamespaceURI(String prefix) {
		return this.namespaces.get(prefix);
	}

	@Override
	public int getNamespaceCount() {
		return this.namespaces.size();
	}

	@Override
	public String getNamespacePrefix(int index) {
		return this.namespacePrefixes.get(index);
	}

	@Override
	public String getNamespaceURI(int index) {
		return this.namespaceURIs.get(index);
	}

	@Override
	public int getAttributeCount() {
		return this.attributeCount;
	}

	@Override
	public String getAttributeLocalName(int index) {
		return this.attributeLocalNames.get(index);
	}

	@Override
	public QName getAttributeName(int index) {
		return this.attributeNames.get(index);
	}

	@Override
	public String getAttributeNamespace(int index) {
		return this.attributeNamespaces.get(index);
	}

	@Override
	public String getAttributePrefix(int index) {
		return this.attributePrefixes.get(index);
	}

	@Override
	public String getAttributeType(int index) {
		return "CDATA";
	}

	@Override
	public String getAttributeValue(int index) {
		return this.attributeValues.get(index);
	}

	@Override
	public String getAttributeValue(String namespaceURI, String localName) {
		// TODO namespaceURI = null not checked for equality
		return this.attributes.get(new QName(namespaceURI, localName));
	}

	@Override
	public boolean isAttributeSpecified(int index) {
		return true;
	}

	@Override
	public boolean isStartElement() {
		return this.eventType == START_ELEMENT;
	}

	@Override
	public boolean isEndElement() {
		return this.eventType == END_ELEMENT;
	}

	public boolean isCharacters() {
		return this.eventType == CHARACTERS;
	}

	public boolean isWhiteSpace() {
		// TODO
		if (isCharacters() || (this.eventType == XMLStreamConstants.CDATA)) {
			char[] ch = this.getTextCharacters();
			final int start = this.getTextStart();
			final int end = start + this.getTextLength();
			for (int i = start; i < end; i++) {
				final char ch1 = ch[i];
				if (!(ch1 == ((char) 0x20) || ch1 == ((char) 0x09)
						|| ch1 == ((char) 0x0d) || ch1 == ((char) 0x0a))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean hasName() {
		if (eventType == START_ELEMENT || eventType == END_ELEMENT) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getEventType() {
		return this.eventType;
	}

	public String getElementText() throws XMLStreamException {

		if (getEventType() != XMLStreamConstants.START_ELEMENT) {
			throw new XMLStreamException(
					"parser must be on START_ELEMENT to read next text",
					getLocation());
		}
		int eventType = next();
		StringBuffer content = new StringBuffer();
		while (eventType != XMLStreamConstants.END_ELEMENT) {
			if (eventType == XMLStreamConstants.CHARACTERS
					|| eventType == XMLStreamConstants.CDATA
					|| eventType == XMLStreamConstants.SPACE
					|| eventType == XMLStreamConstants.ENTITY_REFERENCE) {
				content.append(getText());
			} else if (eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
					|| eventType == XMLStreamConstants.COMMENT) {
				// skipping
			} else if (eventType == XMLStreamConstants.END_DOCUMENT) {
				throw new XMLStreamException(
						"unexpected end of document when reading element text content");
			} else if (eventType == XMLStreamConstants.START_ELEMENT) {
				throw new XMLStreamException(
						"elementGetText() function expects text only elment but START_ELEMENT was encountered.",
						getLocation());
			} else {
				throw new XMLStreamException("Unexpected event type "
						+ eventType, getLocation());
			}
			eventType = next();
		}
		return content.toString();
	}

	public int nextTag() throws XMLStreamException {

		int eventType = next();
		while ((eventType == XMLStreamConstants.CHARACTERS && isWhiteSpace()) // skip
																				// whitespace
				|| (eventType == XMLStreamConstants.CDATA && isWhiteSpace())
				// skip whitespace
				|| eventType == XMLStreamConstants.SPACE
				|| eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
				|| eventType == XMLStreamConstants.COMMENT) {
			eventType = next();
		}

		if (eventType != XMLStreamConstants.START_ELEMENT
				&& eventType != XMLStreamConstants.END_ELEMENT) {
			throw new XMLStreamException("found: "
					+ getEventTypeString(eventType) + ", expected "
					+ getEventTypeString(XMLStreamConstants.START_ELEMENT)
					+ " or "
					+ getEventTypeString(XMLStreamConstants.END_ELEMENT),
					getLocation());
		}

		return eventType;
	}

	final static String getEventTypeString(int eventType) {
		switch (eventType) {
		case XMLEvent.START_ELEMENT:
			return "START_ELEMENT";
		case XMLEvent.END_ELEMENT:
			return "END_ELEMENT";
		case XMLEvent.PROCESSING_INSTRUCTION:
			return "PROCESSING_INSTRUCTION";
		case XMLEvent.CHARACTERS:
			return "CHARACTERS";
		case XMLEvent.COMMENT:
			return "COMMENT";
		case XMLEvent.START_DOCUMENT:
			return "START_DOCUMENT";
		case XMLEvent.END_DOCUMENT:
			return "END_DOCUMENT";
		case XMLEvent.ENTITY_REFERENCE:
			return "ENTITY_REFERENCE";
		case XMLEvent.ATTRIBUTE:
			return "ATTRIBUTE";
		case XMLEvent.DTD:
			return "DTD";
		case XMLEvent.CDATA:
			return "CDATA";
		case XMLEvent.SPACE:
			return "SPACE";
		}
		return "UNKNOWN_EVENT_TYPE, " + String.valueOf(eventType);
	}

	public void require(int type, String namespaceURI, String localName)
			throws XMLStreamException {
		if (type != this.eventType)
			throw new XMLStreamException("Event type "
					+ getEventTypeString(type) + " specified did "
					+ "not match with current parser event "
					+ getEventTypeString(this.eventType));
		if (namespaceURI != null && !namespaceURI.equals(getNamespaceURI()))
			throw new XMLStreamException("Namespace URI " + namespaceURI
					+ " specified did not match "
					+ "with current namespace URI");
		if (localName != null && !localName.equals(getLocalName()))
			throw new XMLStreamException("LocalName " + localName
					+ " specified did not match with " + "current local name");
		return;
	}

	@Override
	public int next() throws XMLStreamException {
		if (!hasNext()) {
			throw new NoSuchElementException("Stream contains no more events.");
		}
		while (!newEvent && hasNext()) {
			// TODO infinite loop
			scan();
		}
		if (newEvent) {
			this.newEvent = false;
			return this.eventType;
		} else {
			throw new NoSuchElementException(
					"BUG: Stream should contain more events, but it does not.");
		}
	}

	public abstract boolean hasNext();

	protected abstract void scan();

}
