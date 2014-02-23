package org.hisrc.jstax.grammar.impl.tests;

import org.hisrc.jstax.grammar.Ch;
import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.StringResult;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.junit.Assert;
import org.junit.Test;

public class ChTest {

	@Test
	public void testCharCheck() {
		final Ch ch = Grammar._char('a');
		final Input input = new StringInput("ab");
		Assert.assertTrue(ch.check(input));
		input.readChar();
		Assert.assertFalse(ch.check(input));
	}

	@Test
	public void testCharRead() {
		final Ch ch = Grammar._char('a');
		final Input input = new StringInput("ab");
		final Result result = new StringResult();
		ch.read(input, result, ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals("a", result.toString());
	}

	@Test
	public void testCharRange() {
		final Ch ch = Grammar.charRange('b', 'y');
		final Input input = new StringInput("ba");
		Assert.assertTrue(ch.check(input));
		input.readChar();
		Assert.assertFalse(ch.check(input));
	}

	@Test
	public void testCharRanges() {
		final Ch ch = Grammar.charRanges(Grammar.charRange('a', 'c'),
				Grammar.charRange('x', 'z'));
		final Input input = new StringInput("xd");
		Assert.assertTrue(ch.check(input));
		input.readChar();
		Assert.assertFalse(ch.check(input));
	}

	@Test
	public void testChars() {
		final Ch ch = Grammar.chars('a', 'b', 'c');
		final Input input = new StringInput("cd");
		Assert.assertTrue(ch.check(input));
		input.readChar();
		Assert.assertFalse(ch.check(input));
	}

	@Test
	public void testNegativeChars() {
		final Ch ch = Grammar.negativeChars("geca");
		final Input input = new StringInput("dc");
		Assert.assertTrue(ch.check(input));
		input.readChar();
		Assert.assertFalse(ch.check(input));
	}

}
