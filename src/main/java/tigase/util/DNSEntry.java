/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2012 "Artur Hefczyc" <artur.hefczyc@tigase.org>
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
 *
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */

package tigase.util;

//~--- classes ----------------------------------------------------------------

/**
 * Created: Dec 19, 2009 10:29:23 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class DNSEntry {
	private String dnsResultHost = null;
	private String hostname = null;
	private String ip = null;
	private int port = 5269;
	private int priority = 0;
	private long ttl = 3600 * 1000;
	private int weight = 0;

	//~--- constructors ---------------------------------------------------------

	/**
	 * Constructs ...
	 *
	 *
	 * @param hostname
	 * @param ip
	 */
	public DNSEntry(String hostname, String ip) {
		this.hostname = hostname;
		this.ip = ip;
	}

	/**
	 * Constructs ...
	 *
	 *
	 * @param hostname
	 * @param port
	 * @param ip
	 */
	public DNSEntry(String hostname, String ip, int port) {
		this.hostname = hostname;
		this.port = port;
		this.ip = ip;
	}

	/**
	 *
	 * @param hostname
	 * @param dnsResultHost
	 * @param ip
	 * @param port
	 * @param ttl
	 * @param priority
	 * @param weight
	 */
	public DNSEntry(String hostname, String dnsResultHost, String ip, int port, long ttl,
			int priority, int weight) {
		this.hostname = hostname;
		this.dnsResultHost = dnsResultHost;
		this.ip = ip;
		this.port = port;
		this.ttl = ttl;
		this.priority = priority;
		this.weight = weight;
	}

	//~--- get methods ----------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public String getDnsResultHost() {
		return dnsResultHost;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public long getTtl() {
		return ttl;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public int getWeight() {
		return weight;
	}

	//~--- methods --------------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "hostname: " + dnsResultHost + ", port: " + port + ", ip: " + ip + ", priority: " + priority
				+ ", weight: " + weight + ", ttl: " + (ttl / 1000);
	}
}


//~ Formatted in Sun Code Convention


//~ Formatted by Jindent --- http://www.jindent.com
