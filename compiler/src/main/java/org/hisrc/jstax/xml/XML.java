package org.hisrc.jstax.xml;

import static org.hisrc.jstax.grammar.production.Producer._char;
import static org.hisrc.jstax.grammar.production.Producer.charRange;
import static org.hisrc.jstax.grammar.production.Producer.charRanges;
import static org.hisrc.jstax.grammar.production.Producer.chars;
import static org.hisrc.jstax.grammar.production.Producer.choice;
import static org.hisrc.jstax.grammar.production.Producer.negativeChars;
import static org.hisrc.jstax.grammar.production.Producer.notContaining;
import static org.hisrc.jstax.grammar.production.Producer.oneOrMore;
import static org.hisrc.jstax.grammar.production.Producer.quoted;
import static org.hisrc.jstax.grammar.production.Producer.quotedSingle;
import static org.hisrc.jstax.grammar.production.Producer.sequence;
import static org.hisrc.jstax.grammar.production.Producer.str;
import static org.hisrc.jstax.grammar.production.Producer.zeroOrMore;
import static org.hisrc.jstax.grammar.production.Producer.zeroOrOne;

import org.hisrc.jstax.grammar.operation.AttributeLocalName;
import org.hisrc.jstax.grammar.operation.AttributeValue;
import org.hisrc.jstax.grammar.operation.CData;
import org.hisrc.jstax.grammar.operation.Comment;
import org.hisrc.jstax.grammar.operation.DecimalCharRef;
import org.hisrc.jstax.grammar.operation.EntityRef;
import org.hisrc.jstax.grammar.operation.HexCharRef;
import org.hisrc.jstax.grammar.operation.Ignore;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.operation.ProcessingInstruction;
import org.hisrc.jstax.grammar.operation.ProcessingInstructionData;
import org.hisrc.jstax.grammar.operation.ProcessingInstructionTarget;
import org.hisrc.jstax.grammar.production.Producer;
import org.hisrc.jstax.grammar.production.Production;
import org.hisrc.jstax.grammar.production.character.Char;
import org.hisrc.jstax.grammar.production.character.CharConstants;
import org.hisrc.jstax.grammar.production.character.CharRanges;
import org.hisrc.jstax.grammar.production.character.Chars;
import org.hisrc.jstax.grammar.production.structure.Choice;

public class XML {

	public static final Char HYPHEN_MINUS = _char("HYPHEN_MINUS", '-');

	public static final Char EQUALS_SIGN = _char("EQUALS_SIGN", '=');

	public static final Chars WHITESPACE_CHAR = Producer.chars(
			"WHITESPACE_CHAR", CharConstants.WHITESPACE_CHARS);

	// [2] Char ::= #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] |
	// [#x10000-#x10FFFF] /* any Unicode character, excluding the surrogate
	// blocks, FFFE, and FFFF. */
	public static final CharRanges CHAR = charRanges("CHAR",
			charRange("CHAR_0", '\u0021', '\uD7FF'), XML.WHITESPACE_CHAR,
			charRange("CHAR_1", '\uE000', '\uFFFD'));

	// [3] S ::= (#x20 | #x9 | #xD | #xA)+
	public static final Production S = oneOrMore(Ignore.INSTANCE, "S",
			Producer.chars(Ignore.INSTANCE, "WHITESPACE_CHAR",
					CharConstants.WHITESPACE_CHARS));
	public static final Production POSSIBLY_S = zeroOrOne("POSSIBLY_S", S);

	// [4] NameStartChar ::= ":" | [A-Z] | "_" | [a-z] | [#xC0-#xD6] |
	// [#xD8-#xF6] | [#xF8-#x2FF] | [#x370-#x37D] | [#x37F-#x1FFF] |
	// [#x200C-#x200D] | [#x2070-#x218F] | [#x2C00-#x2FEF] | [#x3001-#xD7FF] |
	// [#xF900-#xFDCF] | [#xFDF0-#xFFFD] | [#x10000-#xEFFFF]
	public static final CharRanges NAME_START_CHAR = charRanges(
			"NAME_START_CHAR", CharConstants.LETTERS, CharConstants.COLON,
			_char("LOW_LINE", '_'),
			charRange("NAME_START_CHAR_0", '\u00C0', '\u00D6'),
			charRange("NAME_START_CHAR_1", '\u00D8', '\u00F6'),
			charRange("NAME_START_CHAR_2", '\u00F8', '\u02FF'),
			charRange("NAME_START_CHAR_3", '\u0370', '\u037D'),
			charRange("NAME_START_CHAR_4", '\u037F', '\u1FFF'),
			charRange("NAME_START_CHAR_5", '\u200C', '\u200D'),
			charRange("NAME_START_CHAR_6", '\u2070', '\u218F'),
			charRange("NAME_START_CHAR_7", '\u2C00', '\u2FEF'),
			charRange("NAME_START_CHAR_8", '\u3001', '\uD7FF'),
			charRange("NAME_START_CHAR_9", '\uF900', '\uFDCF'),
			charRange("NAME_START_CHAR_10", '\uFDF0', '\uFFFD'));

