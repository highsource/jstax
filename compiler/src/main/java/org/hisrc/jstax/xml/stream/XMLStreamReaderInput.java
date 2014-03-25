package org.hisrc.jstax.xml.stream;

import java.util.Map;

import javax.xml.namespace.QName;

// TODO Exception
public interface XMLStreamReaderInput {

	public void writeStartDocument(String version, String encoding,
			Boolean standalone);

	public void writeEndDocument();

	public void writeStartElement(QName name, Map<QName, String> attributes,
			Map<String, String> namespaces);

	public void writeEndElement(QName name, Map<String, String> namespaces);

	public void writeComment(char[] text, int start, int len);

	public void writeProcessingInstruction(String target);

	public void writeProcessingInstruction(String target, String data);

	public void writeCharacters(char[] text, int start, int len);

	public void writeCDATA(char[] text, int start, int len);

	public void writeEntityReference(String name, char[] text, int start,
			int len);

}
