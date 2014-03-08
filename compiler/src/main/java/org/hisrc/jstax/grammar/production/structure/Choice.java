package org.hisrc.jstax.grammar.production.structure;

import java.util.List;

import org.hisrc.jstax.grammar.production.Production;

public interface Choice extends Production {

	public List<Production> getOptions();

}
