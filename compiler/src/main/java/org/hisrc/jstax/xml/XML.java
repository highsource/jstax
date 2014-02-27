package org.hisrc.jstax.xml;

import static org.hisrc.jstax.grammar.Grammar._char;
import static org.hisrc.jstax.grammar.Grammar.charRange;
import static org.hisrc.jstax.grammar.Grammar.charRanges;
import static org.hisrc.jstax.grammar.Grammar.chars;
import static org.hisrc.jstax.grammar.Grammar.choice;
import static org.hisrc.jstax.grammar.Grammar.delimited;
import static org.hisrc.jstax.grammar.Grammar.negativeChars;
import static org.hisrc.jstax.grammar.Grammar.notIncluding;
import static org.hisrc.jstax.grammar.Grammar.oneOrMore;
import static org.hisrc.jstax.grammar.Grammar.quoted;
import static org.hisrc.jstax.grammar.Grammar.sequence;
import static org.hisrc.jstax.grammar.Grammar.str;
import static org.hisrc.jstax.grammar.Grammar.surrounded;
import static org.hisrc.jstax.grammar.Grammar.zeroOrMore;
import static org.hisrc.jstax.grammar.Grammar.zeroOrOne;

import org.hisrc.jstax.grammar.CharConstants;
import org.hisrc.jstax.grammar.CharRanges;
import org.hisrc.jstax.grammar.Delimited;
import org.hisrc.jstax.grammar.NotIncluding;
import org.hisrc.jstax.grammar.OneOrMoreCh;
import org.hisrc.jstax.grammar.OptionalPrefixedChoice;
import org.hisrc.jstax.grammar.PrefixedChoice;
import org.hisrc.jstax.grammar.PrefixedSequence;
import org.hisrc.jstax.grammar.Quoted;
import org.hisrc.jstax.grammar.Str;
import org.hisrc.jstax.grammar.Surrounded;
import org.hisrc.jstax.grammar.ZeroOrMoreCh;

public class XML {

	// [2] Char ::= #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] |
	// [#x10000-#x10FFFF] /* any Unicode character, excluding the surrogate
	// blocks, FFFE, and FFFF. */
	public static final CharRanges CHAR = charRanges(
			charRange('\u0021', '\uD7FF'), CharConstants.WHITESPACE_CHAR,
			charRange('\uE000', '\uFFFD'));
	// [3] S ::= (#x20 | #x9 | #xD | #xA)+
	public static final OneOrMoreCh S = oneOrMore(CharConstants.WHITESPACE_CHAR);
	public static final OptionalPrefixedChoice POSSIBLY_S = zeroOrMore(CharConstants.WHITESPACE_CHAR);
	// [4] NameStartChar ::= ":" | [A-Z] | "_" | [a-z] | [#xC0-#xD6] |
	// [#xD8-#xF6] | [#xF8-#x2FF] | [#x370-#x37D] | [#x37F-#x1FFF] |
	// [#x200C-#x200D] | [#x2070-#x218F] | [#x2C00-#x2FEF] | [#x3001-#xD7FF] |
	// [#xF900-#xFDCF] | [#xFDF0-#xFFFD] | [#x10000-#xEFFFF]
	public static final CharRanges NAME_START_CHAR = charRanges(
			charRange('A', 'Z'), charRange('a', 'z'), CharConstants.COLON,
			_char('_'), charRange('\u00C0', '\u00D6'),
			charRange('\u00D8', '\u00F6'), charRange('\u00F8', '\u02FF'),
			charRange('\u0370', '\u037D'), charRange('\u037F', '\u1FFF'),
			charRange('\u200C', '\u200D'), charRange('\u2070', '\u218F'),
			charRange('\u2C00', '\u2FEF'), charRange('\u3001', '\uD7FF'),
			charRange('\uF900', '\uFDCF'), charRange('\uFDF0', '\uFFFD'));
	// [4a] NameChar ::= NameStartChar | "-" | "." | [0-9] | #xB7 |
	// [#x0300-#x036F] | [#x203F-#x2040]
	public static final CharRanges NAME_CHAR = charRanges(NAME_START_CHAR,
			_char('-'), _char('.'), CharConstants.DIGITS, _char('\u00B7'),
			charRange('\u0300', '\u036F'), charRange('\u203F', '\u2040'));

