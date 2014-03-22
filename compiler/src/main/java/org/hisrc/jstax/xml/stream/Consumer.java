package org.hisrc.jstax.xml.stream;

import org.hisrc.jstax.io.Result.CharsDestination;

public interface Consumer extends CharsDestination<RuntimeException> {

	public int getEventType();

	public String getLocalName();

	public String getText();

	public String getPITarget();

	public String getPIData();

	public String getAttributePrefix();

	public String getAttributeLocalName();

	public String getAttributeValue();

	// Comment
	public void writeComment(String data);

	// Processing instruction
	public void writeProcessingInstruction(String target);

	public void writeProcessingInstructionTarget(String target);

	public void writeProcessingInstructionData(String data);

	//
	public void writeCData(String data);

	//
	public void writeEntityRef(String name);

	public void writeHexCharRef(String charRef);

	public void writeDecCharRef(String charRef);

	//

	public void writeAttributeLocalName(String name);

	public void writeAttributeValue(String name);
	//

	public void writeStartElementLocalName(String name);

	public void writeEndElementLocalName(String name);

	public void writeEmptyStartElementTagSolidus();

	public void writeEmptyStartElementTagGT();

	//
	public void writeStartDocumentVersion(String version);

	public void writeStartDocumentEncoding(String encoding);

	public void writeStartDocumentStandalone(String standalone);

	//
	public void writeEndDocument();

	//
	@Override
	public void writeCharacters(char[] text, int start, int len);

	public void flush();

}
