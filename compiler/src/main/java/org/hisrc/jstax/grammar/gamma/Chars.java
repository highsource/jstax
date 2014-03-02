package org.hisrc.jstax.grammar.gamma;

import java.util.List;


/**
 * Models one of the given chars.
 * 
 * WhitespaceChar ::= #x20 | #x9 | #xD | #xA
 * 
 * @author Aleksei Valikov
 * 
 */
public interface Chars extends CharRanges {

	public List<Char> getChars();

}
