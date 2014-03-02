package org.hisrc.jstax.xml;
//package org.hisrc.jstax.xml;
//
//import static org.hisrc.jstax.grammar.Grammar.choice;
//import static org.hisrc.jstax.grammar.Grammar.negativeChars;
//import static org.hisrc.jstax.grammar.Grammar.notIncluding;
//import static org.hisrc.jstax.grammar.Grammar.quoted;
//import static org.hisrc.jstax.grammar.Grammar.zeroOrMore;
//import static org.hisrc.jstax.grammar.Grammar.zeroOrOne;
//import static org.hisrc.jstax.grammar.ng.Producer.choice;
//import static org.hisrc.jstax.grammar.ng.Producer.sequence;
//
//import org.hisrc.jstax.grammar.NotIncluding;
//import org.hisrc.jstax.grammar.PrefixedChoice;
//import org.hisrc.jstax.grammar.PrefixedSequence;
//import org.hisrc.jstax.grammar.gamma.CharConstants;
//import org.hisrc.jstax.grammar.gamma.GammaProducer;
//import org.hisrc.jstax.grammar.gamma.Production;
//import org.hisrc.jstax.grammar.gamma.Quoted;
//import org.hisrc.jstax.grammar.gamma.Str;
//import org.hisrc.jstax.grammar.gamma.Surrounded;
//import org.hisrc.jstax.grammar.ng.Choice;
//
//public class XML {
//
//	// [28a] DeclSep ::= PEReference | S
//	public static final PrefixedChoice DECL_SEP = choice(XML2.PE_REFERENCE,
//			XML2.S);
//	// [46] contentspec ::= 'EMPTY' | 'ANY' | Mixed | children
//	public static final NotIncluding CONTENTSPEC = notIncluding(
//			CharConstants.ANY_CHAR, CharConstants.GT);
//	// [45] elementdecl ::= '<!ELEMENT' S Name S contentspec S? '>'
//	public static final Surrounded ELEMENTDECL = surrounded(
//			GammaProducer.str("<!ELEMENT"),
//			sequence(XML2.S, XML2.NAME, XML2.S, CONTENTSPEC, XML2.POSSIBLY_S),
//			CharConstants.GT);
//	// [76] NDataDecl ::= S 'NDATA' S Name
//	public static final PrefixedSequence N_DATA_DECL = sequence(XML2.S,
//			GammaProducer.str("NDATA"), XML2.S, XML2.NAME);
//	// [72] PEDecl ::= '<!ENTITY' S '%' S Name S PEDef S? '>'
//	public static final Surrounded PE_DECL = surrounded(
//			GammaProducer.str("<!ENTITY"),
//			sequence(XML2.S, CharConstants.PERCENT_SIGN, XML2.S, XML2.NAME,
//					XML2.S, XML.PE_DEF, XML2.POSSIBLY_S),
//			GammaProducer._char('>'));
//	// [71] GEDecl ::= '<!ENTITY' S Name S EntityDef S? '>'
//	public static final Surrounded GE_DECL = surrounded(
//			GammaProducer.str("<!ENTITY"),
//			sequence(XML2.S, XML2.NAME, XML2.S, XML10.ENTITY_DEF,
//					XML2.POSSIBLY_S), GammaProducer._char('>'));
//	// [70] EntityDecl ::= GEDecl | PEDecl
//	public static final PrefixedChoice ENTITY_DECL = choice(GE_DECL, PE_DECL);
//	// [83] PublicID ::= 'PUBLIC' S PubidLiteral
//	public static final PrefixedSequence PUBLIC_ID = sequence(
//			GammaProducer.str("PUBLIC"), XML2.S, XML.PUBID_LITERAL);
//	// [82] NotationDecl ::= '<!NOTATION' S Name S (ExternalID | PublicID) S?
//	// '>'
//	public static final Surrounded NOTATION_DECL = surrounded(
//			GammaProducer.str("<!NOTATION"),
//			sequence(XML2.S, XML2.NAME, XML2.S,
//					choice(XML.EXTERNAL_ID, PUBLIC_ID), XML2.POSSIBLY_S),
//			GammaProducer._char('>'));
//	// [29] markupdecl ::= elementdecl | AttlistDecl | EntityDecl | NotationDecl
//	// | PI | Comment
//	// [28] doctypedecl ::= '<!DOCTYPE' S Name (S ExternalID)? S? ('[' intSubset
//	// ']' S?)? '>'
//	public static final PrefixedSequence DOCTYPEDECL = sequence(
//			GammaProducer.str("<!DOCTYPE'"),
//			XML2.S,
//			XML2.NAME,
//			zeroOrOne(sequence(XML2.S, XML.EXTERNAL_ID)),
//			XML2.POSSIBLY_S,
//			zeroOrOne(sequence(GammaProducer._char('['), XML10.INT_SUBSET,
//					GammaProducer._char(']'), XML2.POSSIBLY_S)));
//	// [52] AttlistDecl ::= '<!ATTLIST' S Name AttDef* S? '>'
//	public static final Surrounded ATTLIST_DECL = surrounded(
//			GammaProducer.str("<!ATTLIST"),
//			sequence(XML2.S, XML2.NAME, zeroOrMore(XML.ATT_DEF),
//					XML2.POSSIBLY_S), GammaProducer.str(">"));
//	// [55] StringType ::= 'CDATA'
//	public static final Str STRING_TYPE = GammaProducer.str("CDATA");
//	// [58] NotationType ::= 'NOTATION' S '(' S? Name (S? '|' S? Name)* S? ')'
//	public static final PrefixedSequence NOTATION_TYPE = sequence(
//			// 'NOTATION'
//			GammaProducer.str("NOTATION"),
//			XML2.S,
//			// '(' S? Name (S? '|' S? Name)* S? ')'
//			surrounded(
//			// '('
//					GammaProducer._char('('),
//					// S? Name (S? '|' S? Name)* S?
//					sequence(
//							XML2.POSSIBLY_S,
//							XML2.NAME,
//							// (S? '|' S? Name)*
//							zeroOrMore(
//							// S? '|' S? Name
//							sequence(XML2.POSSIBLY_S,
//									// |
//									GammaProducer._char('|'), XML2.POSSIBLY_S,
//									XML2.NAME)), XML2.POSSIBLY_S),
//					// ')'
//					GammaProducer._char(')')));
//	public static final Surrounded ENUMERATION = surrounded(
//	// '('
//			GammaProducer._char('('),
//			// S? Nmtoken (S? '|' S? Nmtoken)* S?
//			sequence(
//					XML2.POSSIBLY_S,
//					XML2.NMTOKEN,
//					// (S? '|' S? Nmtoken)*
//					zeroOrMore(
//					// S? '|' S? Nmtoken
//					sequence(XML2.POSSIBLY_S, GammaProducer._char('|'),
//							XML2.POSSIBLY_S, XML2.NMTOKEN)), XML2.POSSIBLY_S),
//			// ')'
//			GammaProducer._char(')'));
//	// [57] EnumeratedType ::= NotationType | Enumeration
//	public static final PrefixedChoice ENUMERATED_TYPE = choice(NOTATION_TYPE,
//			ENUMERATION);
//	// [77] TextDecl ::= '<?xml' VersionInfo? EncodingDecl S? '?>'
//	public static final Surrounded TEXT_DECL = surrounded(
//			GammaProducer.str("<?xml"),
//			sequence(zeroOrOne(VERSION_INFO), XML.ENCODING_DECL,
//					XML2.POSSIBLY_S), GammaProducer.str("?>"));
//
//	// TODO Do we really need this
//	// [54] AttType ::= StringType | TokenizedType | EnumeratedType
//	public static final PrefixedChoice ATT_TYPE = choice(STRING_TYPE,
//			XML.TOKENIZED_TYPE, ENUMERATED_TYPE);
//	// [74] PEDef ::= EntityValue | ExternalID
//	public static final PrefixedChoice PE_DEF = choice(ENTITY_VALUE,
//			XML.EXTERNAL_ID);
//	public static final PrefixedChoice MARKUPDECL = choice(ELEMENTDECL,
//			ATTLIST_DECL, ENTITY_DECL, NOTATION_DECL, XML2.PI, XML2.COMMENT);
//	// [53] AttDef ::= S Name S AttType S DefaultDecl
//	public static final PrefixedSequence ATT_DEF = sequence(XML2.S, XML2.NAME,
//			XML2.S, ATT_TYPE, XML2.S, XML10.DEFAULT_DECL);
//	// [75] ExternalID ::= 'SYSTEM' S SystemLiteral | 'PUBLIC' S PubidLiteral S
//	// SystemLiteral
//	public static final Choice EXTERNAL_ID = choice(
//			sequence(GammaProducer.str("SYSTEM"), XML2.S, SYSTEM_LITERAL),
//			sequence(GammaProducer.str("PUBLIC"), XML2.S, PUBID_LITERAL,
//					XML2.S, SYSTEM_LITERAL));
//	// [56] TokenizedType ::= 'ID'
//	// | 'IDREF'
//	// | 'IDREFS'
//	// | 'ENTITY'
//	// | 'ENTITIES'
//	// | 'NMTOKEN'
//	// | 'NMTOKENS'
//	public static final Production TOKENIZED_TYPE = GammaProducer.choice(
//			GammaProducer.str("ID"), GammaProducer.str("IDREF"),
//			GammaProducer.str("IDREFS"), GammaProducer.str("ENTITY"),
//			GammaProducer.str("ENTITIES"), GammaProducer.str("NMTOKEN"),
//			GammaProducer.str("NMTOKENS"));
//
// }
