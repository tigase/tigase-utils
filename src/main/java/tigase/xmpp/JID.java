/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2008 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 * 
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */

package tigase.xmpp;

import tigase.util.TigaseStringprepException;

/**
 * Created: Dec 28, 2009 10:48:04 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class JID implements Comparable<JID> {

	private BareJID bareJid = null;
	private String resource = null;
	private String to_string = null;

	public JID(String jid) throws TigaseStringprepException {
		String[] parsedJid = BareJID.parseJID(jid);
		bareJid = BareJID.bareJIDInstance(parsedJid[0], parsedJid[1]);
		setResource(parsedJid[2]);
	}

	public JID(String localpart, String domain, String resource)
			throws TigaseStringprepException {
		bareJid = BareJID.bareJIDInstance(localpart, domain);
		setResource(resource);
	}

	public JID(BareJID bareJid, String resource)
			throws TigaseStringprepException {
		this.bareJid = bareJid;
		setResource(resource);
	}

	public void setResource(String resource) throws TigaseStringprepException {
		this.resource = BareJID.stringPrep.resourceprep(resource);
		to_string = BareJID.toString(bareJid, resource);
	}

	public String getResource() {
		return resource;
	}

	public void setBareJID(BareJID bareJid) {
		this.bareJid = bareJid;
		to_string = BareJID.toString(bareJid, resource);
	}

	public BareJID getBareJID() {
		return bareJid;
	}

	public String getLocalpart() {
		return bareJid.getLocalpart();
	}

	public String getDomain() {
		return bareJid.getDomain();
	}

	@Override
	public String toString() {
		return to_string;
	}

	@Override
	public int compareTo(JID o) {
		return to_string.compareTo(o.to_string);
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof JID) {
			JID jid = (JID)b;
			return bareJid.equals(jid.bareJid) &&
					(resource == jid.resource ||
					(resource != null && resource.equals(jid.resource)));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return to_string.hashCode();
	}

}
