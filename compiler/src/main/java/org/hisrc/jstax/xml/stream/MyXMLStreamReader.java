package org.hisrc.jstax.xml.stream;

public class MyXMLStreamReader implements XMLStreamWriter{

	@Override
	public void writeComment(String data) {
		System.out.println ("COMMENT:" + data);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeProcessingInstruction(String target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeProcessingInstruction(String target, String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeProcessingInstructionTarget(String target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeProcessingInstructionData(String data) {
		// TODO Auto-generated method stub
		
	}

}
