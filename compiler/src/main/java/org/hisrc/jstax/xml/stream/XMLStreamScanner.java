package org.hisrc.jstax.xml.stream;

import org.hisrc.jstax.io.Result.CharsDestination;


public interface XMLStreamScanner extends CharsDestination<RuntimeException>{
	public void writeStartElementLocalName(String name);
	public void writeEmptyStartElementTagSolidus();
	public void writeEmptyStartElementTagGT();
	public void writeEndElementLocalName(String name);
	public void writeProcessingInstruction(String target);
	public void writeProcessingInstructionTarget(String target);
	public void writeProcessingInstructionData(String data);
	@Override
	public void writeCharacters(char[] text, int start, int len);
	public void writeHexCharRef(String charRef);
	public void writeDecCharRef(String charRef);
	public void writeComment(String data);
	public void writeStartDocumentVersion(String version);
	public void writeStartDocumentEncoding(String encoding);
	public void writeStartDocumentStandalone(String standalone);
	public void writeEndDocument();
	public void writeEntityRef(String name);
	public void writeAttributeLocalName(String name);
	public void writeAttributeValue(String name);

	// TODO DTD
	public void writeCData(String data);

	// TODO NOTATION_DECLARATION
	// TODO ENITITY_DECLARATION
}
