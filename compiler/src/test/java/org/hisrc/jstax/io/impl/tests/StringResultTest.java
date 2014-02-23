package org.hisrc.jstax.io.impl.tests;

import junit.framework.Assert;

import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.impl.StringResult;
import org.junit.Test;

public class StringResultTest {

	@Test
	public void testPushChar() {
		final Result result = new StringResult();

		for (int index = 0; index < 2049; index++) {
			result.pushChar((char) index);
		}
		final String resultString = result.toString();
		Assert.assertEquals(2049, resultString.length());
	}

}
