package org.hisrc.jstax.grammar.production.structure.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.operation.Operation;
import org.hisrc.jstax.grammar.production.Producer;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.structure.Str;

public class StrImpl extends SequenceImpl implements Str {

	private final String name;

	public StrImpl(String name, String content) {
		this(None.INSTANCE, name, content);
	}

	public StrImpl(Operation operation, String name, String content) {
		super(operation, elements(operation, name, Validate.notBlank(content)));
		this.name = Validate.notNull(name);
	}

	public String getIdentifierName() {
		return name;
	}

	@Override
	public List<Ch> getElements() {
		@SuppressWarnings("unchecked")
		final List<Ch> elements = (List<Ch>) super.getElements();
		return elements;
	}

	private static final Char[] elements(Operation operation, String name,
			String str) {
		final char[] chars = str.toCharArray();
		final Char[] elements = new Char[chars.length];
		for (int index = 0; index < chars.length; index++) {
			elements[index] = Producer._char(
					(index == chars.length - 1 ? operation : None.INSTANCE),
					(name == null ? null : name + "_" + index), chars[index]);
		}
		return elements;
	}
}
