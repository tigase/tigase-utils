/*
 * Tigase Utils - Utilities module
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
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
package tigase.xmpp;

import org.junit.Test;
import tigase.util.stringprep.TigaseStringprepException;
import tigase.xmpp.jid.BareJID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BareJIDTest {

	@Test
	public void testBareJIDInstance() throws TigaseStringprepException {
		assertEquals(BareJID.bareJIDInstance("a@b"), BareJID.bareJIDInstance("a@b"));
		assertEquals(BareJID.bareJIDInstance("a@b"), BareJID.bareJIDInstance("a", "b"));
		assertEquals(BareJID.bareJIDInstance("a", "b"), BareJID.bareJIDInstance("a", "b"));
		assertEquals(BareJID.bareJIDInstance("a@b"), BareJID.bareJIDInstance("a@b/c"));

		assertEquals(BareJID.bareJIDInstance("a@b"), BareJID.bareJIDInstance("A@B"));
		assertEquals(BareJID.bareJIDInstance("a@b").hashCode(), BareJID.bareJIDInstance("A@B").hashCode());
	}

	@Test
	public void testGetDomain() throws TigaseStringprepException {
		BareJID jid = BareJID.bareJIDInstance("a@b");
		assertEquals("b", jid.getDomain());
	}

	@Test
	public void testGetLocalpart() throws TigaseStringprepException {
		BareJID jid = BareJID.bareJIDInstance("a@b");
		assertEquals("a", jid.getLocalpart());
	}

	@Test
	public void testPercentJids() throws TigaseStringprepException {
		BareJID jid = BareJID.bareJIDInstance("-101100311719181%chat.facebook.com@domain.com");

		assertEquals("domain.com", jid.getDomain());
		assertEquals("-101100311719181%chat.facebook.com", jid.getLocalpart());
		assertEquals("-101100311719181%chat.facebook.com@domain.com", jid.toString());
	}

	@Test
	public void testToString() throws TigaseStringprepException {
		BareJID jid = BareJID.bareJIDInstance("a@b");
		assertEquals("a@b", jid.toString());

		jid = BareJID.bareJIDInstance("a@b/c");
		assertEquals("a@b", jid.toString());

		jid = BareJID.bareJIDInstance("A@B/C");
		assertEquals("A@b", jid.toString());

		jid = BareJID.bareJIDInstance("A@B");
		assertEquals("A@b", jid.toString());
	}

	@Test(expected = TigaseStringprepException.class)
	public void testEmpty1StringDomain() throws TigaseStringprepException {
		BareJID.bareJIDInstance("");
	}

	@Test
	public void testNumericStringDomain() throws TigaseStringprepException {
		BareJID.bareJIDInstance("2182181");
	}

	@Test(expected = TigaseStringprepException.class)
	public void testEmpty2StringDomain() throws TigaseStringprepException {
		BareJID.bareJIDInstance(null, "");
	}

	@Test(expected = TigaseStringprepException.class)
	public void testStringEmptyDomain() throws TigaseStringprepException {
		BareJID.bareJIDInstance("localpart@");
	}

	@Test()
	public void testEmpty1StringDomainNS() {
		BareJID jid = BareJID.bareJIDInstanceNS("");
		assertNull("null should be returned for empty domain", jid);
	}

	@Test()
	public void testEmpty2StringDomainNS() {
		BareJID jid = BareJID.bareJIDInstanceNS(null, "");
		assertNull("null should be returned for empty domain", jid);
	}
}