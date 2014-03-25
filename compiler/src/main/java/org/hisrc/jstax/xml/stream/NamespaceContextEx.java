package org.hisrc.jstax.xml.stream;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.NamespaceContext;

import org.apache.commons.lang3.Validate;

public class NamespaceContextEx implements NamespaceContext {

	private final Map<String, String> declaredNamespaces;
	private final Map<String, String> namespaces;

	public NamespaceContextEx(Map<String, String> namespaces) {
		this(namespaces, namespaces);
	}

	private NamespaceContextEx(Map<String, String> declaredNamespaces,
			Map<String, String> namespaces) {
		Validate.notNull(namespaces);
		this.namespaces = namespaces;
		this.declaredNamespaces = declaredNamespaces;
	}

	public NamespaceContextEx spawn(Map<String, String> namespaces) {
		if (namespaces.isEmpty()) {
			return this;
		} else {
			final Map<String, String> declaredNamespaces = new LinkedHashMap<String, String>();
			declaredNamespaces.putAll(this.declaredNamespaces);
			declaredNamespaces.putAll(namespaces);
			return new NamespaceContextEx(declaredNamespaces, namespaces);
		}
	}

	public Map<String, String> getNamespaces() {
		return namespaces;
	}

	@Override
	public String getNamespaceURI(String prefix) {
		return this.declaredNamespaces.get(prefix);
	}

	@Override
	public String getPrefix(String namespaceURI) {
		Validate.notNull(namespaceURI);
		for (Entry<String, String> entry : this.declaredNamespaces.entrySet()) {
			if (namespaceURI.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	@Override
	public Iterator<String> getPrefixes(String namespaceURI) {
		Validate.notNull(namespaceURI);
		final List<String> prefixes = new LinkedList<String>();
		for (Entry<String, String> entry : this.declaredNamespaces.entrySet()) {
			if (namespaceURI.equals(entry.getValue())) {
				prefixes.add(entry.getKey());
			}
		}
		return prefixes.iterator();
	}

}
