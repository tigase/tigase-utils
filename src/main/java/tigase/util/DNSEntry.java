/*
 * DNSEntry.java
 *
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2012 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
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
 */



package tigase.util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Arrays;

/**
 * The class defines an instance of a single DNS entry.
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @since Dec 19, 2009 10:29:23 PM
 */
public class DNSEntry {
	private String dnsResultHost = null;
	private String hostname      = null;
	private String[] ips         = null;
	private int port             = 5269;
	private int priority         = 0;
	private long ttl             = 3600 * 1000;
	private int weight           = 0;

	//~--- constructors ---------------------------------------------------------

	/**
	 * Constructs DNS entry with hostname and IP to which it resolves.
	 *
	 *
	 * @param hostname the domain name for which this record is valid
	 * @param ip <code>IP address</code> of the machine providing the service.
	 */
	public DNSEntry(String hostname, String ip) {
		this.hostname = hostname;
		this.ips      = new String[1];
		this.ips[0]   = ip;
	}

	/**
	 * Constructs DNS entry with hostname and multiple IP to which it resolves.
	 *
	 *
	 * @param hostname the domain name for which this record is valid
	 * @param ips Array of all <code>IP addresses</code> on which target host provide service.
	 */
	public DNSEntry(String hostname, String[] ips) {
		this.hostname = hostname;
		this.ips      = ips;
	}

	/**
	 * Constructs DNS entry with hostname, IP to which it resolves and a default port number used for connections.
	 *
	 *
	 * @param hostname the domain name for which this record is valid
	 * @param ip <code>IP address</code> of the machine providing the service.
	 * @param port the TCP or UDP port on which the service is to be found
	 */
	public DNSEntry(String hostname, String ip, int port) {
		this(hostname, ip);
		this.port = port;
	}

	/**
	 * Constructs complete SRV DNS entry.
	 *
	 * @param hostname the domain name for which this record is valid
	 * @param dnsResultHost the canonical hostname of the machine providing the service.
	 * @param ip <code>IP address</code> of the machine providing the service.
	 * @param port the TCP or UDP port on which the service is to be found
	 * @param ttl standard DNS time to live field.
	 * @param priority the priority of the target host, lower value means more preferred.
	 * @param weight relative weight for records with the same priority.
	 */
	public DNSEntry(String hostname, String dnsResultHost, String ip, int port, long ttl,
									int priority, int weight) {
		this(hostname, ip, port);
		this.dnsResultHost = dnsResultHost;
		this.ttl           = ttl;
		this.priority      = priority;
		this.weight        = weight;
	}

	/**
	 * Constructs complete SRV DNS entry.
	 *
	 * @param hostname the domain name for which this record is valid
	 * @param dnsResultHost the canonical hostname of the machine providing the service.
	 * @param ips Array of all <code>IP addresses</code> on which target host provide service.
	 * @param port the TCP or UDP port on which the service is to be found
	 * @param ttl standard DNS time to live field.
	 * @param priority the priority of the target host, lower value means more preferred.
	 * @param weight relative weight for records with the same priority.
	 *
	 */
	public DNSEntry(String hostname, String dnsResultHost, String[] ips, int port,
									long ttl, int priority, int weight) {
		this(hostname, ips);
		this.dnsResultHost = dnsResultHost;
		this.port          = port;
		this.ttl           = ttl;
		this.priority      = priority;
		this.weight        = weight;
	}

	//~--- get methods ----------------------------------------------------------

	/**
	 * Returns the domain name for which this record is valid
	 *
	 *
	 * @return the domain name for which this record is valid
	 */
	public String getDnsResultHost() {
		return dnsResultHost;
	}

	/**
	 * Returns the canonical hostname of the machine providing the service.
	 *
	 * @return the canonical hostname of the machine providing the service.
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Returns <code>IP address</code> of the machine providing the service.
	 *
	 *
	 * @return <code>IP address</code> of the machine providing the service.
	 */
	public String getIp() {
		return ips[0];
	}

	/**
	 * Returns array containing all <code>IP addresses</code> on which service is available (in case hostname resolves to multiple IPs)
	 *
	 *
	 * @return array containing all <code>IP addresses</code> on which service is available
	 */
	public String[] getIps() {
		return ips;
	}

	/**
	 * Returns the TCP or UDP port on which the service is to be found
	 *
	 *
	 * @return the TCP or UDP port on which the service is to be found
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Returns the priority of the target host, lower value means more preferred.
	 *
	 *
	 * @return the priority of the target host, lower value means more preferred.
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Returns standard DNS time to live field.
	 *
	 *
	 * @return standard DNS time to live field.
	 */
	public long getTtl() {
		return ttl;
	}

	/**
	 * Returns relative weight for records with the same priority.
	 *
	 *
	 * @return relative weight for records with the same priority.
	 */
	public int getWeight() {
		return weight;
	}

	//~--- methods --------------------------------------------------------------

	/**
	 * Returns string interpretation of the DNS entry
	 *
	 *
	 * @return string interpretation of the DNS entry
	 */
	@Override
	public String toString() {
		return "hostname: " + dnsResultHost + ", port: " + port + ", ip(s): " +
					 Arrays.toString(ips) + ", priority: " + priority + ", weight: " + weight +
					 ", ttl: " + (ttl / 1000);
	}
}


//~ Formatted in Tigase Code Convention on 13/02/21


//~ Formatted by Jindent --- http://www.jindent.com
