package org.hisrc.jstax.xml;

import static org.hisrc.jstax.grammar.Grammar._char;
import static org.hisrc.jstax.grammar.Grammar.charRange;
import static org.hisrc.jstax.grammar.Grammar.charRanges;
import static org.hisrc.jstax.grammar.Grammar.chars;
import static org.hisrc.jstax.grammar.Grammar.choice;
import static org.hisrc.jstax.grammar.Grammar.delimited;
import static org.hisrc.jstax.grammar.Grammar.excluding;
import static org.hisrc.jstax.grammar.Grammar.negativeChars;
import static org.hisrc.jstax.grammar.Grammar.notIncluding;
import static org.hisrc.jstax.grammar.Grammar.oneOrMore;
import static org.hisrc.jstax.grammar.Grammar.quoted;
import static org.hisrc.jstax.grammar.Grammar.sequence;
import static org.hisrc.jstax.grammar.Grammar.str;
import static org.hisrc.jstax.grammar.Grammar.strIgnoreCase;
import static org.hisrc.jstax.grammar.Grammar.surrounded;
import static org.hisrc.jstax.grammar.Grammar.zeroOrMore;
import static org.hisrc.jstax.grammar.Grammar.zeroOrOne;

import org.hisrc.jstax.grammar.Char;
import org.hisrc.jstax.grammar.CharConstants;
import org.hisrc.jstax.grammar.CharRange;
import org.hisrc.jstax.grammar.CharRanges;
import org.hisrc.jstax.grammar.Chars;
import org.hisrc.jstax.grammar.Choice;
import org.hisrc.jstax.grammar.Delimited;
import org.hisrc.jstax.grammar.Excluding;
import org.hisrc.jstax.grammar.NotIncluding;
import org.hisrc.jstax.grammar.OneOrMore;
import org.hisrc.jstax.grammar.Quoted;
import org.hisrc.jstax.grammar.Sequence;
import org.hisrc.jstax.grammar.Str;
import org.hisrc.jstax.grammar.Surrounded;
import org.hisrc.jstax.grammar.ZeroOrMore;

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
	public static final Char GT = _char('>');
	public static final CharRange DIGITS = charRange('0', '9');
	public static final CharRanges LETTERS = charRanges(charRange('a', 'z'),
			charRange('A', 'Z'));
	public static final CharRanges DIGITS_AND_LETTERS = charRanges(DIGITS,
			LETTERS);

	public static final Choice YES_NO = choice(str("yes"), str("no"));

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
	public static final ZeroOrMore POSSIBLY_S = zeroOrMore(WHITESPACE_CHAR);

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
			_char('-'), _char('.'), DIGITS, _char('\u00B7'),
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
			choice(negativeChars(PERCENT_SIGN, AMPERSAND), PE_REFERENCE,
					REFERENCE), QUOTES);

	// [10] AttValue ::= '"' ([^<&"] | Reference)* '"' | "'" ([^<&'] |
	// Reference)* "'"
	public static final Quoted ATT_VALUE = quoted(
			choice(negativeChars(LT, AMPERSAND), REFERENCE), QUOTES);

	// [11] SystemLiteral ::= ('"' [^"]* '"') | ("'" [^']* "'")
	// zeroOrMoreChar
	public static final Quoted SYSTEM_LITERAL = quoted(ANY_CHAR, QUOTES);

	// [13] PubidChar ::= #x20 | #xD | #xA | [a-zA-Z0-9] | [-'()+,./:=?;!*#@$_%]

	public static final CharRanges PUBID_CHAR = charRanges(SPACE_CHAR, CR_CHAR,
			LF_CHAR, DIGITS_AND_LETTERS, chars("-'()+,./:=?;!*#@$_%"));

	// [12] PubidLiteral ::= '"' PubidChar* '"' | "'" (PubidChar - "'")* "'"

	// TODO zeroOrMoreChar
	public static final Quoted PUBID_LITERAL = quoted(PUBID_CHAR, QUOTES);

	// [21] CDEnd ::= ']]>'
	public static final Str CD_END = str("]]>");

	// [14] CharData ::= [^<&]* - ([^<&]* ']]>' [^<&]*)
	// TODO Check where CharData is used.
	public static final NotIncluding CHAR_DATA = notIncluding(
			negativeChars(SEMICOLON, LT), CD_END);

	// [15] Comment ::= '<!--' ((Char - '-') | ('-' (Char - '-')))* '-->'
	// TODO Recheck
	public static final Sequence COMMENT = sequence(
			surrounded(str("<!--"), notIncluding(CHAR, str("--")), str("--")),
			GT);

	// [17] PITarget ::= Name - (('X' | 'x') ('M' | 'm') ('L' | 'l'))

	public static final Excluding PI_TARGET = excluding(NAME,
			strIgnoreCase("xml"));

	// [16] PI ::= '<?' PITarget (S (Char* - (Char* '?>' Char*)))? '?>'
	// TODO
	public static final Surrounded PI = surrounded(
			str("<?"),
			sequence(PI_TARGET,
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
	public static final Sequence VERSION_NUM = sequence(str("1."),
			oneOrMore(DIGITS));
	// [25] Eq ::= S? '=' S?
	public static final Sequence EQ = sequence(POSSIBLY_S, _char('='),
			POSSIBLY_S);
	// [24] VersionInfo ::= S 'version' Eq ("'" VersionNum "'" | '"' VersionNum
	// '"')
	public static final Sequence VERSION_INFO = sequence(S, str("version"), EQ,
			quoted(VERSION_NUM, QUOTES));
	// [27] Misc ::= Comment | PI | S

	public static final Choice MISC = choice(COMMENT, PI, S);

	// [22] prolog ::= XMLDecl? Misc* (doctypedecl Misc*)?

	// [32] SDDecl ::= S 'standalone' Eq (("'" ('yes' | 'no') "'") | ('"' ('yes'
	// | 'no') '"'))

	public static final Sequence SD_DECL = sequence(S, str("standalone"), EQ,
			quoted(YES_NO, QUOTES));

	// [81] EncName ::= [A-Za-z] ([A-Za-z0-9._] | '-')*
	public static final Sequence ENC_NAME = sequence(LETTERS,
			oneOrMore(charRanges(DIGITS_AND_LETTERS, chars("._-"))));
	// [80] EncodingDecl ::= S 'encoding' Eq ('"' EncName '"' | "'" EncName "'"
	// )
	// TODO Wrong in quotes!!!
	public static final Sequence ENCODING_DECL = sequence(S, str("encoding"),
			EQ, quoted(ENC_NAME, QUOTES));

	// [23] XMLDecl ::= '<?xml' VersionInfo EncodingDecl? SDDecl? S? '?>'
	public static final Surrounded XML_DECL = surrounded(
			str("<?xml"),
			sequence(VERSION_INFO, zeroOrOne(ENCODING_DECL),
					zeroOrOne(SD_DECL), POSSIBLY_S), str("?>"));

	// [28a] DeclSep ::= PEReference | S

	public static final Choice DECL_SEP = choice(PE_REFERENCE, S);

	// [28] doctypedecl ::= '<!DOCTYPE' S Name (S ExternalID)? S? ('[' intSubset
	// ']' S?)? '>'
	// [28b] intSubset ::= (markupdecl | DeclSep)*

	// [46] contentspec ::= 'EMPTY' | 'ANY' | Mixed | children

	// [51] Mixed ::= '(' S? '#PCDATA' (S? '|' S? Name)* S? ')*'
	// | '(' S? '#PCDATA' S? ')'

	// [47] children ::= (choice | seq) ('?' | '*' | '+')?
	// [48] cp ::= (Name | choice | seq) ('?' | '*' | '+')?
	// [49] choice ::= '(' S? cp ( S? '|' S? cp )+ S? ')'
	// [50] seq ::= '(' S? cp ( S? ',' S? cp )* S? ')'

	public static final NotIncluding CONTENTSPEC = notIncluding(ANY_CHAR, GT);

	// [45] elementdecl ::= '<!ELEMENT' S Name S contentspec S? '>'
	public static final Surrounded ELEMENTDECL = surrounded(str("<!ELEMENT"),
			sequence(S, NAME, S, CONTENTSPEC, POSSIBLY_S), GT);

	// [52] AttlistDecl ::= '<!ATTLIST' S Name AttDef* S? '>'

	// [55] StringType ::= 'CDATA'
	public static final Str STRING_TYPE = str("CDATA");

	// [56] TokenizedType ::= 'ID'
	// | 'IDREF'
	// | 'IDREFS'
	// | 'ENTITY'
	// | 'ENTITIES'
	// | 'NMTOKEN'
	// | 'NMTOKENS'
	public static final Choice TOKENIZED_TYPE = choice(str("ID"), str("IDREF"),
			str("IDREFS"), str("ENTITY"), str("ENTITIES"), str("NMTOKEN"),
			str("NMTOKENS"));

	// [58] NotationType ::= 'NOTATION' S '(' S? Name (S? '|' S? Name)* S? ')'
	public static final Sequence NOTATION_TYPE = sequence(
	// 'NOTATION'
			str("NOTATION"),
			// S
			S,
			// '(' S? Name (S? '|' S? Name)* S? ')'
			surrounded(
			// '('
					_char('('),
					// S? Name (S? '|' S? Name)* S?
					sequence(
					// S?
							POSSIBLY_S,
							// Name
							NAME,
							// (S? '|' S? Name)*
							zeroOrMore(
							// S? '|' S? Name
							sequence(
							// S?
									POSSIBLY_S,
									// |
									_char('|'),
									// S?
									POSSIBLY_S,
									// Name
									NAME)),
							// S?
							POSSIBLY_S),
					// ')'
					_char(')')));

	// [59] Enumeration ::= '(' S? Nmtoken (S? '|' S? Nmtoken)* S? ')'

	public static final Surrounded ENUMERATION = surrounded(
	// '('
			_char('('),
			// S? Nmtoken (S? '|' S? Nmtoken)* S?
			sequence(
			// S?
					POSSIBLY_S,
					// Nmtoken
					NMTOKEN,
					// (S? '|' S? Nmtoken)*
					zeroOrMore(
					// S? '|' S? Nmtoken
					sequence(POSSIBLY_S, _char('|'), POSSIBLY_S, NMTOKEN)),
					// S?
					POSSIBLY_S),
			// ')'
			_char(')'));

	// [57] EnumeratedType ::= NotationType | Enumeration
	public static final Choice ENUMERATED_TYPE = choice(NOTATION_TYPE,
			ENUMERATION);

	// TODO Do we really need this
	// [54] AttType ::= StringType | TokenizedType | EnumeratedType
	public static final Choice ATT_TYPE = choice(STRING_TYPE, TOKENIZED_TYPE,
			ENUMERATED_TYPE);

	// [60] DefaultDecl ::= '#REQUIRED' | '#IMPLIED'| (('#FIXED' S)? AttValue)
	public static final Choice DEFAULT_DECL = choice(str("#REQUIRED"),
			str("#IMPLIED"), str("#FIXED"),
			sequence(zeroOrOne(sequence(str("#FIXED"), S)), ATT_VALUE));

	// [53] AttDef ::= S Name S AttType S DefaultDecl
	public static final Sequence ATT_DEF = sequence(S, NAME, S, ATT_TYPE, S,
			DEFAULT_DECL);

	// [52] AttlistDecl ::= '<!ATTLIST' S Name AttDef* S? '>'
	public static final Surrounded ATTLIST_DECL = surrounded(str("<!ATTLIST"),
			sequence(S, NAME, zeroOrMore(ATT_DEF), POSSIBLY_S), str(">"));

	// [75] ExternalID ::= 'SYSTEM' S SystemLiteral | 'PUBLIC' S PubidLiteral S
	// SystemLiteral
	public static final Choice EXTERNAL_ID = choice(
			sequence(str("SYSTEM"), S, SYSTEM_LITERAL),
			sequence(str("PUBLIC"), S, PUBID_LITERAL, S, SYSTEM_LITERAL));

	// [74] PEDef ::= EntityValue | ExternalID
	public static final Choice PE_DEF = choice(ENTITY_VALUE, EXTERNAL_ID);

	// [76] NDataDecl ::= S 'NDATA' S Name
	public static final Sequence N_DATA_DECL = sequence(S, str("NDATA"), S,
			NAME);

	// [73] EntityDef ::= EntityValue | (ExternalID NDataDecl?)
	public static final Choice ENTITY_DEF = choice(ENTITY_VALUE,
			sequence(EXTERNAL_ID, zeroOrOne(N_DATA_DECL)));

	// [72] PEDecl ::= '<!ENTITY' S '%' S Name S PEDef S? '>'
	public static final Surrounded PE_DECL = surrounded(str("<!ENTITY"),
			sequence(S, PERCENT_SIGN, S, NAME, S, PE_DEF, POSSIBLY_S),
			_char('>'));

	// [71] GEDecl ::= '<!ENTITY' S Name S EntityDef S? '>'
	public static final Surrounded GE_DECL = surrounded(str("<!ENTITY"),
			sequence(S, NAME, S, ENTITY_DEF, POSSIBLY_S), _char('>'));

	// [70] EntityDecl ::= GEDecl | PEDecl
	public static final Choice ENTITY_DECL = choice(GE_DECL, PE_DECL);

	// [83] PublicID ::= 'PUBLIC' S PubidLiteral
	public static final Sequence PUBLIC_ID = sequence(str("PUBLIC"), S,
			PUBID_LITERAL);

	// [82] NotationDecl ::= '<!NOTATION' S Name S (ExternalID | PublicID) S?
	// '>'
	public static final Surrounded NOTATION_DECL = surrounded(
			str("<!NOTATION"),
			sequence(S, NAME, S, choice(EXTERNAL_ID, PUBLIC_ID), POSSIBLY_S),
			_char('>'));
	// [29] markupdecl ::= elementdecl | AttlistDecl | EntityDecl | NotationDecl
	// | PI | Comment

	public static final Choice MARKUPDECL = choice(ELEMENTDECL, ATTLIST_DECL,
			ENTITY_DECL, NOTATION_DECL, PI, COMMENT);

	// [28b] intSubset ::= (markupdecl | DeclSep)*
	public static final ZeroOrMore INT_SUBSET = zeroOrMore(choice(MARKUPDECL,
			DECL_SEP));

	// [28] doctypedecl ::= '<!DOCTYPE' S Name (S ExternalID)? S? ('[' intSubset
	// ']' S?)? '>'
	public static final Sequence DOCTYPEDECL = sequence(str("<!DOCTYPE'"), S,
			NAME, zeroOrOne(sequence(S, EXTERNAL_ID)), POSSIBLY_S,
			zeroOrOne(sequence(_char('['), INT_SUBSET, _char(']'), POSSIBLY_S)));

	// [77] TextDecl ::= '<?xml' VersionInfo? EncodingDecl S? '?>'
	public static final Surrounded TEXT_DECL = surrounded(str("<?xml"),
			sequence(zeroOrOne(VERSION_INFO), ENCODING_DECL, POSSIBLY_S),
			str("?>"));

	// [61] conditionalSect ::= includeSect | ignoreSect
	// [62] includeSect ::= '<![' S? 'INCLUDE' S? '[' extSubsetDecl ']]>'
	// [63] ignoreSect ::= '<![' S? 'IGNORE' S? '[' ignoreSectContents* ']]>'
	// [64] ignoreSectContents ::= Ignore ('<![' ignoreSectContents ']]>'
	// Ignore)*

	// [65] Ignore ::= Char* - (Char* ('<![' | ']]>') Char*)
	// public static final Excluding IGNORE = excluding(
	// zeroOrMore(CHAR),
	// sequence(zeroOrMore(CHAR),
	// choice(str("<!["), str("]]>"), zeroOrMore(CHAR))));

	// [61] conditionalSect ::= includeSect | ignoreSect
	// public static final Choice CONDITIONAL_SECT = choice(INCLUDE_SECT,
	// IGNORE_SECT);

	// [41] Attribute ::= Name Eq AttValue
	public static final Sequence ATTRIBUTE = sequence(NAME, EQ, ATT_VALUE);

	// [44] EmptyElemTag ::= '<' Name (S Attribute)* S? '/>'
	public static final Surrounded EMPTY_ELEM_TAG = surrounded(_char('<'),
			sequence(NAME, zeroOrMore(sequence(S, ATTRIBUTE)), POSSIBLY_S),
			str("/>"));

	// [40] STag ::= '<' Name (S Attribute)* S? '>'
	public static final Surrounded S_TAG = surrounded(_char('<'),
			sequence(NAME, zeroOrMore(sequence(S, ATTRIBUTE)), POSSIBLY_S), GT);

	// [42] ETag ::= '</' Name S? '>'
	public static final Surrounded E_TAG = surrounded(str("</"),
			sequence(NAME, POSSIBLY_S), GT);

	// [43] content ::= CharData? ((element | Reference | CDSect | PI | Comment)
	// CharData?)*
	public static final Sequence CONTENT = sequence(
			zeroOrOne(CHAR_DATA),
			zeroOrMore(sequence(
					// TODO ELEMENT
					choice(null, REFERENCE, CD_SECT, PI, COMMENT),
					zeroOrOne(CHAR_DATA))));

	// [39] element ::= EmptyElemTag|STag content ETag
	public static final Choice ELEMENT = choice(EMPTY_ELEM_TAG,
			sequence(S_TAG, CONTENT, E_TAG));

	// [22] prolog ::= XMLDecl? Misc* (doctypedecl Misc*)?
	public static final Sequence PROLOG = sequence(zeroOrOne(XML_DECL),
			zeroOrMore(MISC),
			zeroOrOne(sequence(DOCTYPEDECL, zeroOrMore(MISC)))
	);

	// [1] document ::= prolog element Misc*
	public static final Sequence DOCUMENT = sequence(PROLOG, ELEMENT,
			zeroOrMore(MISC));

}