/*
 * BareJIDTest.java
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
package tigase.xmpp;

import tigase.util.stringprep.TigaseStringprepException;

import junit.framework.TestCase;
import tigase.xmpp.jid.BareJID;

public class BareJIDTest extends TestCase {

	public void testBareJIDInstance() throws TigaseStringprepException {
		assertEquals(BareJID.bareJIDInstance("a@b"), BareJID.bareJIDInstance("a@b"));
		assertEquals(BareJID.bareJIDInstance("a@b"), BareJID.bareJIDInstance("a", "b"));
		assertEquals(BareJID.bareJIDInstance("a", "b"), BareJID.bareJIDInstance("a", "b"));
		assertEquals(BareJID.bareJIDInstance("a@b"), BareJID.bareJIDInstance("a@b/c"));

		assertEquals(BareJID.bareJIDInstance("a@b"), BareJID.bareJIDInstance("A@B"));
		assertEquals(BareJID.bareJIDInstance("a@b").hashCode(), BareJID.bareJIDInstance("A@B").hashCode());
	}

	public void testGetDomain() throws TigaseStringprepException {
		BareJID jid = BareJID.bareJIDInstance("a@b");
		assertEquals("b", jid.getDomain());
	}

	public void testGetLocalpart() throws TigaseStringprepException {
		BareJID jid = BareJID.bareJIDInstance("a@b");
		assertEquals("a", jid.getLocalpart());
	}

	public void testPercentJids() throws TigaseStringprepException {
		BareJID jid = BareJID.bareJIDInstance("-101100311719181%chat.facebook.com@domain.com");

		assertEquals("domain.com", jid.getDomain());
		assertEquals("-101100311719181%chat.facebook.com", jid.getLocalpart());
		assertEquals("-101100311719181%chat.facebook.com@domain.com", jid.toString());
	}

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

}