	// [4a] NameChar ::= NameStartChar | "-" | "." | [0-9] | #xB7 |
	// [#x0300-#x036F] | [#x203F-#x2040]
	public static final CharRanges NAME_CHAR = charRanges("NAME_CHAR",
			NAME_START_CHAR, HYPHEN_MINUS, _char("FULL_STOP", '.'),
			CharConstants.DIGITS, _char("MIDDLE_DOT", '\u00B7'),
			charRange("NAME_CHAR_0", '\u0300', '\u036F'),
			charRange("NAME_CHAR_1", '\u203F', '\u2040'));

	// [5] Name ::= NameStartChar (NameChar)*
	public static final Production NAME = sequence("NAME", NAME_START_CHAR,
			zeroOrMore("NAME_CHARS", XML.NAME_CHAR));

	// // [6] Names ::= Name (#x20 Name)*
	// public static final Production NAMES = delimited("NAMES", NAME,
	// CharConstants.SPACE_CHAR);

	// // [7] Nmtoken ::= (NameChar)+
	// public static final Production NMTOKEN = oneOrMore("NMTOKEN",
	// XML.NAME_CHAR);

	// // [8] Nmtokens ::= Nmtoken (#x20 Nmtoken)*
	// public static final Production NMTOKENS = delimited("NMTOKENS", NMTOKEN,
	// CharConstants.SPACE_CHAR);

	// [69] PEReference ::= '%' Name ';'
	public static final Production PE_REFERENCE = sequence("PE_REFERENCE",
			CharConstants.PERCENT_SIGN, NAME, CharConstants.SEMICOLON);

	// [68] EntityRef ::= '&' Name ';'
	public static final Production ENTITY_REF = sequence("ENTITY_REF",
			_char(Ignore.INSTANCE, "ENTITY_REF_AMPERSAND", '&'), NAME,
			_char(EntityRef.INSTANCE, "ENTITY_REF_SEMICOLON", ';'));

	// [66] CharRef ::= '&#' [0-9]+ ';' | '&#x' [0-9a-fA-F]+ ';'
	public static final Production CHAR_REF = sequence(
			"CHAR_REF",
			sequence("CHAR_REF_PREFIX",
					_char(Ignore.INSTANCE, "CHAR_REF_PREFIX_0", '&'),
					_char(Ignore.INSTANCE, "CHAR_REF_PREFIX_0", '#')),
			choice("CHAR_REF_SUFFIX",
					sequence(
							"DEC_CHAR_REF",
							oneOrMore("DEC_CHAR_REF_N", CharConstants.DIGITS),
							_char(DecimalCharRef.INSTANCE, "DEC_CHAR_REF_END",
									';')),
					sequence(
							"HEX_CHAR_REF",
							_char(Ignore.INSTANCE, "HEX_CHAR_REF_PREFIX", 'x'),
							oneOrMore(
									"HEX_CHAR_REF_SUFFIX",
									charRanges(
											"HEX_CHARS",
											CharConstants.DIGITS,
											charRange("HEX_SMALL_LETTERS", 'a',
													'f'),
											charRange("HEX_CAPITAL_LETTERS",
													'A', 'F'))),
							_char(HexCharRef.INSTANCE, "Hex_CHAR_REF_END", ';'))));

	// [67] Reference ::= EntityRef | CharRef
	public static final Production REFERENCE = choice("REFERENCE", ENTITY_REF,
			CHAR_REF);

	// [9] EntityValue ::= '"' ([^%&"] | PEReference | Reference)* '"' | "'"
	// ([^%&'] | PEReference | Reference)* "'"
	// public static final Production ENTITY_VALUE = quoted(
	// None.INSTANCE,
	// "ENTITY_VALUE",
	// CharConstants.QUOTES,
	// negativeChars("ENTITY_VALUE_CHAR", CharConstants.PERCENT_SIGN,
	// CharConstants.AMPERSAND), PE_REFERENCE, REFERENCE);

