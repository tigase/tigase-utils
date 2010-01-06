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
import tigase.util.XMPPStringPrepFactory;
import tigase.util.XMPPStringPrepIfc;

/**
 * Created: Dec 28, 2009 10:47:51 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class BareJID implements Comparable<BareJID> {

	protected static XMPPStringPrepIfc stringPrep =
			XMPPStringPrepFactory.getDefaultXMPPStringPrep();

	public static BareJID bareJIDInstance(String jid) throws TigaseStringprepException {
		String[] parsedJid = parseJID(jid);
		return new BareJID(parsedJid[0], parsedJid[1]);
	}

	public static BareJID bareJIDInstance(String p_localpart, String p_domain)
			throws TigaseStringprepException {
		return new BareJID(p_localpart, p_domain);
	}

	public static String[] parseJID(String jid) {
		String[] result = new String[3];
		// Cut off the resource part first
    int idx = jid.indexOf('/');
		// Resource part:
		result[2] = (idx == -1 ? null : jid.substring(idx+1));
    String id = (idx == -1 ? jid : jid.substring(0, idx));
		// Parse the localpart and the domain name
		idx = id.indexOf('@');
		result[0] = (idx == -1 ? null : id.substring(0, idx));
		result[1] = (idx == -1 ? id : id.substring(idx+1));
		return result;
	}

	public static String toString(String p_localpart, String p_domain) {
    return ((p_localpart != null && p_localpart.length() > 0) ?
			(p_localpart + "@" + p_domain) : p_domain);
	}

	public static String toString(String p_localpart, String p_domain, String p_resource) {
    return ((p_localpart != null && p_localpart.length() > 0) ?
			(p_localpart + "@" + p_domain) : p_domain)
				+ ((p_resource != null && p_resource.length() > 0) ?
					"/" + p_resource : "");
	}

	public static String toString(BareJID bareJid, String p_resource) {
    return bareJid.toString()
				+ ((p_resource != null && p_resource.length() > 0) ?
					"/" + p_resource : "");
	}

	private String localpart = null;
	private String domain = null;
	private String to_string = null;

	private BareJID(String localpart, String domain) throws TigaseStringprepException {
		this.localpart = localpart != null ? stringPrep.nodeprep(localpart) : null;
		this.domain = stringPrep.nameprep(domain).intern();
		this.to_string = toString(this.localpart, this.domain);
	}

	public String getLocalpart() {
		return localpart;
	}

	public String getDomain() {
		return domain;
	}

	@Override
	public String toString() {
		return to_string;
	}

	@Override
	public int compareTo(BareJID o) {
		return to_string.compareTo(o.to_string);
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof BareJID) {
			return this.domain == ((BareJID)b).domain
					&& this.localpart.equals(((BareJID)b).localpart);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return to_string.hashCode();
	}

}
