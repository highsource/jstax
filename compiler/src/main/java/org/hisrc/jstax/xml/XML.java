package org.hisrc.jstax.xml;

import static org.hisrc.jstax.grammar.Grammar._char;
import static org.hisrc.jstax.grammar.Grammar.charRange;
import static org.hisrc.jstax.grammar.Grammar.charRanges;
import static org.hisrc.jstax.grammar.Grammar.oneOrMore;
import static org.hisrc.jstax.grammar.Grammar.zeroOrMore;

import org.hisrc.jstax.grammar.CharConstants;
import org.hisrc.jstax.grammar.CharRanges;
import org.hisrc.jstax.grammar.OneOrMoreCh;
import org.hisrc.jstax.grammar.ZeroOrMoreCh;

public class XML {

	// [2] Char ::= #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] |
	// [#x10000-#x10FFFF] /* any Unicode character, excluding the surrogate
	// blocks, FFFE, and FFFF. */
	public static final CharRanges CHAR = charRanges(
			charRange('\u0020', '\uD7FF'), CharConstants.WHITESPACE_CHAR,
			charRange('\uE000', '\uFFFD'));
	// [3] S ::= (#x20 | #x9 | #xD | #xA)+
	public static final OneOrMoreCh S = oneOrMore(CharConstants.WHITESPACE_CHAR);
	public static final ZeroOrMoreCh POSSIBLY_S = zeroOrMore(CharConstants.WHITESPACE_CHAR);
	// [4] NameStartChar ::= ":" | [A-Z] | "_" | [a-z] | [#xC0-#xD6] |
	// [#xD8-#xF6] | [#xF8-#x2FF] | [#x370-#x37D] | [#x37F-#x1FFF] |
	// [#x200C-#x200D] | [#x2070-#x218F] | [#x2C00-#x2FEF] | [#x3001-#xD7FF] |
	// [#xF900-#xFDCF] | [#xFDF0-#xFFFD] | [#x10000-#xEFFFF]
	public static final CharRanges NAME_START_CHAR = charRanges(
			charRange('A', 'Z'), charRange('a', 'z'), CharConstants.COLON, _char('_'),
			charRange('\u00C0', '\u00D6'), charRange('\u00D8', '\u00F6'),
			charRange('\u00F8', '\u02FF'), charRange('\u0370', '\u037D'),
			charRange('\u037F', '\u1FFF'), charRange('\u200C', '\u200D'),
			charRange('\u2070', '\u218F'), charRange('\u2C00', '\u2FEF'),
			charRange('\u3001', '\uD7FF'), charRange('\uF900', '\uFDCF'),
			charRange('\uFDF0', '\uFFFD'));
	// [4a] NameChar ::= NameStartChar | "-" | "." | [0-9] | #xB7 |
	// [#x0300-#x036F] | [#x203F-#x2040]
	public static final CharRanges NAME_CHAR = charRanges(NAME_START_CHAR,
			_char('-'), _char('.'), CharConstants.DIGITS, _char('\u00B7'),
			charRange('\u0300', '\u036F'), charRange('\u203F', '\u2040'));

}
