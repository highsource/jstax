package org.hisrc.jstax.xml;

import static org.hisrc.jstax.grammar.gamma.Producer._char;
import static org.hisrc.jstax.grammar.gamma.Producer.choice;
import static org.hisrc.jstax.grammar.gamma.Producer.delimited;
import static org.hisrc.jstax.grammar.gamma.Producer.negativeChars;
import static org.hisrc.jstax.grammar.gamma.Producer.quoted;
import static org.hisrc.jstax.grammar.gamma.Producer.quotedSingle;
import static org.hisrc.jstax.grammar.gamma.Producer.sequence;
import static org.hisrc.jstax.grammar.gamma.Producer.str;
import static org.hisrc.jstax.grammar.gamma.Producer.surrounded;
import static org.hisrc.jstax.grammar.gamma.Producer.terminated;

import org.hisrc.jstax.grammar.gamma.CharConstants;
import org.hisrc.jstax.grammar.gamma.CharRanges;
import org.hisrc.jstax.grammar.gamma.Choice;
import org.hisrc.jstax.grammar.gamma.Delimited;
import org.hisrc.jstax.grammar.gamma.Producer;
import org.hisrc.jstax.grammar.gamma.Production;
import org.hisrc.jstax.grammar.gamma.Str;
import org.hisrc.jstax.grammar.gamma.Surrounded;

public class XML {

	// [2] Char ::= #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] |
	// [#x10000-#x10FFFF] /* any Unicode character, excluding the surrogate
	// blocks, FFFE, and FFFF. */
	public static final CharRanges CHAR = Producer.charRanges(
			Producer.charRange('\u0021', '\uD7FF'),
			CharConstants.WHITESPACE_CHAR,
			Producer.charRange('\uE000', '\uFFFD'));

	// [3] S ::= (#x20 | #x9 | #xD | #xA)+
	public static final Production S = CharConstants.WHITESPACE_CHAR
			.oneOrMore();
	public static final Production POSSIBLY_S = CharConstants.WHITESPACE_CHAR
			.zeroOrMore();

	// [4] NameStartChar ::= ":" | [A-Z] | "_" | [a-z] | [#xC0-#xD6] |
	// [#xD8-#xF6] | [#xF8-#x2FF] | [#x370-#x37D] | [#x37F-#x1FFF] |
	// [#x200C-#x200D] | [#x2070-#x218F] | [#x2C00-#x2FEF] | [#x3001-#xD7FF] |
	// [#xF900-#xFDCF] | [#xFDF0-#xFFFD] | [#x10000-#xEFFFF]
	public static final CharRanges NAME_START_CHAR = Producer.charRanges(
			Producer.charRange('A', 'Z'), Producer.charRange('a', 'z'),
			CharConstants.COLON, Producer._char('_'),
			Producer.charRange('\u00C0', '\u00D6'),
			Producer.charRange('\u00D8', '\u00F6'),
			Producer.charRange('\u00F8', '\u02FF'),
			Producer.charRange('\u0370', '\u037D'),
			Producer.charRange('\u037F', '\u1FFF'),
			Producer.charRange('\u200C', '\u200D'),
			Producer.charRange('\u2070', '\u218F'),
			Producer.charRange('\u2C00', '\u2FEF'),
			Producer.charRange('\u3001', '\uD7FF'),
			Producer.charRange('\uF900', '\uFDCF'),
			Producer.charRange('\uFDF0', '\uFFFD'));

	// [4a] NameChar ::= NameStartChar | "-" | "." | [0-9] | #xB7 |
	// [#x0300-#x036F] | [#x203F-#x2040]
	public static final CharRanges NAME_CHAR = Producer.charRanges(
			NAME_START_CHAR, Producer._char('-'), Producer._char('.'),
			CharConstants.DIGITS, Producer._char('\u00B7'),
			Producer.charRange('\u0300', '\u036F'),
			Producer.charRange('\u203F', '\u2040'));

	// [5] Name ::= NameStartChar (NameChar)*
	public static final Production NAME = NAME_START_CHAR.followedBy(NAME_CHAR
			.zeroOrMore());

	// [6] Names ::= Name (#x20 Name)*
	public static final Delimited NAMES = delimited(NAME,
			CharConstants.SPACE_CHAR);

	// [7] Nmtoken ::= (NameChar)+
	public static final Production NMTOKEN = NAME_CHAR.oneOrMore();

	// [8] Nmtokens ::= Nmtoken (#x20 Nmtoken)*
	public static final Delimited NMTOKENS = delimited(NMTOKEN,
			CharConstants.SPACE_CHAR);

	// [69] PEReference ::= '%' Name ';'
	public static final Production PE_REFERENCE = CharConstants.PERCENT_SIGN
			.followedBy(NAME).followedBy(CharConstants.SEMICOLON);

