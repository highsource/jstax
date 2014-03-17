package org.hisrc.jstax.xml.stream;

public interface Consumer {

	public int getEventType();

	public String getText();
	
	public String getPITarget();
	
	public String getPIData();

	// Comment
	public void writeComment(String data);

	// Processing instruction
	public void writeProcessingInstruction(String target);

	public void writeProcessingInstruction(String target, String data);

	public void writeProcessingInstructionTarget(String target);

	public void writeProcessingInstructionData(String data);

	//
	public void writeCData(String data);
	
	public void flush();

}
