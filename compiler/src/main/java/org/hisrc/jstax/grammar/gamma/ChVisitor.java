package org.hisrc.jstax.grammar.gamma;

public interface ChVisitor<R> {

	public R visitCh(Char _char);

	public R visitCh(CharRange charRange);

	public R visitCh(CharRanges charRanges);

	public R visitCh(Chars chars);

	public R visitCh(NegativeChars negativeChars);
}
