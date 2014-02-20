package org.hisrc.jstax.grammar;

import java.util.List;

public interface Composite extends Production {
	
	public List<? extends Production> getElements();

}
