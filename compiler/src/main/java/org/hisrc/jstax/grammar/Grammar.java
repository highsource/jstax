package org.hisrc.jstax.grammar;

import java.util.List;

import org.hisrc.jstax.grammar.impl.CharImpl;
import org.hisrc.jstax.grammar.impl.CharRangeImpl;
import org.hisrc.jstax.grammar.impl.CharRangesImpl;
import org.hisrc.jstax.grammar.impl.CharsImpl;
import org.hisrc.jstax.grammar.impl.ChoiceImpl;
import org.hisrc.jstax.grammar.impl.DelimitedImpl;
import org.hisrc.jstax.grammar.impl.EpsilonImpl;
import org.hisrc.jstax.grammar.impl.ExcludingCharRangesImpl;
import org.hisrc.jstax.grammar.impl.ExcludingImpl;
import org.hisrc.jstax.grammar.impl.NegativeCharsImpl;
import org.hisrc.jstax.grammar.impl.NotIncludingImpl;
import org.hisrc.jstax.grammar.impl.OneOrMoreChImpl;
import org.hisrc.jstax.grammar.impl.OptionalPrefixedChoiceImpl;
import org.hisrc.jstax.grammar.impl.PrefixedChoiceImpl;
import org.hisrc.jstax.grammar.impl.PrefixedSequenceImpl;
import org.hisrc.jstax.grammar.impl.QuotedImpl;
import org.hisrc.jstax.grammar.impl.SequenceImpl;
import org.hisrc.jstax.grammar.impl.StrImpl;
import org.hisrc.jstax.grammar.impl.SurroundedImpl;
import org.hisrc.jstax.grammar.impl.TerminatedImpl;
import org.hisrc.jstax.grammar.impl.ZeroOrMoreImpl;

public class Grammar {

	public static Epsilon epsilon() {
		return new EpsilonImpl();
	}

	public static Sequence sequence(Production... elements) {
		return new SequenceImpl(elements);
	}

	public static PrefixedChoice sequence1(PrefixedChoice first,
			PrefixedChoice... elements) {
		if (elements.length == 0) {
			return first;
		} else {
			final List<? extends Prefix> prefixes = first.getElements();
			final Prefix[] suffixedElements = new Prefix[prefixes.size()];
			int index = 0;
			for (Prefix prefix : first.getElements()) {
				suffixedElements[index++] = sequence(prefix, elements);
			}
			return choice(suffixedElements);
		}
	}

	public static PrefixedSequence sequence(Prefix first,
			Production... elements) {
		return new PrefixedSequenceImpl(first, elements);
	}

	public static PrefixedChoice choice(PrefixedChoice... elements) {
		return new PrefixedChoiceImpl(elements);
	}

	public static Choice choice(Production... elements) {
		return new ChoiceImpl(elements);
	}

	public static OptionalPrefixedChoice zeroOrOne(PrefixedChoice element) {
		return new OptionalPrefixedChoiceImpl(element);
	}

	public static OptionalPrefixedChoice zeroOrMore(Ch element) {
		return zeroOrOne(oneOrMore(element));
	}

	public static ZeroOrMore zeroOrMore(Production element) {
		return new ZeroOrMoreImpl(element);
	}

	public static OneOrMoreCh oneOrMore(Ch element) {
		return new OneOrMoreChImpl(element);
	}

	public static Char _char(char ch) {
		return new CharImpl(ch);
	}

	public static Chars chars(char... chars) {
		return new CharsImpl(chars);
	}

	public static Chars chars(String chars) {
		return new CharsImpl(chars);
	}

	public static Chars chars(Char... chars) {
		return new CharsImpl(chars);
	}

	public static NegativeChars negativeChars(Char... negativeChars) {
		return new NegativeCharsImpl(negativeChars);
	}

	public static NegativeChars negativeChars(char... negativeChars) {
		return new NegativeCharsImpl(negativeChars);
	}

	public static NegativeChars negativeChars(String negativeChars) {
		return new NegativeCharsImpl(negativeChars);
	}

	public static CharRange charRange(char from, char to) {
		return new CharRangeImpl(from, to);
	}

	public static CharRanges charRanges(CharRanges... elements) {
		return new CharRangesImpl(elements);
	}

	public static Delimited delimited(Prefix element, Prefix delimiter) {
		return new DelimitedImpl(element, delimiter);
	}

	public static Excluding excluding(Prefix content, PrefixedChoice exclusion) {
		return new ExcludingImpl(content, exclusion);
	}

	public static NotIncluding notIncluding(Ch element, Str exclusion) {
		return new NotIncludingImpl(element, exclusion);
	}

	public static Excluding excluding(PrefixedChoice content,
			CharRanges exclusion) {
		return new ExcludingCharRangesImpl(content, exclusion);
	}

	public static Terminated terminated(Production content, Str end) {
		return new TerminatedImpl(content, end);
	}

	public static Surrounded surrounded(Str start, Production content, Str end) {
		return new SurroundedImpl(start, content, end);
	}

	public static Str str(String str) {
		return new StrImpl(str);
	}

	public static Quoted quoted(PrefixedChoice content, Chars quotes) {
		return new QuotedImpl(content, quotes);
	}
}
