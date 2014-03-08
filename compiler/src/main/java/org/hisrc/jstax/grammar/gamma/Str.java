package org.hisrc.jstax.grammar.gamma;

import java.util.List;

import org.hisrc.jstax.grammar.character.Ch;

public interface Str extends Sequence {

	@Override
	public List<Ch> getElements();

}
