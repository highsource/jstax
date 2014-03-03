package org.hisrc.jstax.grammar.gamma;

public class CharConstants {

	public static final Char MIN = Producer._char('\u0000');
	public static final Char MAX = Producer._char('\uFFFF');
	public static final CharRange ANY_CHAR = Producer.charRange(MIN.getChar(),
			MAX.getChar());
	public static final Char TAB_CHAR = Producer._char((char) 0x9);
	public static final Char LF_CHAR = Producer._char((char) 0xA);
	public static final Char CR_CHAR = Producer._char((char) 0xD);
	public static final Char SPACE_CHAR = Producer._char((char) 0x20);
	public static final Char APOSTROPHE = Producer._char('\'');
	public static final Char QUOTATION_MARK = Producer._char('\"');
	public static final Chars QUOTES = Producer.chars(APOSTROPHE,
			QUOTATION_MARK);
	public static final Char AMPERSAND = Producer._char('&');
	public static final Char PERCENT_SIGN = Producer._char('%');
	public static final Char SEMICOLON = Producer._char(';');
	public static final Char LT = Producer._char('<');
	public static final Char GT = Producer._char('>');
	public static final CharRange DIGITS = Producer.charRange('0', '9');
	public static final CharRanges LETTERS = Producer.charRanges(
			Producer.charRange('a', 'z'), Producer.charRange('A', 'Z'));
	public static final CharRanges DIGITS_AND_LETTERS = Producer.charRanges(
			DIGITS, LETTERS);
	// [3]
	public static final Chars WHITESPACE_CHAR = Producer.chars(SPACE_CHAR,
			TAB_CHAR, LF_CHAR, CR_CHAR);
	public static final Char COLON = Producer._char(':');
}
