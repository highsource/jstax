package org.hisrc.jstax.xml.stream;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

public class XMLStreamScannerImpl implements XMLStreamScanner {

	private final XMLStreamReaderInput input;

	public XMLStreamScannerImpl(XMLStreamReaderInput input) {
		this.input = Validate.notNull(input);
	}
	
	private String elementLocalName;
	private Map<String, String> attributes;
	
	@Override
	public void writeStartElementLocalName(String name) {
		this.elementLocalName = name;
		this.attributes = new LinkedHashMap<String, String>();
	}
	
	@Override
	public void writeEndElementLocalName(String name) {
		// TODO check names match
		
		// TODO Auto-generated method stub
//		this.input.writeEndElement(name, namespaces);
		
	}
	
//	@Override
//	public void writeEmptyStartElementTagSolidus() {
//		// TODO process attributes;
//		// TODO Auto-generated method stub
//		
//	}
	
	

	@Override
	public void writeComment(String data) {
		this.input.writeComment(data);
	}

	@Override
	public void writeProcessingInstruction(String target) {
		this.input.writeProcessingInstruction(target);
	}

	private String piTarget;

	@Override
	public void writeProcessingInstructionTarget(String target) {
		this.piTarget = target;
	}

	@Override
	public void writeProcessingInstructionData(String data) {
		final String target = this.piTarget;
		this.piTarget = null;
		this.input.writeProcessingInstruction(target, data);
	}

}
