package org.hisrc.jstax.xml.stream;

public class MyXMLStreamReader implements XMLStreamWriter{

	@Override
	public void writeComment(String data) {
		System.out.println ("COMMENT:" + data);
		// TODO Auto-generated method stub
		
	}

}
