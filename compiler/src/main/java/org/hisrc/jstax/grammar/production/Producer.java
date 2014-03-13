package org.hisrc.jstax.grammar.production;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.operation.Operation;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.character.CharRange;
import org.hisrc.jstax.grammar.production.character.CharRanges;
import org.hisrc.jstax.grammar.production.character.Chars;
import org.hisrc.jstax.grammar.production.character.NegativeChars;
import org.hisrc.jstax.grammar.production.character.impl.CharImpl;
import org.hisrc.jstax.grammar.production.character.impl.CharRangeImpl;
import org.hisrc.jstax.grammar.production.character.impl.CharRangesImpl;
import org.hisrc.jstax.grammar.production.character.impl.CharsImpl;
import org.hisrc.jstax.grammar.production.character.impl.NegativeCharsImpl;
import org.hisrc.jstax.grammar.production.some.impl.OneOrMoreImpl;
import org.hisrc.jstax.grammar.production.some.impl.ZeroOrMoreImpl;
import org.hisrc.jstax.grammar.production.some.impl.ZeroOrOneImpl;
import org.hisrc.jstax.grammar.production.structure.Choice;
import org.hisrc.jstax.grammar.production.structure.Sequence;
import org.hisrc.jstax.grammar.production.structure.Str;
import org.hisrc.jstax.grammar.production.structure.impl.ChoiceImpl;
import org.hisrc.jstax.grammar.production.structure.impl.NotContainingImpl;
import org.hisrc.jstax.grammar.production.structure.impl.SequenceImpl;
import org.hisrc.jstax.grammar.production.structure.impl.StrImpl;
import org.hisrc.jstax.grammar.production.structure.impl.Terminated1Impl;

public class Producer {

	public static Production zeroOrMore(String name, Production production) {
		Validate.notNull(name);
		Validate.notNull(production);
		return new ZeroOrMoreImpl(name, production);
	}

	public static Production zeroOrOne(String name, Production production) {
		Validate.notNull(name);
		Validate.notNull(production);
		return new ZeroOrOneImpl(name, production);
	}

	public static Production oneOrMore(String name, Production production) {
		Validate.notNull(name);
		Validate.notNull(production);
		return new OneOrMoreImpl(name, production);
	}

	public static Char _char(String name, char ch) {
		return new CharImpl(name, ch);
	}

	public static Char _char(Operation operation, String name, char ch) {
		return new CharImpl(operation, name, ch);
	}

	public static Chars chars(String name, char... chars) {
		return new CharsImpl(name, chars);
	}

	public static Chars chars(String name, String chars) {
		return new CharsImpl(name, chars);
	}

	public static Chars chars(String name, Char... chars) {
		return new CharsImpl(name, chars);
	}

	public static CharRange charRange(String name, char from, char to) {
		return new CharRangeImpl(name, from, to);
	}

	public static CharRanges charRanges(String name, CharRanges... elements) {
		return new CharRangesImpl(name, elements);
	}

	public static NegativeChars negativeChars(String name,
			Char... negativeChars) {
		return new NegativeCharsImpl(name, negativeChars);
	}

	public static NegativeChars negativeChars(String name,
			char... negativeChars) {
		return new NegativeCharsImpl(name, negativeChars);
	}

	public static NegativeChars negativeChars(String name, String negativeChars) {
		return new NegativeCharsImpl(negativeChars);
	}

	public static Str str(String name, String str) {
		return new StrImpl(name, str);
	}

	public static Str str(Operation operation, String name, String str) {
		return new StrImpl(operation, name, str);
	}

	public static Choice choice(String name, Production... options) {
		return new ChoiceImpl(name, options);
	}

	public static Sequence sequence(String name, Production... elements) {
		return new SequenceImpl(name, elements);
	}

	public static Production delimited(String name, Production element,
			Production delimiter) {
		Validate.notNull(name);
		return sequence(
				"name",
				element,
				zeroOrMore(
						name + "_DELIMITED_ELEMENTS",
						sequence(name + "_DELIMITED_ELEMENT", delimiter,
								element)));
	}

	public static Production notContaining(String name, Ch content, Str end) {
		return new NotContainingImpl(name, content, end);
	}

	public static Production quoted(String name, Chars quotes, Ch ch,
			Production... contents) {

		final Production additionalContent;
		if (contents == null || contents.length == 0) {
			additionalContent = null;
		} else if (contents.length == 1) {
			additionalContent = contents[0];
		} else {
			additionalContent = Producer.choice(name + "_ADDITIONAL_CONTENT",
					contents);
		}

		final List<Char> chars = quotes.getChars();
		final Production[] productions = new Production[chars.size()];
		int index = 0;
		for (final Char quote : chars) {
			// Theoretically might be null, but practically not
			final Ch mainContent = ch.minus(
					name + "_MAIN_CONTENT_" + quote.getIdentifierName(), quote);
			final Production content = additionalContent == null ? mainContent
					: choice(name + "_CONTENT_" + quote.getIdentifierName(),
							mainContent, additionalContent);
			productions[index++] = sequence(
					name + "_CONTENT_" + quote.getIdentifierName() + "_QUOTED",
					quote, content, quote);
		}
		return choice(name, productions);
	}

	// '"' X '"' | "'" X "'"
	public static Production quotedSingle(String name, Chars quotes,
			Production content) {
		final List<Char> chars = quotes.getChars();
		final Production[] productions = new Production[chars.size()];
		int index = 0;
		for (final Char quote : chars) {
			productions[index++] = sequence(
					name + "_QUOTED_" + quote.getIdentifierName(), quote,
					content, quote);
		}
		return choice(name, productions);
	}

	public static Production terminated(String name, Ch _char, Char... terminator) {
		Validate.notNull(name);
		Validate.notNull(_char);
		Validate.notNull(terminator);
		return new Terminated1Impl(name, _char, terminator);
	}
}
