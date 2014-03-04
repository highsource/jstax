package org.hisrc.jstax.collections4.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.map.MultiValueMap;

public class MultiValueArrayMap<K, V> extends MultiValueMap<K, V> {

	private static final long serialVersionUID = 1907591175923512558L;
	private static final int DEFAULT_KEYS_CAPACITY = 1024;
	private static final int DEFAULT_VALUES_CAPACITY = 16;

	public MultiValueArrayMap() {
		super(new HashMap<K, List<V>>(DEFAULT_KEYS_CAPACITY),
				new Factory<List<V>>() {
					public List<V> create() {
						return new ArrayList<V>(DEFAULT_VALUES_CAPACITY);
					}
				});
	}
}
