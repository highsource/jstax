package org.hisrc.jstax.xml.stream;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.Validate;

public class XMLStreamScannerImpl implements XMLStreamScanner {

	private final XMLStreamReaderInput input;

	public XMLStreamScannerImpl(XMLStreamReaderInput input) {
		this.input = Validate.notNull(input);
	}

	private Map<String, String> entityDeclarationsMap = new HashMap<String, String>();
	private String startElementPrefixOrLocalPart;
	private String startElementLocalPart;
	private String endElementPrefixOrLocalPart;
	private String endElementLocalPart;
	private String attributePrefixOrLocalPart;
	private String attributeLocalPart;
	private String version;
	private String encoding;
	private String standalone;
	private String piTarget;

	private Map<QName, String> unresolvedAttributes;
	private Map<String, String> namespaces;
	private Stack<NamespaceContextEx> namespaceContexts = new Stack<NamespaceContextEx>();
	private Stack<QName> elementNames = new Stack<QName>();

	{
		entityDeclarationsMap.put("lt", "<");
		entityDeclarationsMap.put("gt", ">");
		entityDeclarationsMap.put("quot", "\"");
		entityDeclarationsMap.put("apos", "\'");
		entityDeclarationsMap.put("amp", "&");

	}

	@Override
	public void writeStartElementPrefixOrLocalPart(String prefixOrLocalPart) {
		this.startElementPrefixOrLocalPart = prefixOrLocalPart;
		this.unresolvedAttributes = new LinkedHashMap<QName, String>();
		this.namespaces = new LinkedHashMap<String, String>();
	}

	@Override
	public void writeStartElementLocalPart(String localPart) {
		this.startElementLocalPart = localPart;
	}
	
	@Override
	public void writeStartElementTagGT() {
		writeStartElement();
	}

	@Override
	public void writeEmptyStartElementTagSolidus() {
		writeStartElement();
	}

	private void writeStartElement() {
		final String startElementPrefixOrLocalPart = this.startElementPrefixOrLocalPart;
		final String startElementLocalPart = this.startElementLocalPart;
		this.startElementPrefixOrLocalPart = null;
		this.startElementLocalPart = null;
		final Map<String, String> namespaces = this.namespaces;
		this.namespaces = null;
		final Map<QName, String> unresolvedAttributes = this.unresolvedAttributes;
		this.unresolvedAttributes = null;

		final NamespaceContextEx namespaceContext;
		if (namespaceContexts.isEmpty()) {
			namespaceContext = new NamespaceContextEx(namespaces);
		} else {
			namespaceContext = namespaceContexts.peek().spawn(namespaces);
		}
		namespaceContexts.push(namespaceContext);

		final QName name = resolveElementName(namespaceContext,
				startElementPrefixOrLocalPart, startElementLocalPart);
		this.elementNames.push(name);
		final Map<QName, String> attributes = resolveAttributeNames(
				namespaceContext, unresolvedAttributes);
		this.input.writeStartElement(name, attributes, namespaces);
	}

	@Override
	public void writeEmptyStartElementTagGT() {

		final QName name = this.elementNames.pop();
		final NamespaceContextEx namespaceContext = this.namespaceContexts
				.pop();
		final Map<String, String> namespaces = namespaceContext.getNamespaces();
		this.input.writeEndElement(name, namespaces);
	}

	private Map<QName, String> resolveAttributeNames(
			final NamespaceContextEx namespaceContext,
			Map<QName, String> unresolvedAttributes) {
		final Map<QName, String> resolvedAttributes = new LinkedHashMap<QName, String>(
				unresolvedAttributes.size());

		for (Entry<QName, String> unresolvedAttribute : unresolvedAttributes
				.entrySet()) {
			final QName unresolvedAttributeName = unresolvedAttribute.getKey();
			final QName attributeName = resolveAttributeName(namespaceContext,
					unresolvedAttributeName);
			final String attributeValue = unresolvedAttribute.getValue();
			resolvedAttributes.put(attributeName, attributeValue);
		}
		return resolvedAttributes;
	}

	private QName resolveElementName(final NamespaceContextEx namespaceContext,
			final String _prefixOrLocalPart, final String _localPart) {
		final String prefix;
		final String localPart;
		final String namespaceURI;
		// TODO Auto-generated method stub
		if (_localPart == null) {
			prefix = XMLConstants.DEFAULT_NS_PREFIX;
			localPart = _prefixOrLocalPart;

		} else {
			prefix = XMLConstants.DEFAULT_NS_PREFIX;
			localPart = _prefixOrLocalPart;
		}
		namespaceURI = namespaceContext.getNamespaceURI(prefix);
		return new QName(namespaceURI, localPart, prefix);
	}

	private QName resolveAttributeName(
			final NamespaceContextEx namespaceContext,
			final QName unresolvedAttributeName) {
		final String prefix = unresolvedAttributeName.getPrefix();
		final String localPart = unresolvedAttributeName.getLocalPart();

		if (XMLConstants.DEFAULT_NS_PREFIX.equals(prefix)) {
			return unresolvedAttributeName;
		} else {
			return new QName(namespaceContext.getNamespaceURI(prefix),
					localPart, prefix);
		}
	}

