package org.hisrc.jstax.grammar.production.structure;

import java.util.List;

import org.hisrc.jstax.grammar.production.character.Ch;

public interface Str extends Sequence {

	@Override
	public List<Ch> getElements();

}
