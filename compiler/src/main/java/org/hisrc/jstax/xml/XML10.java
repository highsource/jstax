package org.hisrc.jstax.xml;

import static org.hisrc.jstax.grammar.Grammar._char;
import static org.hisrc.jstax.grammar.Grammar.choice;
import static org.hisrc.jstax.grammar.Grammar.excluding;
import static org.hisrc.jstax.grammar.Grammar.sequence;
import static org.hisrc.jstax.grammar.Grammar.sequence1;
import static org.hisrc.jstax.grammar.Grammar.str;
import static org.hisrc.jstax.grammar.Grammar.zeroOrMore;
import static org.hisrc.jstax.grammar.Grammar.zeroOrOne;

import org.hisrc.jstax.grammar.Choice;
import org.hisrc.jstax.grammar.Excluding;
import org.hisrc.jstax.grammar.Sequence;
import org.hisrc.jstax.grammar.ZeroOrMore;

public class XML10 {

	// [17] PITarget ::= Name - (('X' | 'x') ('M' | 'm') ('L' | 'l'))
	public static final Excluding PI_TARGET = excluding(
			XML.NAME,
			sequence1(choice(_char('X'), _char('x')),
					choice(_char('M'), _char('m')),
					choice(_char('L'), _char('l'))));

	// [22] prolog ::= XMLDecl? Misc* (doctypedecl Misc*)?

	// [28] doctypedecl ::= '<!DOCTYPE' S Name (S ExternalID)? S? ('[' intSubset
	// ']' S?)? '>'
	// [28b] intSubset ::= (markupdecl | DeclSep)*

	// [51] Mixed ::= '(' S? '#PCDATA' (S? '|' S? Name)* S? ')*'
	// | '(' S? '#PCDATA' S? ')'

	// [47] children ::= (choice | seq) ('?' | '*' | '+')?
	// [48] cp ::= (Name | choice | seq) ('?' | '*' | '+')?
	// [49] choice ::= '(' S? cp ( S? '|' S? cp )+ S? ')'
	// [50] seq ::= '(' S? cp ( S? ',' S? cp )* S? ')'

	// [52] AttlistDecl ::= '<!ATTLIST' S Name AttDef* S? '>'

	// [59] Enumeration ::= '(' S? Nmtoken (S? '|' S? Nmtoken)* S? ')'

	// [60] DefaultDecl ::= '#REQUIRED' | '#IMPLIED'| (('#FIXED' S)? AttValue)
	public static final Choice DEFAULT_DECL = choice(str("#REQUIRED"),
			str("#IMPLIED"),
			sequence(zeroOrOne(sequence(str("#FIXED"), XML.S)), XML.ATT_VALUE));

	// [73] EntityDef ::= EntityValue | (ExternalID NDataDecl?)
	public static final Choice ENTITY_DEF = choice(XML.ENTITY_VALUE,
			sequence(XML.EXTERNAL_ID, zeroOrOne(XML.N_DATA_DECL)));

	// [28b] intSubset ::= (markupdecl | DeclSep)*
	public static final ZeroOrMore INT_SUBSET = zeroOrMore(choice(
			XML.MARKUPDECL, XML.DECL_SEP));

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

	// [43] content ::= CharData? ((element | Reference | CDSect | PI | Comment)
	// CharData?)*
	public static final Sequence CONTENT = sequence(
			zeroOrOne(XML.CHAR_DATA),
			zeroOrMore(sequence(

			choice(
			// TODO ELEMENT
			null, XML.REFERENCE, XML.CD_SECT, XML.PI, XML.COMMENT),
					zeroOrOne(XML.CHAR_DATA))));

	// [22] prolog ::= XMLDecl? Misc* (doctypedecl Misc*)?
	public static final Sequence PROLOG = sequence(zeroOrOne(XML.XML_DECL),
			zeroOrMore(XML.MISC),
			zeroOrOne(sequence(XML.DOCTYPEDECL, zeroOrMore(XML.MISC))));

	// [1] document ::= prolog element Misc*
	public static final Sequence DOCUMENT = sequence(PROLOG, XML.ELEMENT,
			zeroOrMore(XML.MISC));

}