package org.hisrc.jstax.grammar.gamma;

import java.util.List;

public interface Sequence extends Production {

	public List<? extends Production> getElements();

}
