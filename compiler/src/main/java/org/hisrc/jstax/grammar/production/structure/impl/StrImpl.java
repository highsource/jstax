package org.hisrc.jstax.grammar.production.structure.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.production.Producer;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.structure.Str;

public class StrImpl extends SequenceImpl implements Str {

	public StrImpl(String name, String content) {
		super(name, elements(name, Validate.notBlank(content)));
	}

	@Override
	public List<Ch> getElements() {
		@SuppressWarnings("unchecked")
		final List<Ch> elements = (List<Ch>) super.getElements();
		return elements;
	}

	private static final Char[] elements(String name, String str) {
		final char[] chars = str.toCharArray();
		final Char[] elements = new Char[chars.length];
		for (int index = 0; index < chars.length; index++) {
			elements[index] = Producer._char((name == null ? null : name + "_"
					+ index),

			chars[index]);
		}
		return elements;
	}
}
