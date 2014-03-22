package org.hisrc.jstax.xml.stream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public abstract class AbstractUnXMLStreamReaderImpl implements XMLStreamReader {

	@Override
	public Object getProperty(String name) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int next() throws XMLStreamException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void require(int type, String namespaceURI, String localName) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void close() throws XMLStreamException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NamespaceContext getNamespaceContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getEncoding() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasText() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Location getLocation() {
		throw new UnsupportedOperationException();
	}
}
