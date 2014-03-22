package org.hisrc.jstax.xml.stream;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;

import org.apache.commons.lang3.Validate;

public class ConsumerImpl implements Consumer {

	private Map<String, String> entityRefMap = new HashMap<String, String>();
	private int eventType = -1;
	private String localName;
	private String text;
	//
	private String piTarget;
	private String piData;
	//
	private String attributePrefix;
	private String attributeLocalName;
	private String attributeValue;

	{
		entityRefMap.put("lt", "<");
		entityRefMap.put("gt", ">");
		entityRefMap.put("quot", "\"");
		entityRefMap.put("apos", "\'");
		entityRefMap.put("amp", "&");
	}

	public void flush() {
		this.eventType = -1;
		this.text = null;
		this.piTarget = null;
		this.piData = null;
		this.localName = null;
		this.attributeLocalName = null;
		this.attributePrefix = null;
		this.attributeValue = null;
	}

	@Override
	public String getLocalName() {
		return localName;
	}

	@Override
	public String getText() {
		return this.text;
	}

	public String getPITarget() {
		return this.piTarget;
	}

	public String getPIData() {
		return this.piData;
	}

	@Override
	public String getAttributeLocalName() {
		return this.attributeLocalName;
	}

	@Override
	public String getAttributePrefix() {
		return this.attributePrefix;
	}

	@Override
	public String getAttributeValue() {
		return this.attributeValue;
	}

	// //////////////////

	@Override
	public void writeComment(String data) {
		this.text = data;
		this.eventType = XMLStreamConstants.COMMENT;
	}

	@Override
	public void writeProcessingInstruction(String target) {
		this.piTarget = target;
		this.eventType = XMLStreamConstants.PROCESSING_INSTRUCTION;
	}

	public void writeProcessingInstruction(String target, String data) {
		this.piTarget = target;
		this.piData = data;
		this.eventType = XMLStreamConstants.PROCESSING_INSTRUCTION;
	}

	private String _piTarget;

	@Override
	public void writeProcessingInstructionTarget(String target) {
		Validate.notNull(target);
		this._piTarget = target;
	}

	@Override
	public void writeProcessingInstructionData(String data) {
		if (this._piTarget == null) {
			throw new IllegalStateException(
					"Processing instruction target must have been set earlier, but it is null.");
		}
		final String target = this._piTarget;
		this._piTarget = null;
		if (data == null) {
			writeProcessingInstruction(target);
		} else {
			writeProcessingInstruction(target, data);
		}
	}

	@Override
	public void writeCData(String data) {
		if (data != null) {
			this.text = data;
			this.eventType = XMLStreamConstants.CDATA;
		}
	}

	@Override
	public int getEventType() {
		return this.eventType;
	}

	@Override
	public void writeEntityRef(String name) {
		Validate.notNull(name);
		this.eventType = XMLStreamConstants.ENTITY_REFERENCE;
		this.localName = name;
		this.text = resolveEnitityRef(name);
	}

	@Override
	public void writeHexCharRef(String charRef) {
		Validate.notNull(charRef);
		// TODO Exception
		char _char = (char) Integer.parseInt(charRef, 16);
		this.eventType = XMLStreamConstants.CHARACTERS;
		this.text = String.valueOf(_char);
	}

	@Override
	public void writeDecCharRef(String charRef) {
		Validate.notNull(charRef);
		// TODO Exception
		char _char = (char) Integer.parseInt(charRef, 10);
		this.eventType = XMLStreamConstants.CHARACTERS;
		this.text = String.valueOf(_char);
	}

	public String resolveEnitityRef(final String name) {
		Validate.notNull(name);
		final String text = this.entityRefMap.get(name);
		if (text == null) {
			// TODO
			throw new IllegalArgumentException("Entity [" + name
					+ "] is not known in this context.");
		}
		return text;
	}

	private String _attributeLocalName;

	@Override
	public void writeAttributeLocalName(String name) {
		Validate.notNull(name);
		this._attributeLocalName = name;
	}

	@Override
	public void writeAttributeValue(String value) {
		Validate.notNull(value);
		if (this._attributeLocalName == null) {
			throw new IllegalStateException(
					"Attribute name must have been set earlier, but it is null.");
		}
		final String localName = this._attributeLocalName;
		this._attributeLocalName = null;
		writeAttribute(localName, value);
	}

	public void writeAttribute(String localName, String value) {
		Validate.notNull(localName);
		Validate.notNull(value);
		this.attributeLocalName = localName;
		this.attributeValue = value;
		this.eventType = XMLStreamConstants.ATTRIBUTE;
	}

	@Override
	public void writeStartElementLocalName(String name) {
		Validate.notNull(name);
		this.localName = name;
		this.eventType = ConsumerConstants.START_ELEMENT_NAME;
	}
	
	@Override
	public void writeEndElementLocalName(String name) {
		Validate.notNull(name);
		this.localName = name;
		this.eventType = ConsumerConstants.START_ELEMENT_NAME;
	}
	
	
	@Override
	public void writeEmptyStartElementTagSolidus() {
		this.eventType = ConsumerConstants.START_ELEMENT;
	}
	@Override
	public void writeEmptyStartElementTagGT(){
		this.eventType = ConsumerConstants.END_ELEMENT;
	}

	@Override
	public void writeStartDocumentVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeStartDocumentEncoding(String encoding) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeStartDocumentStandalone(String standalone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeEndDocument() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeCharacters(char[] text, int start, int len) {
		// TODO Auto-generated method stub
		
	}
	
	

};