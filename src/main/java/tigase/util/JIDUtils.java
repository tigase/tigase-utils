/*
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2012 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 *
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */
package tigase.util;

import java.net.UnknownHostException;

/**
 * <code>JIDUtils</code> class contains static methods for <em>JIDUtils</em>
 * manipulation.
 *
 * <p>
 * Created: Thu Jan 27 22:53:41 2005
 * </p>
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 * @deprecated replaced by tigase.xmpp.JID
 */
@Deprecated
public abstract class JIDUtils {

  /**
   * Method <code>getNodeID</code> cuts off <em>resource</em> <em>JIDUtils</em> part
   * if exists and returns only node ID.
   *
   * @param jid a <code>String</code> value of <em>JIDUtils</em> to parse.
   * @return a <code>String</code> value of node <em>ID</em> without resource
   * part.
   */
  public static final String getNodeID(final String jid) {
    int idx = jid.indexOf('/');
    return idx == -1 ? jid.toLowerCase() : jid.substring(0, idx).toLowerCase();
  }

  /**
   * Method <code>getNodeID</code> parses given <em>JIDUtils</em> and returns
   * <em>resource</em> part of given <em>JIDUtils</em> or empty string if there
   * was no <em>resource</em> part.
   *
   * @param jid a <code>String</code> value of <em>JIDUtils</em> to parse.
   * @return a <code>String</code> value of node <em>Resource</em> or empty
   * string.
   */
  public static final String getNodeResource(final String jid) {
    int idx = jid.indexOf('/');
    return idx == -1 ? null : jid.substring(idx+1).toLowerCase();
  }

  /**
   * Method <code>getNodeHost</code> parses given <em>JIDUtils</em> and returns node
   * <em>domain</em> part.
   *
   * @param jid a <code>String</code> value of <em>JIDUtils</em> to parse.
   * @return a <code>String</code> value of node <em>domain</em> part.
   */
  public static final String getNodeHost(final String jid) {
    String id = getNodeID(jid);
    int idx = id.lastIndexOf('@');
    return idx == -1 ? id.toLowerCase() : id.substring(idx+1).toLowerCase();
  }

  /**
   * Method <code>getNodeHostIP</code> parses given <em>JIDUtils</em> for node
   * <em>domain</em> part and then tries to resolve host IP address..
   *
   * @param jid a <code>String</code> value of <em>JIDUtils</em> to parse.
	 * @return a <code>String</code> value of node <em>domain</em> IP address.
	 * @throws UnknownHostException
   */
  public static final String getNodeHostIP(final String jid)
		throws UnknownHostException {
    String domain = getNodeHost(jid);
    return DNSResolver.getHostSRV_IP(domain);
  }

  /**
   * Method <code>getNodeNick</code> parses given <em>JIDUtils</em> and returns
   * node nick name or empty string if nick name could not be found.
   *
   * @param jid a <code>String</code> value of <em>JIDUtils</em> to parse.
   * @return a <code>String</code> value of node nick name or empty string.
   */
  public static final String getNodeNick(final String jid) {
    String id = getNodeID(jid);
    int idx = id.lastIndexOf('@');
    return idx == -1 ? null : id.substring(0, idx).toLowerCase();
  }

  /**
   * This is static method to construct user <em>ID</em> from given
   * <em>JIDUtils</em> parts.
   * This is not user session <em>ID</em> (<em>JIDUtils</em>), this is just
   * user <em>ID</em> - <em>JIDUtils</em> without resource part.
   *
   * @param nick a <code>String</code> value of node part of <em>JIDUtils</em>.
	 * @param domain a <code>String</code> value of domain part of <em>JIDUtils</em>.
	 * @return
   */
  public static final String getNodeID(final String nick, final String domain) {
    return ((nick != null && nick.length() > 0) ?
			(nick + "@" + domain).toLowerCase() : domain.toLowerCase());
  }

	/**
	 * <code>getJID</code> method builds valid JIDUtils string from given nick name,
	 * domain and resource. It is aware of the fact that some elements might be
	 * <code>null</code> and then they are not included in JIDUtils. <code>domain</code>
	 * musn't be <code>null</code> however.
	 *
	 * @param nick a <code>String</code> value of JIDUtils's nick name. <code>null</code>
	 * allowed.
	 * @param domain a <code>String</code> value of JIDUtils's domain name.
	 * <code>null</code> <strong>not</strong> allowed.
	 * @param resource a <code>String</code> value of JIDUtils's resource.
	 * @return a <code>String</code> value
	 */
	public static final String getJID(final String nick, final String domain,
		final String resource) {
		StringBuilder sb = new StringBuilder();
		if (nick != null) {
			sb.append(nick).append("@");
		} // end of if (nick != null)
		if (domain == null) {
			throw new NullPointerException("Valid JIDUtils must contain at least domain name.");
		} // end of if (domain == null)
		sb.append(domain);
		if (resource != null) {
			sb.append("/").append(resource);
		} // end of if (resource != null)
		return  sb.toString().toLowerCase();
	}

	/**
	 * <code>checkNickName</code> method checks whether given string is a valid
	 * nick name: not null, not zero length, doesn't contain invalid characters.
	 *
	 * @param nickname a <code>String</code> value of nick name to validate.
	 * @return a <code>String</code> value <code>null</code> if nick name
	 * is correct otherwise text with description of the problem.
	 */
	public static final String checkNickName(final String nickname) {
		if (nickname == null || nickname.trim().length() == 0) {
			return "Nickname empty.";
		} // end of if (new_comp_name == null || new_comp_name.length() == 0)
		if (nickname.contains(" ") || nickname.contains("\t")
				|| nickname.contains("@") || nickname.contains("&")) {
			return "Nickname contains invalid characters.";
		} // end of if (!isValidCompName(new_comp_name))
		return null;
	}

} // JIDUtils