	// [10] AttValue ::= '"' ([^<&"] | Reference)* '"' | "'" ([^<&'] |
	// Reference)* "'"
	public static final Production ATT_VALUE = quoted(
			AttributeValue.INSTANCE,
			"ATT_VALUE",
			CharConstants.QUOTES,
			negativeChars("ATT_VALUE_CHAR", CharConstants.LT,
					CharConstants.AMPERSAND), REFERENCE);

	// [11] SystemLiteral ::= ('"' [^"]* '"') | ("'" [^']* "'")
	// zeroOrMoreChar
	// public static final Production SYSTEM_LITERAL = quoted("SYSTEM_LITERAL",
	// CharConstants.QUOTES, CharConstants.ANY_CHAR);

	// [13] PubidChar ::= #x20 | #xD | #xA | [a-zA-Z0-9] | [-'()+,./:=?;!*#@$_%]
	public static final CharRanges PUBID_CHAR = charRanges("PUBID_CHAR",
			CharConstants.SPACE_CHAR, CharConstants.CR_CHAR,
			CharConstants.LF_CHAR, CharConstants.DIGITS_AND_LETTERS,
			chars("PUBID_CHAR_0", "-'()+,./:=?;!*#@$_%"));

	// [12] PubidLiteral ::= '"' PubidChar* '"' | "'" (PubidChar - "'")* "'"
	// public static final Production PUBID_LITERAL = quoted("PUBID_LITERAL",
	// CharConstants.QUOTES, PUBID_CHAR);

	// [14] CharData ::= [^<&]* - ([^<&]* ']]>' [^<&]*)
	public static final Production CHAR_DATA = notContaining(
			"CHAR_DATA",
			negativeChars("CHAR_DATA_CHAR", CharConstants.AMPERSAND,
					CharConstants.LT), str("CD_END", "]]>"));

	public static final Production COMMENT_PRE_END = new CommentPreEndProduction(
			"COMMENT_END", _char(None.INSTANCE, "COMMENT_CONTENT_TERMINATOR_0",
					'-'), _char(Comment.INSTANCE,
					"COMMENT_CONTENT_TERMINATOR_1", '-'));
	// [15] Comment ::= '<!--' ((Char - '-') | ('-' (Char - '-')))* '-->'
	public static final Production COMMENT = sequence(
			"COMMENT",
			sequence("COMMENT_START",
					_char(Ignore.INSTANCE, "COMMENT_START_0", '<'),
					_char(Ignore.INSTANCE, "COMMENT_START_1", '!'),
					_char(Ignore.INSTANCE, "COMMENT_START_2", '-'),
					_char(Ignore.INSTANCE, "COMMENT_START_3", '-')),

			COMMENT_PRE_END, CharConstants.GT);

	// [17] PITarget ::= Name - (('X' | 'x') ('M' | 'm') ('L' | 'l'))
	// public static final Production XML_IGNORECASE = sequence(
	// //
	// _char('X').or(_char('x')),
	// //
	// _char('M').or(_char('m')),
	// //
	// _char('L').or(_char('l')));
	// public static final Production PI_TARGET = NAME.butNot(XML_IGNORECASE);
	public static final Production PI_TARGET = new PITargetProduction(
			"PI_TARGET", NAME_START_CHAR, NAME_CHAR);
	// .butNot(XML_IGNORECASE);

	// [16] PI ::= '<?' PITarget (S (Char* - (Char* '?>' Char*)))? '?>'
	public static final Production PI_START = sequence("PI_START",
			_char(Ignore.INSTANCE, "PI_START_0", '<'),
			_char(Ignore.INSTANCE, "PI_START_1", '?'));

	public static final Production PI_END = sequence("PI_END",
			_char(None.INSTANCE, "PI_END_0", '?'),
			_char(ProcessingInstruction.INSTANCE, "PI_END_1", '>'));

	public static final Production PI_DELIMITER = sequence(
			"PI_DELIMITER",
			chars(ProcessingInstructionTarget.INSTANCE, "PI_DELIMITER_S_0",
					CharConstants.WHITESPACE_CHARS),
			zeroOrMore(
					"PI_DELIMITER_S_N",
					chars(Ignore.INSTANCE, "PI_DELIMITER_S_N_S",
							CharConstants.WHITESPACE_CHARS)));

