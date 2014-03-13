package org.hisrc.jstax.io.impl.tests;

import junit.framework.Assert;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.impl.StringResult;
import org.junit.Test;

public class StringResultTest {

	@Test
	public void testPushChar() {
		final Result result = new StringResult();

		for (int index = 0; index < 2048; index++) {
			result.pushChar((char) index);
		}
		final String resultString = result.toString();
		Assert.assertEquals(2048, resultString.length());
	}

	@Test
	public void testPopChar() {
		final Result result = new StringResult();

		for (int index = 0; index < 2048; index++) {
			result.pushChar((char) index);
		}

		for (int index = 0; index < 1024; index++) {
			result.popChar();
		}

		Assert.assertEquals(1024, result.popString().length());
		Assert.assertNull(result.popString());
	}

}
