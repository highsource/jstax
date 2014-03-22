package org.hisrc.jstax.xml.stream;

import java.util.Map;

import javax.xml.namespace.QName;

import org.hisrc.jstax.io.Result.CharsDestination;

// TODO Exception
public interface XMLStreamReaderInput extends
		CharsDestination<RuntimeException> {

	public void writeStartDocument(String version, String encoding,
			Boolean standalone);

	public void writeEndDocument();

	public void writeStartElement(QName name, Map<QName, String> attributes,
			Map<String, String> namespaces);

	public void writeEndElement(QName name, Map<String, String> namespaces);

	public void writeComment(String comment);

	public void writeProcessingInstruction(String target);

	public void writeProcessingInstruction(String target, String data);

	@Override
	public void writeCharacters(char[] text, int start, int len);

}
