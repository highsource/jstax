package org.hisrc.jstax.io.impl.tests;

import org.hisrc.jstax.io.CharConstants;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.impl.StringInput;
import org.junit.Assert;
import org.junit.Test;

public class StringInputTest {

	@Test
	public void test() {
		final char[] chars = new char[] {
				//
				'a',

				CharConstants.CR,
				//
				'b', '2', CharConstants.LF,
				//
				'c', '2', '3', CharConstants.CR, CharConstants.LF,
				//
				'd', '2', '3', '4', CharConstants.LF, CharConstants.CR,
				//
				'e', '2', '3', '4', '5' };
		final String str = new String(chars);
		System.out.println(str);
		final Input si = new StringInput(str);
		char ch;
		while ((ch = si.readChar()) != CharConstants.EOF) {
			System.out.println(si.getLocator().getLineNumber() + ":"
					+ si.getLocator().getColumnNumber() + "[" + ch + "]");
		}
		Assert.assertEquals(6, si.getLocator().getLineNumber());
		Assert.assertEquals(5, si.getLocator().getColumnNumber());
	}
}