	public static final Production PI_DATA_END = new PIEndProduction(
			"PI_DATA_END", _char(None.INSTANCE, "PI_END_0", '?'), _char(
					ProcessingInstructionData.INSTANCE, "PI_END_1", '>'));

	public static final Production PI = sequence(
			"PI",
			PI_START,
			PI_TARGET,
			choice("PI_END_OR_S_PI_CONTENT_PI_END", PI_END,
					sequence("S_PI_CONTENT_PI_END", PI_DELIMITER, PI_DATA_END)));

	// [19] CDStart ::= '<![CDATA['
	public static final Production CD_START = sequence("CD_START",
			_char(Ignore.INSTANCE, "CD_START_0", '<'),
			_char(Ignore.INSTANCE, "CD_START_1", '!'),
			_char(Ignore.INSTANCE, "CD_START_2", '['),
			_char(Ignore.INSTANCE, "CD_START_3", 'C'),
			_char(Ignore.INSTANCE, "CD_START_4", 'D'),
			_char(Ignore.INSTANCE, "CD_START_5", 'A'),
			_char(Ignore.INSTANCE, "CD_START_6", 'T'),
			_char(Ignore.INSTANCE, "CD_START_7", 'A'),
			_char(Ignore.INSTANCE, "CD_START_8", '['));

	// [20] CData ::= (Char* - (Char* ']]>' Char*))
	// public static final Production C_DATA = terminated(CHAR.zeroOrMore(),
	// CD_END);

	// [21] CDEnd ::= ']]>'
	public static final Production CDATA_CDEND = new CDEndProduction(
			"CDATA_CDEND", _char(None.INSTANCE, "CD_END_0", ']'), _char(
					None.INSTANCE, "CD_END_1", ']'), _char(CData.INSTANCE,
					"CD_END_2", '>'));

	// [18] CDSect ::= CDStart CData CDEnd
	public static final Production CD_SECT = sequence("CD_SECT", CD_START,
			CDATA_CDEND);

	// [26] VersionNum ::= '1.' [0-9]+
	public static final Production VERSION_NUM = sequence("VERSION_NUM",
			str("VERSION_NUM_PREFIX", "1."),
			oneOrMore("VERSION_NUM_SUFFIX", CharConstants.DIGITS));

	// [25] Eq ::= S? '=' S?
	public static final Production EQ = sequence("EQ", POSSIBLY_S, EQUALS_SIGN,
			POSSIBLY_S);

	// [24] VersionInfo ::= S 'version' Eq ("'" VersionNum "'" | '"' VersionNum
	// '"')
	public static final Production VERSION_INFO = sequence(
			"VERSION_INFO",
			S,
			str("VERSION_ATTRIBUTE_NAME", "version"),
			EQ,
			quotedSingle("VERSION_INFO_VALUE", CharConstants.QUOTES,
					VERSION_NUM));

	// [81] EncName ::= [A-Za-z] ([A-Za-z0-9._] | '-')*
	public static final Production ENC_NAME = sequence(
			"ENC_NAME",
			CharConstants.LETTERS,
			zeroOrMore(
					"ENC_NAME_CHARS",
					charRanges("ENC_NAME_CHAR",
							CharConstants.DIGITS_AND_LETTERS,
							chars("SPECIAL_ENC_NAME_CHARS", "._-"))));

	// [80] EncodingDecl ::= S 'encoding' Eq ('"' EncName '"' | "'" EncName "'"
	// )
	public static final Production ENCODING_DECL = sequence("ENCODING_DECL", S,
			str("ENCODING_ATTRIBUTE_NAME", "encoding"), EQ,
			quotedSingle("ENCODING_VALUE", CharConstants.QUOTES, ENC_NAME));

	// [32] SDDecl ::= S 'standalone' Eq (("'" ('yes' | 'no') "'") | ('"' ('yes'
	// | 'no') '"'))
	public static final Production YES_NO = choice("YES_NO", str("YES", "yes"),
			str("NO", "no"));
	public static final Production SD_DECL = sequence("SD_DECL", S,
			str("STANDALONE_ATTRIBUTE_NAME", "standalone"), EQ,
			quotedSingle("STANDALONE_VALUE", CharConstants.QUOTES, YES_NO));

	// [23] XMLDecl ::= '<?xml' VersionInfo EncodingDecl? SDDecl? S? '?>'
	public static final Production XML_DECL =

