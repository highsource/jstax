package org.hisrc.jstax.grammar.production.structure;

import java.util.List;

import org.hisrc.jstax.grammar.production.Production;

public interface Sequence extends Production {

	public List<? extends Production> getElements();

}
