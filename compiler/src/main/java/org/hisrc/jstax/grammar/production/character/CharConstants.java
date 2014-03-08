package org.hisrc.jstax.grammar.production.character;

import static org.hisrc.jstax.grammar.production.Producer._char;
import static org.hisrc.jstax.grammar.production.Producer.charRange;
import static org.hisrc.jstax.grammar.production.Producer.charRanges;
import static org.hisrc.jstax.grammar.production.Producer.chars;

public class CharConstants {

	public static final CharRange LATIN_CAPITAL_LETTERS = charRange(
			"LATIN_CAPITAL_LETTERS", 'A', 'Z');
	public static final CharRange LATIN_SMALL_LETTERS = charRange(
			"LATIN_SMALL_LETTERS", 'a', 'z');
	public static final Char MIN = _char("MIN", '\u0000');
	public static final Char MAX = _char("MAX", '\uFFFF');
	public static final CharRange ANY_CHAR = charRange("ANY_CHAR",
			MIN.getChar(), MAX.getChar());
	public static final Char TAB_CHAR = _char("TAB_CHAR", (char) 0x9);
	public static final Char LF_CHAR = _char("LF_CHAR", (char) 0xA);
	public static final Char CR_CHAR = _char("CR_CHAR", (char) 0xD);
	public static final Char SPACE_CHAR = _char("SPACE_CHAR", (char) 0x20);
	public static final Char APOSTROPHE = _char("APOSTROPHE", '\'');
	public static final Char QUOTATION_MARK = _char("QUOTATION_MARK", '\"');
	public static final Chars QUOTES = chars("QUOTES", APOSTROPHE,
			QUOTATION_MARK);
	public static final Char AMPERSAND = _char("AMPERSAND", '&');
	public static final Char PERCENT_SIGN = _char("PERCENT_SIGN", '%');
	public static final Char SEMICOLON = _char("SEMICOLON", ';');
	public static final Char LT = _char("LT", '<');
	public static final Char GT = _char("GT", '>');
	public static final CharRange DIGITS = charRange("DIGITS", '0', '9');
	public static final CharRanges LETTERS = charRanges("LETTERS",
			LATIN_SMALL_LETTERS, LATIN_CAPITAL_LETTERS);
	public static final CharRanges DIGITS_AND_LETTERS = charRanges(
			"DIGITS_AND_LETTERS", DIGITS, LETTERS);
	public static final Char COLON = _char("COLON", ':');
}