	@Override
	public void writeAttributePrefixOrLocalPart(String name) {
		this.attributePrefixOrLocalPart = name;
	}

	@Override
	public void writeAttributeLocalPart(String localPart) {
		this.attributeLocalPart = localPart;
	}

	@Override
	public void writeAttributeValue(String value) {

		final String attributePrefixOrLocalPart = this.attributePrefixOrLocalPart;
		final String attributeLocalPart = this.attributeLocalPart;

		this.attributePrefixOrLocalPart = null;
		this.attributeLocalPart = null;

		final String prefix;
		final String localPart;

		if (attributeLocalPart == null) {
			prefix = XMLConstants.DEFAULT_NS_PREFIX;
			localPart = attributePrefixOrLocalPart;
			if (XMLConstants.XMLNS_ATTRIBUTE.equals(localPart)) {
				// xmlns="..."
				namespaces.put(XMLConstants.DEFAULT_NS_PREFIX, value);
			} else {
				unresolvedAttributes.put(new QName(XMLConstants.NULL_NS_URI,
						localPart, prefix), value);
			}

		} else {
			prefix = XMLConstants.DEFAULT_NS_PREFIX;
			localPart = attributePrefixOrLocalPart;

			if (XMLConstants.XMLNS_ATTRIBUTE.equals(prefix)) {
				// xmlns:x="..."
				namespaces.put(localPart, value);
			} else {
				unresolvedAttributes.put(new QName(XMLConstants.NULL_NS_URI,
						localPart, prefix), value);
			}
		}
	}

	@Override
	public void writeEndElementPrefixOrLocalPart(String prefixOrLocalPart) {
		this.endElementPrefixOrLocalPart = prefixOrLocalPart;
	}

	@Override
	public void writeEndElementLocalPart(String localPart) {
		this.endElementLocalPart = localPart;
	}

	@Override
	public void writeEndElementTagGT() {
		final String endElementPrefixOrLocalPart = this.endElementPrefixOrLocalPart;
		final String endElementLocalPart = this.endElementLocalPart;
		this.endElementPrefixOrLocalPart = null;
		this.endElementLocalPart = null;
		final NamespaceContextEx namespaceContext = this.namespaceContexts
				.pop();
		final QName name = resolveElementName(namespaceContext,
				endElementPrefixOrLocalPart, endElementLocalPart);
		final QName elementName = this.elementNames.pop();

		if (!name.equals(elementName)) {
			// TODO Exception
			throw new RuntimeException();
		} else {
			this.input.writeEndElement(name, namespaceContext.getNamespaces());
		}
	}

	@Override
	public void writeComment(char[] text, int start, int len) {
		this.input.writeComment(text, start, len);
	}

	@Override
	public void writeProcessingInstruction(String target) {
		this.input.writeProcessingInstruction(target);
	}

	@Override
	public void writeProcessingInstructionTarget(String target) {
		this.piTarget = target;
	}

	@Override
	public void writeProcessingInstructionData(String data) {
		final String target = this.piTarget;
		this.piTarget = null;
		if (data != null) {
			this.input.writeProcessingInstruction(target, data);
		} else {
			this.input.writeProcessingInstruction(target);
		}
	}

	@Override
	public void writeStartDocumentVersion(String version) {
		this.version = version;
	}

	@Override
	public void writeStartDocumentEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public void writeStartDocumentStandalone(String standalone) {
		this.standalone = standalone;
	}

	@Override
	public void writeStartDocument() {
		final String version = this.version;
		this.version = null;
		final String encoding = this.encoding;
		this.encoding = null;
		final String standalone = this.standalone;
		this.standalone = null;
		// TODO Check yes/no
		this.input.writeStartDocument(version, encoding,
				standalone == null ? null : !standalone.equals("no"));
	}

	@Override
	public void writeEndDocument() {
		this.input.writeEndDocument();
	}

	@Override
	public void writeCharacters(char[] text, int start, int len) {
		this.input.writeCharacters(text, start, len);
	}

	@Override
	public void writeCDATA(char[] text, int start, int len) {
		this.input.writeCDATA(text, start, len);
	}

	@Override
	public void writeDecCharRef(String charRef) {
		Validate.notNull(charRef);
		// TODO Exception
		char _char = (char) Integer.parseInt(charRef, 10);
		this.input.writeCharacters(new char[] { _char }, 0, 1);
	}

	@Override
	public void writeHexCharRef(String charRef) {
		Validate.notNull(charRef);
		// TODO Exception
		char _char = (char) Integer.parseInt(charRef, 16);
		this.input.writeCharacters(new char[] { _char }, 0, 1);
	}

	@Override
	public void writeEntityRef(String name) {
		Validate.notNull(name);
		final String text = resolveEnitityRef(name);
		char[] characters = text.toCharArray();
		this.input.writeEntityReference(name, characters, 0, characters.length);
	}

	public String resolveEnitityRef(final String name) {
		Validate.notNull(name);
		final String replacementText = this.entityDeclarationsMap.get(name);
		if (replacementText == null) {
			// TODO
			throw new IllegalArgumentException("Entity [" + name
					+ "] is not known in this context.");
		}
		return replacementText;
	}

}