	sequence("XML_DECL",

	PI_START, str("XML", "xml"), VERSION_INFO,
			zeroOrOne("POSSIBLY_ENCODING_DECL", ENCODING_DECL),
			zeroOrOne("POSSIBLY_ENCODING_SD_DECL", SD_DECL), POSSIBLY_S,
			str("XML_DECL_END", "?>"));

	// [27] Misc ::= Comment | PI | S
	public static final Production MISC = choice("MISC", COMMENT, PI, S);

	// [22] prolog ::= XMLDecl? Misc* (doctypedecl Misc*)?
	public static final Production PROLOG = sequence("PROLOG",
			zeroOrOne("POSSIBILY_XML_DECL", XML_DECL),
			zeroOrMore("PROLOG_MISC", MISC));
	// .followedBy(DOCTYPE_DECL.followedBy(MISC.zeroOrMore()).zeroOrOne())

	// [41] Attribute ::= Name Eq AttValue
	public static final Production ATTRIBUTE = sequence("ATTRIBUTE", NAME,
			POSSIBLY_S, _char(AttributeLocalName.INSTANCE, "EQUALS_SIGN", '='),
			POSSIBLY_S, ATT_VALUE);

	// [44] EmptyElemTag ::= '<' Name (S Attribute)* S? '/>'

	// StartTagPart ::= '<' Name (S Attribute)* S?
	public static final Production START_TAG_PART = sequence("START_TAG_PART",

	CharConstants.LT, NAME,
			zeroOrMore("ATTRIBUTES", sequence("ATTRIBUTE", S, ATTRIBUTE)),
			POSSIBLY_S);

	// public static final Production EMPTY_ELEM_TAG = START_TAG_PART
	// .followedBy(str("/>"));

	// [40] STag ::= '<' Name (S Attribute)* S? '>'
	// public static final Production S_TAG = START_TAG_PART
	// .followedBy(_char('>'));

	// [42] ETag ::= '</' Name S? '>'
	public static final Production E_TAG = sequence("E_TAG",
			str("E_TAG_PREFIX", "</"), NAME, POSSIBLY_S, CharConstants.GT);

	// element | Reference | CDSect | PI | Comment
	public static final Choice NON_CHARACTER_OR_ELEMENT_CONTENT = choice(
			"NON_CHARACTER_OR_ELEMENT_CONTENT", REFERENCE, CD_SECT, PI, COMMENT);

	// [43] content ::= CharData? ((element | Reference | CDSect | PI | Comment)
	// CharData?)*
	// public static final Production CONTENT =
	// CHAR_DATA.zeroOrOne().followedBy(
	// NON_CHARACTER_CONTENT.followedBy(CHAR_DATA.zeroOrOne())
	// .zeroOrMore());

	// [39] element ::= EmptyElemTag|STag content ETag
	// public static final Production ELEMENT = choice(EMPTY_ELEM_TAG,
	// sequence(S_TAG, CONTENT, E_TAG));
	public static final Production ELEMENT = new ElementProduction("ELEMENT",
			START_TAG_PART, str("EMPTY_ELEM_START_TAG_END", "/>"),
			CharConstants.GT, CHAR_DATA, NON_CHARACTER_OR_ELEMENT_CONTENT,
			E_TAG);

	// public static final Production ELEMENT = new ElementProduction(
	// "ELEMENT",
	// str("START_TAG_PART", "<e"),
	// str("EMPTY_ELEM_START_TAG_END", "/>"),
	// CharConstants.GT,
	// // CHAR_DATA,
	// _char("CHAR_DATA", 'c'),
	// _char("NON_CHARACTER_OR_ELEMENT_CONTENT", '!'),
	// str("START_TAG_PART", "</e>"));

	// choice(
	// START_TAG_PART.followedBy(str("/>")),
	// START_TAG_PART.followedBy(_char('>')).followedBy(CONTENT)
	// .followedBy(E_TAG));
	// START_TAG_PART.followedBy(str("EMPTY_ELEM_TAG_END", "/>").or(
	// CharConstants.GT.followedBy(CONTENT).followedBy(E_TAG)));

	// [1] document ::= prolog element Misc*
	// [39] element ::= EmptyElemTag|STag content ETag
	// [43] content ::= CharData? ((element | Reference | CDSect | PI | Comment)
	// CharData?)*

	public static final Production DOCUMENT = sequence("DOCUMENT", PROLOG,
			ELEMENT, zeroOrMore("DOCUMENT_MISC", MISC));

}