	// [68] EntityRef ::= '&' Name ';'
	public static final Production ENTITY_REF = CharConstants.AMPERSAND
			.followedBy(NAME).followedBy(CharConstants.SEMICOLON);

	// [66] CharRef ::= '&#' [0-9]+ ';' | '&#x' [0-9a-fA-F]+ ';'
	public static final Production CHAR_REF = sequence(
			Producer.str("&#"),
			choice(CharConstants.DIGITS.oneOrMore(),
					sequence(
							Producer._char('x'),
							Producer.charRanges(CharConstants.DIGITS,
									Producer.charRange('a', 'f'),
									Producer.charRange('A', 'F')).oneOrMore())),
			CharConstants.SEMICOLON);

	// [67] Reference ::= EntityRef | CharRef
	public static final Production REFERENCE = ENTITY_REF.or(CHAR_REF);

	// [9] EntityValue ::= '"' ([^%&"] | PEReference | Reference)* '"' | "'"
	// ([^%&'] | PEReference | Reference)* "'"
	public static final Production ENTITY_VALUE = quoted(CharConstants.QUOTES,
			negativeChars(CharConstants.PERCENT_SIGN, CharConstants.AMPERSAND),
			PE_REFERENCE, REFERENCE);

	// [10] AttValue ::= '"' ([^<&"] | Reference)* '"' | "'" ([^<&'] |
	// Reference)* "'"
	public static final Production ATT_VALUE = quoted(CharConstants.QUOTES,
			negativeChars(CharConstants.LT, CharConstants.AMPERSAND), REFERENCE);

	// [11] SystemLiteral ::= ('"' [^"]* '"') | ("'" [^']* "'")
	// zeroOrMoreChar
	public static final Production SYSTEM_LITERAL = quoted(
			CharConstants.QUOTES, CharConstants.ANY_CHAR);

	// [13] PubidChar ::= #x20 | #xD | #xA | [a-zA-Z0-9] | [-'()+,./:=?;!*#@$_%]
	public static final CharRanges PUBID_CHAR = Producer.charRanges(
			CharConstants.SPACE_CHAR, CharConstants.CR_CHAR,
			CharConstants.LF_CHAR, CharConstants.DIGITS_AND_LETTERS,
			Producer.chars("-'()+,./:=?;!*#@$_%"));

	// [12] PubidLiteral ::= '"' PubidChar* '"' | "'" (PubidChar - "'")* "'"
	public static final Production PUBID_LITERAL = quoted(CharConstants.QUOTES,
			PUBID_CHAR);

	// [21] CDEnd ::= ']]>'
	public static final Str CD_END = Producer.str("]]>");

	// [14] CharData ::= [^<&]* - ([^<&]* ']]>' [^<&]*)
	public static final Production CHAR_DATA = terminated(
			Producer.negativeChars(CharConstants.SEMICOLON, CharConstants.LT),
			CD_END);

	// [15] Comment ::= '<!--' ((Char - '-') | ('-' (Char - '-')))* '-->'
	public static final Production COMMENT = str("<!--").followedBy(
			terminated(CHAR.zeroOrMore(), str("--")).followedBy(str("--")))
			.followedBy(_char('>'));

	// [17] PITarget ::= Name - (('X' | 'x') ('M' | 'm') ('L' | 'l'))
	// public static final Production XML_IGNORECASE = sequence(
	// //
	// _char('X').or(_char('x')),
	// //
	// _char('M').or(_char('m')),
	// //
	// _char('L').or(_char('l')));
	// public static final Production PI_TARGET = NAME.butNot(XML_IGNORECASE);
	public static final Production PI_TARGET = NAME;
	// .butNot(XML_IGNORECASE);

	// [16] PI ::= '<?' PITarget (S (Char* - (Char* '?>' Char*)))? '?>'
	public static final Str PI_START = Producer.str("<?");
	public static final Str PI_END = Producer.str("?>");
	public static final Production PI = PI_START.followedBy(PI_TARGET)
			.followedBy(
					S.followedBy(PI_END).or(
							S.followedBy(terminated(CHAR.zeroOrMore(), PI_END))
									.followedBy(PI_END)));

	// [19] CDStart ::= '<![CDATA['
	public static final Str CD_START = Producer.str("<![CDATA[");

	// [20] CData ::= (Char* - (Char* ']]>' Char*))
	// public static final Production C_DATA = terminated(CHAR.zeroOrMore(),
	// CD_END);

	// [18] CDSect ::= CDStart CData CDEnd
	public static final Production CD_SECT = CD_START.followedBy(
			terminated(CHAR.zeroOrMore(), CD_END)).followedBy(CD_END);

	// [26] VersionNum ::= '1.' [0-9]+
	public static final Production VERSION_NUM = Producer.str("1.").followedBy(
			CharConstants.DIGITS.oneOrMore());

