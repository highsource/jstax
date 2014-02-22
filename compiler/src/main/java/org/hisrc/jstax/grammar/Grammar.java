package org.hisrc.jstax.grammar;

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
import org.hisrc.jstax.grammar.impl.OneOrMoreImpl;
import org.hisrc.jstax.grammar.impl.QuotedImpl;
import org.hisrc.jstax.grammar.impl.SequenceImpl;
import org.hisrc.jstax.grammar.impl.StrIgnoreCaseImpl;
import org.hisrc.jstax.grammar.impl.StrImpl;
import org.hisrc.jstax.grammar.impl.SurroundedImpl;
import org.hisrc.jstax.grammar.impl.TerminatedImpl;
import org.hisrc.jstax.grammar.impl.ZeroOrMoreImpl;
import org.hisrc.jstax.grammar.impl.ZeroOrOneImpl;

public class Grammar {

	public static Epsilon epsilon() {
		return new EpsilonImpl();
	}

	public static Sequence sequence(Production... elements) {
		return new SequenceImpl(elements);
	}

	public static Choice choice(Production... elements) {
		return new ChoiceImpl(elements);
	}

	public static ZeroOrOne zeroOrOne(Production element) {
		return new ZeroOrOneImpl(element);
	}

	public static ZeroOrMore zeroOrMore(Production element) {
		return new ZeroOrMoreImpl(element);
	}

	public static OneOrMore oneOrMore(Production element) {
		return new OneOrMoreImpl(element);
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

	public static CharRange charRange(char from, char to) {
		return new CharRangeImpl(from, to);
	}

	public static CharRanges charRanges(CharRanges... elements) {
		return new CharRangesImpl(elements);
	}

	public static Delimited delimited(Production element, Production delimiter) {
		return new DelimitedImpl(element, delimiter);
	}

	public static Excluding excluding(Production content, Production exclusion) {
		return new ExcludingImpl(content, exclusion);
	}

	public static NotIncluding notIncluding(Ch element, Str exclusion) {
		return new NotIncludingImpl(element, exclusion);
	}

	public static Excluding excluding(Production content, CharRanges exclusion) {
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

	public static Str strIgnoreCase(String str) {
		return new StrIgnoreCaseImpl(str);
	}

	public static Quoted quoted(Production content, Chars quotes) {
		return new QuotedImpl(content, quotes);
	}
}
