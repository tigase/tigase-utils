/*
 * TokenTest.java
 *
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */
package tigase.util;

import org.junit.Assert;
import org.junit.Test;
import tigase.util.stringprep.TigaseStringprepException;
import tigase.xmpp.jid.BareJID;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

/**
 * Created by bmalkow on 21.04.2017.
 */
public class TokenTest {

	@Test
	public void testCopy() {
		byte[] buff = new byte[]{(byte) 97, (byte) 98, (byte) 99, (byte) 100};
		String s = Token.copy(buff, 1, 2);

		Assert.assertEquals("bc", s);
	}

	@Test
	public void testTokenCreation() {
		Token t = Token.create(BareJID.bareJIDInstanceNS("a@b.c"), new Date(100), "1234567890");
		Assert.assertEquals("AVlVQmlMbU09ADEwMAAxMjM0NTY3ODkw", t.getEncoded());
		Assert.assertEquals("cVoryT3UDGStynBGwjMp9EpsTnIPKmSGgVv+ngZgxGk=", t.getHash());
	}

	@Test
	public void testTokenDecoding() {
		Token t = Token.parse("AVlVQmlMbU09ADEwMAAxMjM0NTY3ODkw");

		Assert.assertEquals(BareJID.bareJIDInstanceNS("a@b.c"), t.getJid());
		Assert.assertEquals(new Date(100), t.getTimestamp());
		Assert.assertEquals("1234567890", t.getRandom());

		String j = "\u0250\u0287o\u029E\u0250\u026F\u0250\u0283\u0250@\u0287s\u01DD\u0287.\u01DD\u0283d\u026F\u0250x\u01DD.\u026Fo\u0254";
		Date d = new Date();
		String r = String.valueOf((new Random()).nextLong());
		t = Token.parse(Token.create(BareJID.bareJIDInstanceNS(j), d, r).getEncoded());

		Assert.assertEquals(BareJID.bareJIDInstanceNS(j), t.getJid());
		Assert.assertEquals(d, t.getTimestamp());
		Assert.assertEquals(r, t.getRandom());
	}

	@Test
	public void testTokenUnicode1() throws TigaseStringprepException, UnsupportedEncodingException {
		Token t = Token.create(BareJID.bareJIDInstance("\u0239la@b.c"), new Date(100), "1234567890");
		Token t2 = Token.parse(t.getEncoded());
		Assert.assertEquals("JIDs do not match!", "\u0239la@b.c", t.getJid().toString());
		Assert.assertEquals("JIDs do not match!", t.getJid(), t2.getJid());

		Assert.assertEquals("Timestamp do not match!", 100, t.getTimestamp().getTime());
		Assert.assertEquals("Timestamp do not match!", t.getTimestamp().getTime(), t2.getTimestamp().getTime());

		Assert.assertEquals("Random do not match!", "1234567890", t.getRandom());
		Assert.assertEquals("Random do not match!", t.getRandom(), t2.getRandom());
	}

	@Test
	public void testTokenUnicode2() throws TigaseStringprepException, UnsupportedEncodingException {
		Token t = Token.create(BareJID.bareJIDInstance("\u0200la\u1000@b.c"), new Date(100), "1234567890");
		Token t2 = Token.parse(t.getEncoded());
		Assert.assertEquals("JIDs do not match!", "\u0200la\u1000@b.c", t.getJid().toString());
		Assert.assertEquals("JIDs do not match!", t.getJid(), t2.getJid());

		Assert.assertEquals("Timestamp do not match!", 100, t.getTimestamp().getTime());
		Assert.assertEquals("Timestamp do not match!", t.getTimestamp().getTime(), t2.getTimestamp().getTime());

		Assert.assertEquals("Random do not match!", "1234567890", t.getRandom());
		Assert.assertEquals("Random do not match!", t.getRandom(), t2.getRandom());
	}
}