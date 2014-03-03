package org.hisrc.jstax.grammar.gamma;

import java.util.List;

import org.hisrc.jstax.grammar.gamma.impl.CharImpl;
import org.hisrc.jstax.grammar.gamma.impl.CharRangeImpl;
import org.hisrc.jstax.grammar.gamma.impl.CharRangesImpl;
import org.hisrc.jstax.grammar.gamma.impl.CharsImpl;
import org.hisrc.jstax.grammar.gamma.impl.ChoiceImpl;
import org.hisrc.jstax.grammar.gamma.impl.DelimitedImpl;
import org.hisrc.jstax.grammar.gamma.impl.NegativeCharsImpl;
import org.hisrc.jstax.grammar.gamma.impl.SequenceImpl;
import org.hisrc.jstax.grammar.gamma.impl.StrImpl;
import org.hisrc.jstax.grammar.gamma.impl.SurroundedImpl;
import org.hisrc.jstax.grammar.gamma.impl.TerminatedImpl;

public class Producer {

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

	public static CharRange charRange(char from, char to) {
		return new CharRangeImpl(from, to);
	}

	public static CharRanges charRanges(CharRanges... elements) {
		return new CharRangesImpl(elements);
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

	public static Str str(String str) {
		return new StrImpl(str);
	}

	public static Choice choice(Production... options) {
		return new ChoiceImpl(options);
	}

	public static Sequence sequence(Production... elements) {
		return new SequenceImpl(elements);
	}

	public static Delimited delimited(Production element, Production delimiter) {
		return new DelimitedImpl(element, delimiter);
	}

	public static Terminated terminated(Production content, Str end) {
		return new TerminatedImpl(content, end);
	}

	public static Surrounded surrounded(Str start, Production content, Str end) {
		return new SurroundedImpl(start, content, end);
	}

//	// X* - (X* Y X*)
//	public static Production notIncluding(Ch _char, Str string) {
//		return _char.zeroOrMore().butNot(
//				_char.oneOrMore().followedBy(string)
//						.followedBy(_char.oneOrMore()));
//	}

	// '"' X* '"' | "'" X* "'"
	public static Production quoted(Production content, Chars quotes) {
		return quotedSingle(content.zeroOrMore(), quotes);
	}

	// '"' X '"' | "'" X "'"
	public static Production quotedSingle(Production content, Chars quotes) {
		final List<Char> chars = quotes.getChars();
		final Production[] productions = new Production[chars.size()];
		int index = 0;
		for (final Char _char : chars) {
			productions[index++] = surrounded(_char, content, _char);
		}
		return choice(productions);
	}

}