	// [5] Name ::= NameStartChar (NameChar)*
	// TODO StartedByAndFollowed ?
	// TODO zeroOrMoreChar
	public static final PrefixedSequence NAME = sequence(NAME_START_CHAR,
			zeroOrMore(NAME_CHAR));
	public static final PrefixedChoice YES_NO = choice(str("yes"), str("no"));
	// [56] TokenizedType ::= 'ID'
	// | 'IDREF'
	// | 'IDREFS'
	// | 'ENTITY'
	// | 'ENTITIES'
	// | 'NMTOKEN'
	// | 'NMTOKENS'
	public static final PrefixedChoice TOKENIZED_TYPE = choice(str("ID"),
			str("IDREF"), str("IDREFS"), str("ENTITY"), str("ENTITIES"),
			str("NMTOKEN"), str("NMTOKENS"));
	// [75] ExternalID ::= 'SYSTEM' S SystemLiteral | 'PUBLIC' S PubidLiteral S
	// SystemLiteral
	public static final PrefixedChoice EXTERNAL_ID = choice(
			sequence(str("SYSTEM"), S, XML.SYSTEM_LITERAL),
			sequence(str("PUBLIC"), S, XML.PUBID_LITERAL, S,
					XML.SYSTEM_LITERAL));
	// [6] Names ::= Name (#x20 Name)*
	public static final Delimited NAMES = delimited(NAME,
			CharConstants.SPACE_CHAR);
	// [7] Nmtoken ::= (NameChar)+
	public static final OneOrMoreCh NMTOKEN = oneOrMore(NAME_CHAR);
	// [8] Nmtokens ::= Nmtoken (#x20 Nmtoken)*
	public static final Delimited NMTOKENS = delimited(NMTOKEN,
			CharConstants.SPACE_CHAR);
	// [66] CharRef ::= '&#' [0-9]+ ';' | '&#x' [0-9a-fA-F]+ ';'
	public static final PrefixedSequence CHAR_REF = sequence(
			str("&#"),
			choice(oneOrMore(CharConstants.DIGITS),
					sequence(_char('x'),
							oneOrMore(CharConstants.DIGITS_AND_LETTERS))),
			CharConstants.SEMICOLON);
	// [68] EntityRef ::= '&' Name ';'
	public static final Surrounded ENTITY_REF = surrounded(
			CharConstants.AMPERSAND, NAME, CharConstants.SEMICOLON);
	// [67] Reference ::= EntityRef | CharRef
	public static final PrefixedChoice REFERENCE = choice(ENTITY_REF, CHAR_REF);
	// [69] PEReference ::= '%' Name ';'
	public static final Surrounded PE_REFERENCE = surrounded(
			CharConstants.PERCENT_SIGN, NAME, CharConstants.SEMICOLON);
	// [13] PubidChar ::= #x20 | #xD | #xA | [a-zA-Z0-9] | [-'()+,./:=?;!*#@$_%]
	public static final CharRanges PUBID_CHAR = charRanges(
			CharConstants.SPACE_CHAR, CharConstants.CR_CHAR,
			CharConstants.LF_CHAR, CharConstants.DIGITS_AND_LETTERS,
			chars("-'()+,./:=?;!*#@$_%"));
	// [21] CDEnd ::= ']]>'
	public static final Str CD_END = str("]]>");
	// [14] CharData ::= [^<&]* - ([^<&]* ']]>' [^<&]*)
	// TODO Check where CharData is used.
	public static final NotIncluding CHAR_DATA = notIncluding(
			negativeChars(CharConstants.SEMICOLON, CharConstants.LT), CD_END);
	// [15] Comment ::= '<!--' ((Char - '-') | ('-' (Char - '-')))* '-->'
	// TODO Recheck
	public static final PrefixedSequence COMMENT = sequence(
			surrounded(str("<!--"), notIncluding(CHAR, str("--")), str("--")),
			CharConstants.GT);
	// [16] PI ::= '<?' PITarget (S (Char* - (Char* '?>' Char*)))? '?>'
	// TODO
	public static final Surrounded PI = surrounded(
			str("<?"),
			sequence(XML10.PI_TARGET,
					zeroOrOne(sequence(S, notIncluding(CHAR, str("?>"))))),
			str("?>"));
	// [19] CDStart ::= '<![CDATA['
	public static final Str CD_START = str("<![CDATA[");
	// [20] CData ::= (Char* - (Char* ']]>' Char*))
	public static final NotIncluding C_DATA = notIncluding(CHAR, CD_END);
	// [18] CDSect ::= CDStart CData CDEnd
	public static final Surrounded CD_SECT = surrounded(CD_START, C_DATA,
			CD_END);
	// [26] VersionNum ::= '1.' [0-9]+
	// TODO oneOrMore(DIGITS);
	public static final PrefixedSequence VERSION_NUM = sequence(str("1."),
			oneOrMore(CharConstants.DIGITS));
	// [24] VersionInfo ::= S 'version' Eq ("'" VersionNum "'" | '"' VersionNum
	// '"')
	public static final PrefixedSequence VERSION_INFO = sequence(S,
			str("version"), XML.EQ, quoted(VERSION_NUM, CharConstants.QUOTES));
	// [27] Misc ::= Comment | PI | S
	// [32] SDDecl ::= S 'standalone' Eq (("'" ('yes' | 'no') "'") | ('"' ('yes'
	// | 'no') '"'))
	public static final PrefixedSequence SD_DECL = sequence(S,
			str("standalone"), XML.EQ, quoted(YES_NO, CharConstants.QUOTES));
	// [81] EncName ::= [A-Za-z] ([A-Za-z0-9._] | '-')*
	public static final PrefixedSequence ENC_NAME = sequence(
			CharConstants.LETTERS,
			oneOrMore(charRanges(CharConstants.DIGITS_AND_LETTERS, chars("._-"))));
	// [23] XMLDecl ::= '<?xml' VersionInfo EncodingDecl? SDDecl? S? '?>'
	public static final Surrounded XML_DECL = surrounded(
			str("<?xml"),
			sequence(VERSION_INFO, zeroOrOne(XML.ENCODING_DECL),
					zeroOrOne(SD_DECL), POSSIBLY_S), str("?>"));
	// [28a] DeclSep ::= PEReference | S
	public static final PrefixedChoice DECL_SEP = choice(PE_REFERENCE, S);
	// [46] contentspec ::= 'EMPTY' | 'ANY' | Mixed | children
	public static final NotIncluding CONTENTSPEC = notIncluding(
			CharConstants.ANY_CHAR, CharConstants.GT);
	// [45] elementdecl ::= '<!ELEMENT' S Name S contentspec S? '>'
	public static final Surrounded ELEMENTDECL = surrounded(str("<!ELEMENT"),
			sequence(S, NAME, S, CONTENTSPEC, POSSIBLY_S), CharConstants.GT);
	// [76] NDataDecl ::= S 'NDATA' S Name
	public static final PrefixedSequence N_DATA_DECL = sequence(S,
			str("NDATA"), S, NAME);
	// [72] PEDecl ::= '<!ENTITY' S '%' S Name S PEDef S? '>'
	public static final Surrounded PE_DECL = surrounded(
			str("<!ENTITY"),
			sequence(S, CharConstants.PERCENT_SIGN, S, NAME, S, XML.PE_DEF,
					POSSIBLY_S), _char('>'));
	// [71] GEDecl ::= '<!ENTITY' S Name S EntityDef S? '>'
	public static final Surrounded GE_DECL = surrounded(str("<!ENTITY"),
			sequence(S, NAME, S, XML10.ENTITY_DEF, POSSIBLY_S), _char('>'));
	// [70] EntityDecl ::= GEDecl | PEDecl
	public static final PrefixedChoice ENTITY_DECL = choice(GE_DECL, PE_DECL);
	// [83] PublicID ::= 'PUBLIC' S PubidLiteral
	public static final PrefixedSequence PUBLIC_ID = sequence(str("PUBLIC"), S,
			XML.PUBID_LITERAL);
	// [82] NotationDecl ::= '<!NOTATION' S Name S (ExternalID | PublicID) S?
	// '>'
	public static final Surrounded NOTATION_DECL = surrounded(
			str("<!NOTATION"),
			sequence(S, NAME, S, choice(EXTERNAL_ID, PUBLIC_ID), POSSIBLY_S),
			_char('>'));
	// [29] markupdecl ::= elementdecl | AttlistDecl | EntityDecl | NotationDecl
	// | PI | Comment
	// [28] doctypedecl ::= '<!DOCTYPE' S Name (S ExternalID)? S? ('[' intSubset
	// ']' S?)? '>'
	public static final PrefixedSequence DOCTYPEDECL = sequence(
			str("<!DOCTYPE'"),
			S,
			NAME,
			zeroOrOne(sequence(S, EXTERNAL_ID)),
			POSSIBLY_S,
			zeroOrOne(sequence(_char('['), XML10.INT_SUBSET, _char(']'),
					POSSIBLY_S)));
	// [41] Attribute ::= Name Eq AttValue
	public static final PrefixedSequence ATTRIBUTE = sequence(NAME, XML.EQ,
			XML.ATT_VALUE);
	// [44] EmptyElemTag ::= '<' Name (S Attribute)* S? '/>'
	public static final Surrounded EMPTY_ELEM_TAG = surrounded(_char('<'),
			sequence(NAME, zeroOrMore(sequence(S, ATTRIBUTE)), POSSIBLY_S),
			str("/>"));
	// [40] STag ::= '<' Name (S Attribute)* S? '>'
	public static final Surrounded S_TAG = surrounded(_char('<'),
			sequence(NAME, zeroOrMore(sequence(S, ATTRIBUTE)), POSSIBLY_S),
			CharConstants.GT);
	// [42] ETag ::= '</' Name S? '>'
	public static final Surrounded E_TAG = surrounded(str("</"),
			sequence(NAME, POSSIBLY_S), CharConstants.GT);
	// [39] element ::= EmptyElemTag|STag content ETag
	public static final PrefixedChoice ELEMENT = choice(EMPTY_ELEM_TAG,
			sequence(S_TAG, XML10.CONTENT, E_TAG));
	// [52] AttlistDecl ::= '<!ATTLIST' S Name AttDef* S? '>'
	public static final Surrounded ATTLIST_DECL = surrounded(str("<!ATTLIST"),
			sequence(S, NAME, zeroOrMore(XML.ATT_DEF), POSSIBLY_S),
			str(">"));
	// [55] StringType ::= 'CDATA'
	public static final Str STRING_TYPE = str("CDATA");
	// [58] NotationType ::= 'NOTATION' S '(' S? Name (S? '|' S? Name)* S? ')'
	public static final PrefixedSequence NOTATION_TYPE = sequence(
			// 'NOTATION'
			str("NOTATION"),
			S,
			// '(' S? Name (S? '|' S? Name)* S? ')'
			surrounded(
			// '('
					_char('('),
					// S? Name (S? '|' S? Name)* S?
					sequence(POSSIBLY_S,
							NAME,
							// (S? '|' S? Name)*
							zeroOrMore(
							// S? '|' S? Name
							sequence(POSSIBLY_S,
							// |
									_char('|'), POSSIBLY_S, NAME)),
							POSSIBLY_S),
					// ')'
					_char(')')));
	public static final Surrounded ENUMERATION = surrounded(
	// '('
			_char('('),
			// S? Nmtoken (S? '|' S? Nmtoken)* S?
			sequence(
					POSSIBLY_S,
					NMTOKEN,
					// (S? '|' S? Nmtoken)*
					zeroOrMore(
					// S? '|' S? Nmtoken
					sequence(POSSIBLY_S, _char('|'), POSSIBLY_S,
							NMTOKEN)), POSSIBLY_S),
			// ')'
			_char(')'));
	// [57] EnumeratedType ::= NotationType | Enumeration
	public static final PrefixedChoice ENUMERATED_TYPE = choice(
			NOTATION_TYPE, ENUMERATION);
	// [77] TextDecl ::= '<?xml' VersionInfo? EncodingDecl S? '?>'
	public static final Surrounded TEXT_DECL = surrounded(
			str("<?xml"),
			sequence(zeroOrOne(VERSION_INFO), XML.ENCODING_DECL, POSSIBLY_S),
			str("?>"));
	// [9] EntityValue ::= '"' ([^%&"] | PEReference | Reference)* '"' | "'"
	// ([^%&'] | PEReference | Reference)* "'"
	public static final Quoted ENTITY_VALUE = quoted(
			choice(negativeChars(CharConstants.PERCENT_SIGN,
					CharConstants.AMPERSAND), PE_REFERENCE, REFERENCE),
			CharConstants.QUOTES);
	// [10] AttValue ::= '"' ([^<&"] | Reference)* '"' | "'" ([^<&'] |
	// Reference)* "'"
	public static final Quoted ATT_VALUE = quoted(
			choice(negativeChars(CharConstants.LT, CharConstants.AMPERSAND),
					REFERENCE), CharConstants.QUOTES);
	// [11] SystemLiteral ::= ('"' [^"]* '"') | ("'" [^']* "'")
	// zeroOrMoreChar
	public static final Quoted SYSTEM_LITERAL = quoted(CharConstants.ANY_CHAR,
			CharConstants.QUOTES);
	// [12] PubidLiteral ::= '"' PubidChar* '"' | "'" (PubidChar - "'")* "'"
	// TODO zeroOrMoreChar
	public static final Quoted PUBID_LITERAL = quoted(PUBID_CHAR,
			CharConstants.QUOTES);
	// TODO optimize
	public static final PrefixedChoice MISC = choice(COMMENT, PI, S);
	// [80] EncodingDecl ::= S 'encoding' Eq ('"' EncName '"' | "'" EncName "'"
	// )
	// TODO Wrong in quotes!!!
	public static final PrefixedSequence ENCODING_DECL = sequence(S,
			str("encoding"), XML.EQ, quoted(ENC_NAME, CharConstants.QUOTES));
	// TODO Do we really need this
	// [54] AttType ::= StringType | TokenizedType | EnumeratedType
	public static final PrefixedChoice ATT_TYPE = choice(STRING_TYPE,
			TOKENIZED_TYPE, ENUMERATED_TYPE);
	// [74] PEDef ::= EntityValue | ExternalID
	public static final PrefixedChoice PE_DEF = choice(ENTITY_VALUE,
			EXTERNAL_ID);
	public static final PrefixedChoice MARKUPDECL = choice(ELEMENTDECL,
	ATTLIST_DECL, ENTITY_DECL, NOTATION_DECL, PI,
	COMMENT);
	// [25] Eq ::= S? '=' S?
	public static final PrefixedChoice EQ = choice(
			sequence(S, _char('='), POSSIBLY_S),
			sequence(_char('='), POSSIBLY_S));
	// [53] AttDef ::= S Name S AttType S DefaultDecl
	public static final PrefixedSequence ATT_DEF = sequence(S, NAME,
			S, ATT_TYPE, S, XML10.DEFAULT_DECL);

}
