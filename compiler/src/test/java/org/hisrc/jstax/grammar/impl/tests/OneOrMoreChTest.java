package org.hisrc.jstax.grammar.impl.tests;

import org.hisrc.jstax.grammar.Grammar;
import org.hisrc.jstax.grammar.OneOrMoreCh;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.impl.StringInput;
import org.hisrc.jstax.io.impl.StringResult;
import org.hisrc.jstax.io.impl.ThrowingErrorHandler;
import org.junit.Assert;
import org.junit.Test;

public class OneOrMoreChTest {

	@Test
	public void testReads() {
		final OneOrMoreCh abc = Grammar.oneOrMore(Grammar.chars("abc"));
		final Input input = new StringInput("abcacbbacbcacabcba");
		final Result result = new StringResult();
		abc.read(input, result, ThrowingErrorHandler.INSTANCE);
		Assert.assertEquals("abcacbbacbcacabcba", result.toString());

	}
}
