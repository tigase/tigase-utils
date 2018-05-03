/*
 * JIDTest.java
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

import junit.framework.TestCase;
import tigase.util.stringprep.TigaseStringprepException;
import tigase.xmpp.jid.BareJID;
import tigase.xmpp.jid.JID;

/**
 * @author Wojciech Kapcia <wojciech.kapcia@tigase.org>
 */
public class JIDTest
		extends TestCase {

	public void testGetBareJid() throws TigaseStringprepException {
		JID jid = JID.jidInstance("a@b/c");
		assertEquals(BareJID.bareJIDInstance("a", "b"), jid.getBareJID());
	}

	public void testGetDomain() throws TigaseStringprepException {
		JID jid = JID.jidInstance("a@b/c");
		assertEquals("b", jid.getDomain());
	}

	public void testGetLocalpart() throws TigaseStringprepException {
		JID jid = JID.jidInstance("a@b/c");
		assertEquals("a", jid.getLocalpart());
	}

	public void testGetResource() throws TigaseStringprepException {
		JID jid = JID.jidInstance("a@b/c");
		assertEquals("c", jid.getResource());
	}

	public void testJIDInstance() throws TigaseStringprepException {
//		assertEquals(JID.jidInstance("a@b"), JID.jidInstance("a@b"));
//		assertEquals(JID.jidInstance("a@b"), JID.jidInstance("a", "b"));
//		assertEquals(JID.jidInstance("a", "b"), JID.jidInstance("a", "b"));
//		assertEquals(JID.jidInstance("a@b/c"), JID.jidInstance("a@b/c"));
//		assertEquals(JID.jidInstance("a", "b", "c"), JID.jidInstance("a@b/c"));

		assertEquals(JID.jidInstance("a@b/C"), JID.jidInstance("A@B/C"));
		assertTrue(JID.jidInstance("A@b/c").equals(JID.jidInstance("a@b/c")));
		assertFalse(JID.jidInstance("a@b/C").equals(JID.jidInstance("a@b/c")));
		assertEquals(JID.jidInstance("a@b/C").hashCode(), JID.jidInstance("A@B/C").hashCode());

		assertFalse(JID.jidInstance("a@b").equals(JID.jidInstance("a@b/c")));
		assertFalse(JID.jidInstance("a@b/C").hashCode() == JID.jidInstance("a@b/c").hashCode());
	}

	public void testPercentJids() throws TigaseStringprepException {
		JID jid = JID.jidInstance("-101100311719181%chat.facebook.com@domain.com");

		assertEquals("domain.com", jid.getDomain());
		assertEquals("-101100311719181%chat.facebook.com", jid.getLocalpart());
		assertNull(jid.getResource());
		assertEquals("-101100311719181%chat.facebook.com@domain.com", jid.toString());
	}

	public void testToString() throws TigaseStringprepException {
		JID jid = JID.jidInstance("a@b");
		assertEquals("a@b", jid.toString());

		jid = JID.jidInstance("a@b/c");
		assertEquals("a@b/c", jid.toString());
		assertNotSame("A@b/c", jid.toString());
		assertNotSame("a@B/c", jid.toString());
	}
	
	public void testEquals() throws TigaseStringprepException {
		JID jid1 = JID.jidInstance("a@b/t");
		JID jid2 = JID.jidInstance("A@B/t");
		assertTrue(jid1.equals(jid2));
		assertFalse(jid1.equals(null));
	}
}