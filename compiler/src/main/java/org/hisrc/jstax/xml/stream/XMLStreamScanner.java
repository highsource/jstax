package org.hisrc.jstax.xml.stream;

public interface XMLStreamScanner {
	public void writeStartElementPrefixOrLocalPart(String name);

	public void writeStartElementLocalPart(String name);

	public void writeStartElementTagGT();

	public void writeEmptyStartElementTagSolidus();

	public void writeEmptyStartElementTagGT();

	public void writeEndElementPrefixOrLocalPart(String name);

	public void writeEndElementLocalPart(String name);

	public void writeEndElementTagGT();

	public void writeProcessingInstruction(String target);

	public void writeProcessingInstructionTarget(String target);

	public void writeProcessingInstructionData(String data);

	public void writeCharacters(char[] text, int start, int len);

	public void writeCDATA(char[] text, int start, int len);

	public void writeHexCharRef(String charRef);

	public void writeDecCharRef(String charRef);

	public void writeComment(char[] text, int start, int len);

	public void writeStartDocumentVersion(String version);

	public void writeStartDocumentEncoding(String encoding);

	public void writeStartDocumentStandalone(String standalone);

	public void writeStartDocument();

	public void writeEndDocument();

	public void writeEntityRef(String name);

	public void writeAttributePrefixOrLocalPart(String name);

	public void writeAttributeLocalPart(String name);

	public void writeAttributeValue(String value);

	// public void writeCData(char[] text, int start, int len);

	// TODO DTD
	// TODO NOTATION_DECLARATION
	// TODO ENITITY_DECLARATION
}
