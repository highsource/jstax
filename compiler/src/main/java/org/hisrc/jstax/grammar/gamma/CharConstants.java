package org.hisrc.jstax.grammar.gamma;

public class CharConstants {

	public static final Char MIN = GammaProducer._char('\u0000');
	public static final Char MAX = GammaProducer._char('\uFFFF');
	public static final CharRange ANY_CHAR = GammaProducer.charRange(
			MIN.getChar(), MAX.getChar());
	public static final Char TAB_CHAR = GammaProducer._char((char) 0x9);
	public static final Char LF_CHAR = GammaProducer._char((char) 0xA);
	public static final Char CR_CHAR = GammaProducer._char((char) 0xD);
	public static final Char SPACE_CHAR = GammaProducer._char((char) 0x20);
	public static final Char APOSTROPHE = GammaProducer._char('\'');
	public static final Char QUOTATION_MARK = GammaProducer._char('\"');
	public static final Chars QUOTES = GammaProducer.chars(APOSTROPHE,
			QUOTATION_MARK);
	public static final Char AMPERSAND = GammaProducer._char('&');
	public static final Char PERCENT_SIGN = GammaProducer._char('%');
	public static final Char SEMICOLON = GammaProducer._char(';');
	public static final Char LT = GammaProducer._char('<');
	public static final Char GT = GammaProducer._char('>');
	public static final CharRange DIGITS = GammaProducer.charRange('0', '9');
	public static final CharRanges LETTERS = GammaProducer.charRanges(
			GammaProducer.charRange('a', 'z'),
			GammaProducer.charRange('A', 'Z'));
	public static final CharRanges DIGITS_AND_LETTERS = GammaProducer
			.charRanges(DIGITS, LETTERS);
	// [3]
	public static final Chars WHITESPACE_CHAR = GammaProducer.chars(SPACE_CHAR,
			TAB_CHAR, LF_CHAR, CR_CHAR);
	public static final Char COLON = GammaProducer._char(':');
}
