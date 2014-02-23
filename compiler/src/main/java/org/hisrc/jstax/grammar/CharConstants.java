package org.hisrc.jstax.grammar;

import static org.hisrc.jstax.grammar.Grammar._char;
import static org.hisrc.jstax.grammar.Grammar.charRange;
import static org.hisrc.jstax.grammar.Grammar.charRanges;
import static org.hisrc.jstax.grammar.Grammar.chars;
import static org.hisrc.jstax.grammar.Grammar.negativeChars;
import static org.hisrc.jstax.grammar.Grammar.notIncluding;

import org.hisrc.jstax.grammar.impl.CharImpl;
import org.hisrc.jstax.xml.XML10;

public class CharConstants {

	public static final Char MIN = new CharImpl('\u0000');
	public static final Char MAX = new CharImpl('\uFFFF');
	public static final CharRange ANY_CHAR = charRange(MIN.getChar(),
			MAX.getChar());
	public static final Char TAB_CHAR = _char((char) 0x9);
	public static final Char LF_CHAR = _char((char) 0xA);
	public static final Char CR_CHAR = _char((char) 0xD);
	public static final Char SPACE_CHAR = _char((char) 0x20);
	public static final Char APOSTROPHE = _char('\'');
	public static final Char QUOTATION_MARK = _char('\"');
	public static final Chars QUOTES = chars(APOSTROPHE, QUOTATION_MARK);
	public static final Char AMPERSAND = _char('&');
	public static final Char PERCENT_SIGN = _char('%');
	public static final Char SEMICOLON = _char(';');
	public static final Char LT = _char('<');
	public static final Char GT = _char('>');
	public static final CharRange DIGITS = charRange('0', '9');
	public static final CharRanges LETTERS = charRanges(charRange('a', 'z'),
			charRange('A', 'Z'));
	public static final CharRanges DIGITS_AND_LETTERS = charRanges(DIGITS,
			LETTERS);
	// [3]
	public static final Chars WHITESPACE_CHAR = chars(SPACE_CHAR, TAB_CHAR,
			LF_CHAR, CR_CHAR);
	public static final Char COLON = _char(':');
}