	// [25] Eq ::= S? '=' S?
	public static final Production EQ = POSSIBLY_S.followedBy(_char('='))
			.followedBy(POSSIBLY_S);

	// [24] VersionInfo ::= S 'version' Eq ("'" VersionNum "'" | '"' VersionNum
	// '"')
	public static final Production VERSION_INFO = S
			.followedBy(Producer.str("version")).followedBy(EQ)
			.followedBy(quotedSingle(CharConstants.QUOTES, VERSION_NUM));

	// [81] EncName ::= [A-Za-z] ([A-Za-z0-9._] | '-')*
	public static final Production ENC_NAME = CharConstants.LETTERS
			.followedBy(Producer.charRanges(CharConstants.DIGITS_AND_LETTERS,
					Producer.chars("._-")).zeroOrMore());

	// [80] EncodingDecl ::= S 'encoding' Eq ('"' EncName '"' | "'" EncName "'"
	// )
	public static final Production ENCODING_DECL = S
			.followedBy(str("encoding")).followedBy(EQ)
			.followedBy(quotedSingle(CharConstants.QUOTES, ENC_NAME));

	// [32] SDDecl ::= S 'standalone' Eq (("'" ('yes' | 'no') "'") | ('"' ('yes'
	// | 'no') '"'))
	public static final Production YES_NO = Producer.str("yes").or(
			Producer.str("no"));
	public static final Production SD_DECL = S
			.followedBy(Producer.str("standalone")).followedBy(EQ)
			.followedBy(quotedSingle(CharConstants.QUOTES, YES_NO));

	// [23] XMLDecl ::= '<?xml' VersionInfo EncodingDecl? SDDecl? S? '?>'
	public static final Production XML_DECL = PI_START.followedBy(str("xml"))
			.followedBy(VERSION_INFO).followedBy(ENCODING_DECL.zeroOrOne())
			.followedBy(SD_DECL.zeroOrOne()).followedBy(POSSIBLY_S)
			.followedBy(PI_END);

	// [27] Misc ::= Comment | PI | S
	public static final Production MISC = COMMENT.or(PI).or(S);

	// [22] prolog ::= XMLDecl? Misc* (doctypedecl Misc*)?
	public static final Production PROLOG = XML_DECL.zeroOrOne().followedBy(
			MISC.zeroOrMore())
	// .followedBy(DOCTYPE_DECL.followedBy(MISC.zeroOrMore()).zeroOrOne())
	;

	// [41] Attribute ::= Name Eq AttValue
	public static final Production ATTRIBUTE = NAME.followedBy(EQ).followedBy(
			ATT_VALUE);

	// [44] EmptyElemTag ::= '<' Name (S Attribute)* S? '/>'
	public static final Production START_TAG_PART = Producer._char('<')
			.followedBy(NAME).followedBy(S.followedBy(ATTRIBUTE).zeroOrMore())
			.followedBy(POSSIBLY_S);
	// public static final Production EMPTY_ELEM_TAG = START_TAG_PART
	// .followedBy(str("/>"));

	// [40] STag ::= '<' Name (S Attribute)* S? '>'
	// public static final Production S_TAG = START_TAG_PART
	// .followedBy(_char('>'));

	// [42] ETag ::= '</' Name S? '>'
	public static final Production E_TAG = str("</").followedBy(NAME)
			.followedBy(POSSIBLY_S).followedBy(CharConstants.GT);

	// element | Reference | CDSect | PI | Comment
	public static final Choice NON_CHARACTER_CONTENT = choice(REFERENCE,
			CD_SECT, PI, COMMENT);

	// [43] content ::= CharData? ((element | Reference | CDSect | PI | Comment)
	// CharData?)*
	public static final Production CONTENT = CHAR_DATA.zeroOrOne().followedBy(
			NON_CHARACTER_CONTENT.followedBy(CHAR_DATA.zeroOrOne())
					.zeroOrMore());

	// [39] element ::= EmptyElemTag|STag content ETag
	// public static final Production ELEMENT = choice(EMPTY_ELEM_TAG,
	// sequence(S_TAG, CONTENT, E_TAG));
	public static final Production ELEMENT =
	// choice(
	// START_TAG_PART.followedBy(str("/>")),
	// START_TAG_PART.followedBy(_char('>')).followedBy(CONTENT)
	// .followedBy(E_TAG));
	START_TAG_PART.followedBy(str("/>").or(
			_char('>').followedBy(CONTENT).followedBy(E_TAG)));

	// [1] document ::= prolog element Misc*

	public static final Production DOCUMENT = PROLOG.followedBy(ELEMENT)
			.followedBy(MISC.zeroOrMore());

	static {
		// Hack for circular dependency
		// NON_CHARACTER_CONTENT.add(ELEMENT);
	}

}
