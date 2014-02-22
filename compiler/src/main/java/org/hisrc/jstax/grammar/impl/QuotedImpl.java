package org.hisrc.jstax.grammar.impl;

import java.util.List;

import org.hisrc.jstax.grammar.Char;
import org.hisrc.jstax.grammar.Chars;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.Production;
import org.hisrc.jstax.grammar.Quoted;

public class QuotedImpl extends ChoiceImpl implements Quoted {

	private final Production content;
	private final Chars quotes;

	public QuotedImpl(Production content, Chars quotes) {
		super(quoted(content, quotes));
		this.content = content;
		this.quotes = quotes;
	}

	@Override
	public Production getContent() {
		return this.content;
	}

	@Override
	public Chars getQuotes() {
		return quotes;
	}

	private static Production[] quoted(Production content, Chars quotes) {
		final List<Char> chars = quotes.getChars();
		final Production[] productions = new Production[chars.size()];
		int index = 0;
		for (Char _char : chars) {
			productions[index++] = Grammar.surrounded(_char,
					Grammar.zeroOrMore(Grammar.excluding(content, _char)),
					_char);
		}
		return productions;
	}
}
