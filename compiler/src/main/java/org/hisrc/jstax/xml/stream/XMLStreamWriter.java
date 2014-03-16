package org.hisrc.jstax.xml.stream;

public interface XMLStreamWriter {

	// Comment
	public void writeComment(String data);

	// Processing instruction
	public void writeProcessingInstruction(String target);

	public void writeProcessingInstruction(String target, String data);

	public void writeProcessingInstructionTarget(String target);

	public void writeProcessingInstructionData(String data);
	
	//
	public void writeCData(String data);

}
