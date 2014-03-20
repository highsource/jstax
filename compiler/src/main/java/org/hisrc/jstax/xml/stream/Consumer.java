package org.hisrc.jstax.xml.stream;

public interface Consumer {

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

	public void writeProcessingInstruction(String target, String data);

	public void writeProcessingInstructionTarget(String target);

	public void writeProcessingInstructionData(String data);

	//
	public void writeCData(String data);

	//
	public void writeEntityRef(String name);

	public void writeHexCharRef(String charRef);

	public void writeDecimalCharRef(String charRef);

	//

	public void writeAttributeLocalName(String name);

	public void writeAttributeValue(String name);

	public void writeAttribute(String localName, String value);

	public void flush();

}
