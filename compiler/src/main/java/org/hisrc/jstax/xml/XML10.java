package org.hisrc.jstax.xml;

import static org.hisrc.jstax.grammar.Grammar._char;
import static org.hisrc.jstax.grammar.Grammar.charRange;
import static org.hisrc.jstax.grammar.Grammar.charRanges;
import static org.hisrc.jstax.grammar.Grammar.chars;
import static org.hisrc.jstax.grammar.Grammar.choice;
import static org.hisrc.jstax.grammar.Grammar.delimited;
import static org.hisrc.jstax.grammar.Grammar.negativeChars;
import static org.hisrc.jstax.grammar.Grammar.oneOrMore;
import static org.hisrc.jstax.grammar.Grammar.quoted;
import static org.hisrc.jstax.grammar.Grammar.sequence;
import static org.hisrc.jstax.grammar.Grammar.str;
import static org.hisrc.jstax.grammar.Grammar.surrounded;
import static org.hisrc.jstax.grammar.Grammar.zeroOrMore;

import org.hisrc.jstax.grammar.Char;
import org.hisrc.jstax.grammar.CharConstants;
import org.hisrc.jstax.grammar.CharRange;
import org.hisrc.jstax.grammar.CharRanges;
import org.hisrc.jstax.grammar.Chars;
import org.hisrc.jstax.grammar.Choice;
import org.hisrc.jstax.grammar.Delimited;
import org.hisrc.jstax.grammar.OneOrMore;
import org.hisrc.jstax.grammar.Quoted;
import org.hisrc.jstax.grammar.Sequence;
import org.hisrc.jstax.grammar.Surrounded;

public class XML10 {

	public static final CharRange ANY_CHAR = charRange(
			CharConstants.MIN.getChar(), CharConstants.MAX.getChar());
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
	public static final CharRange DIGITS = charRange('0', '9');
	public static final CharRanges LETTERS = charRanges(charRange('a', 'z'),
			charRange('A', 'Z'));
	public static final CharRanges DIGITS_AND_LETTERS = charRanges(DIGITS,
			LETTERS);

	// [3]
	public static final Chars WHITESPACE_CHAR = chars(
	//
			SPACE_CHAR,
			//
			TAB_CHAR,
			//
			LF_CHAR,
			//
			CR_CHAR);

	// [2] Char ::= #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] |
	// [#x10000-#x10FFFF] /* any Unicode character, excluding the surrogate
	// blocks, FFFE, and FFFF. */
	public static final CharRanges CHAR = charRanges(
	//
			charRange('\u0020', '\uD7FF'),
			//
			WHITESPACE_CHAR,
			//
			charRange('\uE000', '\uFFFD'));

	// [3] S ::= (#x20 | #x9 | #xD | #xA)+
	public static final OneOrMore S = oneOrMore(WHITESPACE_CHAR);

	// [4] NameStartChar ::= ":" | [A-Z] | "_" | [a-z] | [#xC0-#xD6] |
	// [#xD8-#xF6] | [#xF8-#x2FF] | [#x370-#x37D] | [#x37F-#x1FFF] |
	// [#x200C-#x200D] | [#x2070-#x218F] | [#x2C00-#x2FEF] | [#x3001-#xD7FF] |
	// [#xF900-#xFDCF] | [#xFDF0-#xFFFD] | [#x10000-#xEFFFF]
	public static final CharRanges NAME_START_CHAR = charRanges(
			charRange('A', 'Z'), charRange('a', 'z'), _char(':'), _char('_'),
			charRange('\u00C0', '\u00D6'), charRange('\u00D8', '\u00F6'),
			charRange('\u00F8', '\u02FF'), charRange('\u0370', '\u037D'),
			charRange('\u037F', '\u1FFF'), charRange('\u200C', '\u200D'),
			charRange('\u2070', '\u218F'), charRange('\u2C00', '\u2FEF'),
			charRange('\u3001', '\uD7FF'), charRange('\uF900', '\uFDCF'),
			charRange('\uFDF0', '\uFFFD'));

	// [4a] NameChar ::= NameStartChar | "-" | "." | [0-9] | #xB7 |
	// [#x0300-#x036F] | [#x203F-#x2040]
	public static final CharRanges NAME_CHAR = charRanges(NAME_START_CHAR,
			_char('-'), _char('.'), charRange('0', '9'), _char('\u00B7'),
			charRange('\u0300', '\u036F'), charRange('\u203F', '\u2040'));

	// [5] Name ::= NameStartChar (NameChar)*
	// TODO StartedByAndFollowed ?
	// TODO zeroOrMoreChar
	public static final Sequence NAME = sequence(NAME_START_CHAR,
			zeroOrMore(NAME_CHAR));

	// [6] Names ::= Name (#x20 Name)*
	public static final Delimited NAMES = delimited(NAME, SPACE_CHAR);

	// [7] Nmtoken ::= (NameChar)+
	public static final OneOrMore NMTOKEN = oneOrMore(NAME_CHAR);

	// [8] Nmtokens ::= Nmtoken (#x20 Nmtoken)*
	public static final Delimited NMTOKENS = delimited(NMTOKEN, SPACE_CHAR);

	// [66] CharRef ::= '&#' [0-9]+ ';' | '&#x' [0-9a-fA-F]+ ';'
	public static final Choice CHAR_REF = choice(
			surrounded(str("&#"), oneOrMore(DIGITS), SEMICOLON),
			surrounded(str("&#x"), oneOrMore(DIGITS_AND_LETTERS), SEMICOLON));

	// [68] EntityRef ::= '&' Name ';'
	public static final Surrounded ENTITY_REF = surrounded(AMPERSAND, NAME,
			SEMICOLON);

	// [67] Reference ::= EntityRef | CharRef
	public static final Choice REFERENCE = choice(ENTITY_REF, CHAR_REF);

	// [69] PEReference ::= '%' Name ';'
	public static final Surrounded PE_REFERENCE = surrounded(PERCENT_SIGN,
			NAME, SEMICOLON);

	// [9] EntityValue ::= '"' ([^%&"] | PEReference | Reference)* '"' | "'"
	// ([^%&'] | PEReference | Reference)* "'"
	public static final Quoted ENTITY_VALUE = quoted(
			zeroOrMore(choice(negativeChars(PERCENT_SIGN, AMPERSAND),
					PE_REFERENCE, REFERENCE)), QUOTES);

	// [10] AttValue ::= '"' ([^<&"] | Reference)* '"' | "'" ([^<&'] |
	// Reference)* "'"
	public static final Quoted ATT_VALUE = quoted(
			zeroOrMore(choice(negativeChars(LT, AMPERSAND), REFERENCE)), QUOTES);

	// [11] SystemLiteral ::= ('"' [^"]* '"') | ("'" [^']* "'")
	// zeroOrMoreChar
	public static final Quoted SYSTEM_LITERAL = quoted(zeroOrMore(ANY_CHAR),
			QUOTES);

	// [13] PubidChar ::= #x20 | #xD | #xA | [a-zA-Z0-9] | [-'()+,./:=?;!*#@$_%]

	public static final CharRanges PUBID_CHAR = charRanges(SPACE_CHAR, CR_CHAR,
			LF_CHAR, DIGITS_AND_LETTERS, chars("-'()+,./:=?;!*#@$_%"));

	// [12] PubidLiteral ::= '"' PubidChar* '"' | "'" (PubidChar - "'")* "'"

	// TODO zeroOrMoreChar
	public static final Quoted PUBID_LITERAL = quoted(zeroOrMore(PUBID_CHAR),
			QUOTES);
}
