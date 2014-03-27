package org.hisrc.jstax.grammar.production;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.operation.IgnoreChar;
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
import org.hisrc.jstax.grammar.production.structure.impl.SequenceImpl;
import org.hisrc.jstax.grammar.production.structure.impl.StrImpl;

public class Producer {

	public static Production zeroOrMore(Production production) {
		Validate.notNull(production);
		return new ZeroOrMoreImpl(production);
	}

	public static Production zeroOrOne(Production production) {
		Validate.notNull(production);
		return new ZeroOrOneImpl(production);
	}

	public static Production oneOrMore(Operation operation,
			Production production) {
		return new OneOrMoreImpl(operation, production);
	}

	public static Production oneOrMore(Production production) {
		Validate.notNull(production);
		return new OneOrMoreImpl(production);
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

	public static Chars chars(Operation operation, String name, Char... chars) {
		return new CharsImpl(operation, name, chars);
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

	// public static NegativeChars negativeChars(String name,
	// char... negativeChars) {
	// return new NegativeCharsImpl(name, negativeChars);
	// }

	public static NegativeChars negativeChars(String name, String negativeChars) {
		return new NegativeCharsImpl(negativeChars);
	}

	public static Str str(String name, String str) {
		return new StrImpl(name, str);
	}

	public static Str str(Operation operation, String name, String str) {
		return new StrImpl(operation, name, str);
	}

	public static Choice choice(Production... options) {
		return new ChoiceImpl(options);
	}

	public static Sequence sequence(Production... elements) {
		return new SequenceImpl(elements);
	}

	public static Sequence sequence(Operation operation, Production... elements) {
		return new SequenceImpl(operation, elements);
	}

	public static Production delimited(Production element, Production delimiter) {
		return sequence(element, zeroOrMore(sequence(delimiter, element)));
	}

	public static Production quoted(Operation operation, String name,
			Chars quotes, Ch ch, Production... contents) {

		final Production additionalContent;
		if (contents == null || contents.length == 0) {
			additionalContent = null;
		} else if (contents.length == 1) {
			additionalContent = contents[0];
		} else {
			additionalContent = choice(contents);
		}

		final List<Char> chars = quotes.getChars();
		final Production[] productions = new Production[chars.size()];
		int index = 0;
		for (final Char quote : chars) {
			// Theoretically might be null, but practically not
			final Ch mainContent = ch.minus(
					name + "_MAIN_CONTENT_" + quote.getIdentifierName(), quote);
			final Production content = additionalContent == null ? mainContent
					: choice(mainContent, additionalContent);
			productions[index++] = sequence(
					new CharImpl(IgnoreChar.INSTANCE,
							quote.getIdentifierName(), quote.getChar()),
					zeroOrMore(content),
					new CharImpl(operation, quote.getIdentifierName(), quote
							.getChar()));
		}
		return choice(productions);
	}

	// '"' X '"' | "'" X "'"
	public static Production quotedSingle(Chars quotes, Production content) {
		final List<Char> chars = quotes.getChars();
		final Production[] productions = new Production[chars.size()];
		int index = 0;
		for (final Char quote : chars) {
			final Char ignoredQuote = new CharImpl(IgnoreChar.INSTANCE,
					quote.getIdentifierName(), quote.getChar());
			productions[index++] = sequence(ignoredQuote, content, ignoredQuote);
		}
		return choice(productions);
	}

}
