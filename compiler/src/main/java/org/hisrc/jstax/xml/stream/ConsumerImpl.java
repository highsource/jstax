package org.hisrc.jstax.xml.stream;

import javax.xml.stream.XMLStreamConstants;

import org.apache.commons.lang3.Validate;

public class ConsumerImpl implements Consumer {

	private int eventType = -1;
	private String text;
	private String piTarget;
	private String piData;

	public void flush() {
		this.eventType = -1;
		this.text = null;
		this.piTarget = null;
		this.piData = null;
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
	public void writeComment(String data) {
		this.text = data;
		this.eventType = XMLStreamConstants.COMMENT;
	}

	@Override
	public void writeProcessingInstruction(String target) {
		this.piTarget = target;
		this.eventType = XMLStreamConstants.PROCESSING_INSTRUCTION;
	}

	@Override
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
		Validate.notNull(data);
		if (this._piTarget == null) {
			throw new IllegalStateException(
					"Processing instruction target must have been set earlier, but it is null.");
		}
		final String target = this._piTarget;
		this._piTarget = null;
		writeProcessingInstruction(target, data);
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

};