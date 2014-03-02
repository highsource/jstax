package org.hisrc.jstax.grammar.gamma.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Ch;
import org.hisrc.jstax.grammar.gamma.Char;
import org.hisrc.jstax.grammar.gamma.GammaProducer;
import org.hisrc.jstax.grammar.gamma.Str;

public class StrImpl extends SequenceImpl implements Str {

	public StrImpl(String content) {
		super(elements(content));
	}

	@Override
	public List<Ch> getElements() {
		return (List<Ch>) super.getElements();
	}

	private static final Char[] elements(String str) {
		Validate.notBlank(str);
		final char[] chars = str.toCharArray();
		final Char[] elements = new Char[chars.length];
		for (int index = 0; index < chars.length; index++) {
			elements[index] = GammaProducer._char(chars[index]);
		}
		return elements;
	}
}
