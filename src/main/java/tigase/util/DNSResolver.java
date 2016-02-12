/*
 * DNSResolver.java
 *
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2013 "Tigase, Inc." <office@tigase.com>
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

//~--- non-JDK imports --------------------------------------------------------

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import sun.net.util.IPAddressUtil;

//~--- JDK imports ------------------------------------------------------------


/**
 * DNSResolver class for handling DNS names
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @since Mon Sep 11 09:59:02 2006
 *
 * @deprecated {@code tigase.util.DNSResolverFactory.getInstance()} should be used instead
 */
@Deprecated
public class DNSResolver {
	private static String       defaultHostname = null;
	private static final long   DNS_CACHE_TIME  = 1000 * 60;
	private static final String LOCALHOST       = "localhost";
	private static String[]     localnames      = null;

	/**
	 * Variable <code>log</code> is a class logger.
	 */
	private static final Logger log = Logger.getLogger(DNSResolver.class.getName());
	private static final String OPEN_DNS_HIT_NXDOMAIN = "hit-nxdomain.opendns.com";

	/** Field description */
	public static Map<String, DNSEntry[]> srv_cache = Collections.synchronizedMap(
			new SimpleCache<String, DNSEntry[]>(100, DNS_CACHE_TIME));

	/** Field description */
	public static Map<String, DNSEntry> ip_cache = Collections.synchronizedMap(
			new SimpleCache<String, DNSEntry>(100, DNS_CACHE_TIME));
	private static String opendns_hit_nxdomain_ip = null;
	private static long   resolveDefaultTime      = 0;
	private static Random rand                    = new Random();

	//~--- static initializers --------------------------------------------------

	// ~--- static initializers
	// --------------------------------------------------
	static {
		long start = System.currentTimeMillis();

		// Turn the Java DNS Cache off, we are caching ourselves and we want
		// to have a full control over it.
		java.security.Security.setProperty("networkaddress.cache.ttl", "0");
		ip_cache.put(LOCALHOST, new DNSEntry(LOCALHOST, "127.0.0.1"));
		try {
			String newHostName = InetAddress.getLocalHost().getCanonicalHostName()
					.toLowerCase();

			if (newHostName.equalsIgnoreCase(InetAddress.getLocalHost().getHostAddress())) {
				newHostName = InetAddress.getLocalHost().getHostName().toLowerCase();
			}
			if (!LOCALHOST.equals(newHostName)) {
				localnames    = new String[2];
				localnames[0] = newHostName;
				localnames[1] = LOCALHOST;

				InetAddress[] all = InetAddress.getAllByName(localnames[0]);

				ip_cache.put(localnames[0], new DNSEntry(localnames[0], all[0].getHostAddress()
						.toLowerCase()));
			} else {
				localnames = new String[] { LOCALHOST };
			}
			for (String hostname : localnames) {
				InetAddress[] all = InetAddress.getAllByName(hostname);

				for (InetAddress addr : all) {
					if (addr.isLoopbackAddress() || addr.isAnyLocalAddress() || addr
							.isLinkLocalAddress() || addr.isSiteLocalAddress()) {
						continue;
					}
					defaultHostname = addr.getCanonicalHostName().toLowerCase();
					if (defaultHostname.equalsIgnoreCase(addr.getHostAddress())) {
						defaultHostname = addr.getHostName();
					}
				}
			}
			if (defaultHostname == null) {
				defaultHostname = localnames[0];
			}
		} catch (UnknownHostException e) {
			localnames      = new String[] { LOCALHOST };
			defaultHostname = LOCALHOST;
			log.severe(
					"Most likely network misconfiguration problem, make sure the local hostname " +
					"to whichever it is set does resolve to IP address, now using: " +
					defaultHostname);
		}    // end of try-catch

		// OpenDNS workaround, but this may take a while so let's do it in
		// background...
		new Thread("OpenDNS checker") {
			@Override
			public void run() {
				try {
					opendns_hit_nxdomain_ip = InetAddress.getByName(OPEN_DNS_HIT_NXDOMAIN)
							.getHostAddress();
				} catch (UnknownHostException e) {
					opendns_hit_nxdomain_ip = null;
				}
			}
		}.start();
		resolveDefaultTime = System.currentTimeMillis() - start;
		if (resolveDefaultTime > 0) {
			log.log(Level.WARNING, "Resolving default host name: {0} took: {1}",  new Object[] {defaultHostname, resolveDefaultTime});
		}
	}

	//~--- methods --------------------------------------------------------------

	/**
	 * <code>main</code> method outputting various information about hostnames
	 *
	 * @param args a <code>String[]</code> containing domains to query, if none provided
	 * default one will be used
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void main(final String[] args) throws Exception {
		String host = "tigase.im";

		if (args.length > 0) {
			host = args[0];
		}

		String[]   hostIPs     = getHostIPs(host);
		DNSEntry[] dns_entries = getHostSRV_Entries(host);

		System.out.println(host + ":getHostIP: " + getHostIP(host));
		System.out.println(host + ":getHostIPs (" + hostIPs.length + "): " + Arrays.toString(
				hostIPs));
		System.out.println(host + ":getHostSRV_IP: " + getHostSRV_IP(host));
		System.out.println(host + ":getHostSRV_Entries S2S: " + Arrays.toString(dns_entries));
		System.out.println("-------------------");

		InetAddress[] all = InetAddress.getAllByName(host);

		for (InetAddress ia : all) {
			System.out.println("Host:getAllByName: " + ia.toString());
		}    // end of for (InetAddress ia: all)
		System.out.println("-------------------");
		
		Hashtable env = new Hashtable();

		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

		// env.put("java.naming.provider.url", "dns://10.75.32.10");
		DirContext ctx = new InitialDirContext(env);
		Attributes attrs = ctx.getAttributes("_xmpp-server._tcp." + host, new String[] {
				"SRV",
				"A" });
		String    id  = "SRV";
		Attribute att = attrs.get(id);

		if (att == null) {
			id  = "A";
			att = attrs.get(id);
		}    // end of if (attr == null)
		System.out.println(id + ": " + att.get(0));
		System.out.println("Class: " + att.get(0).getClass().getSimpleName());
		for (NamingEnumeration<? extends Attribute> ae = attrs.getAll(); ae
				.hasMoreElements(); ) {
			Attribute attr   = (Attribute) ae.next();
			String    attrId = attr.getID();

			for (Enumeration vals = attr.getAll(); vals.hasMoreElements();
					System.out.println(attrId + ": " + vals.nextElement()));
		}
		ctx.close();
		System.out.println("-------------------");
		for (DNSEntry entry : dns_entries) {
			System.out.println(entry.toString());
		}
		System.out.println("-------------------");
		System.out.println("defaultHostname: " + defaultHostname);
		System.out.println("-------------------");
		System.out.println("Localhost name: " + InetAddress.getLocalHost().getHostName());
		System.out.println("Localhost canonnical name: " + InetAddress.getLocalHost()
				.getCanonicalHostName());
		System.out.println("Is local loopback: " + InetAddress.getLocalHost()
				.isLoopbackAddress());
		for (String hostname : localnames) {
			all = InetAddress.getAllByName(hostname);
			for (InetAddress addr : all) {
				System.out.println("  ------   ");
				System.out.println("Host name: " + addr.getHostName());
				System.out.println("Host getCanonicalHostName(): " + addr.getCanonicalHostName());
				System.out.println("Host getHostAddress(): " + addr.getHostAddress());
				System.out.println("Is isLoopbackAddress()  : " + addr.isLoopbackAddress());
				System.out.println("Is isAnyLocalAddress()  : " + addr.isAnyLocalAddress());
				System.out.println("Is isLinkLocalAddress() : " + addr.isLinkLocalAddress());
				System.out.println("Is isSiteLocalAddress() : " + addr.isSiteLocalAddress());
			}
		}
	}

	//~--- get methods ----------------------------------------------------------

	/**
	 * Method description
	 *
	 * @return
	 */
	public static String getDefaultHostname() {
		return defaultHostname;
	}

	/**
	 * Method description
	 *
	 * @return
	 */
	public static String[] getDefHostNames() {
		return ((localnames != null)
				? Arrays.copyOf(localnames, localnames.length)
				: null);
	}

	/**
	 * Resolve IP address for the given <code>hostname</code>
	 *
	 * @param hostname the domain name for which this record is valid
	 * @return <code>IP address</code> of the machine providing the service.
	 * @throws UnknownHostException
	 */
	public static String getHostIP(String hostname) throws UnknownHostException {
		return getHostIPs(hostname)[0];
	}

	/**
	 * Resolve all IP addresses for the given <code>hostname</code>
	 *
	 * @param hostname the domain name for which this record is valid
	 * @return Array of all <code>IP addresses</code> on which target host provide service.
	 * @throws UnknownHostException
	 */
	public static String[] getHostIPs(String hostname) throws UnknownHostException {
		DNSEntry cache_res = ip_cache.get(hostname);

		if (cache_res != null) {
			return cache_res.getIps();
		}    // end of if (result != null)

		InetAddress[] all          = InetAddress.getAllByName(hostname);
		String[]      ip_addresses = new String[all.length];

		for (int j = 0; j < all.length; j++) {
			ip_addresses[j] = all[j].getHostAddress();
			if (ip_addresses[j].equals(opendns_hit_nxdomain_ip)) {
				throw new UnknownHostException("OpenDNS NXDOMAIN");
			}
		}
		ip_cache.put(hostname, new DNSEntry(hostname, ip_addresses));

		return ip_addresses;
	}

	/**
	 * Retrieves list of SRV DNS entries for given <code>hostname</code>. Performs lookup
	 * for <code>_xmpp-server._tcp</code> SRV records.
	 *
	 * @param hostname the domain name for which this record is valid
	 * @return Array of the DNSEntry objects containing SRV DNS records
	 * @throws UnknownHostException
	 */
	public static DNSEntry[] getHostSRV_Entries(String hostname)
					throws UnknownHostException {
		String service = "_xmpp-server._tcp";
		int    defPort = 5269;

		return getHostSRV_Entries(hostname, service, defPort);
	}

	/**
	 * Retrieves list of DNS entries for given <code>hostname</code>. Allow specifying
	 * particular type of SRV record.
	 *
	 * @param hostname the domain name for which this record is valid
	 * @param service type of SRV records, for example <code>_xmpp-server._tcp</code>
	 * @param defPort default port number in case DNS records is missing one.
	 * @return Array of the DNSEntry records
	 * @throws UnknownHostException
	 */
	public static DNSEntry[] getHostSRV_Entries(String hostname, String service,
			int defPort)
					throws UnknownHostException {
		String key = service + "." + hostname;
		DNSEntry[] cache_res = srv_cache.get(key);

		if (cache_res != null) {
			return cache_res;
		}    // end of if (result != null)

		String                    result_host = hostname;
		int                       port        = defPort;
		int                       priority    = 0;
		int                       weight      = 0;
		long                      ttl         = 3600 * 1000;
		final ArrayList<DNSEntry> entries     = new ArrayList<DNSEntry>();

		try {
			Hashtable<String, String> env = new Hashtable<String, String>(5);

			env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

			DirContext ctx = new InitialDirContext(env);
			Attributes attrs = ctx.getAttributes(service + "." + hostname, new String[] {
					"SRV" });
			Attribute att = attrs.get("SRV");

			// System.out.println("SRV Attribute: " + att);
			if ((att != null) && (att.size() > 0)) {
				for (int i = 0; i < att.size(); i++) {
					String[] dns_resp = att.get(i).toString().split(" ");

					try {
						priority = Integer.parseInt(dns_resp[0]);
					} catch (Exception e) {
						priority = 0;
					}
					try {
						weight = Integer.parseInt(dns_resp[1]);
					} catch (Exception e) {
						weight = 0;
					}
					try {
						port = Integer.parseInt(dns_resp[2]);
					} catch (Exception e) {
						port = defPort;
					}
					result_host = dns_resp[3];
					try {

						// Jajcus is right here. If there is any problem with one of the SVR
						// host entries then none of the rest would be even considered.
						String[] ip_addresses = getHostIPs(result_host);

						entries.add(new DNSEntry(hostname, result_host, ip_addresses, port, ttl,
								priority, weight));
					} catch (Exception e) {

						// There is no more processing anyway but for the sake of clarity
						// and in case some more code is added in the future we call
						// continue here
						continue;
					}
				}
			} else {
				log.log(Level.FINER, "Empty SRV DNS records set for domain: {0}", hostname);
			}
			ctx.close();
		} catch (NamingException e) {
			result_host = hostname;
			if (log.isLoggable(Level.FINER)) {
				log.log(Level.FINER, "Problem getting SRV DNS records for domain: " + hostname,
						e);
			}
		}    // end of try-catch
		if (entries.isEmpty()) {
			String ip_address = getHostIP(result_host);

			entries.add(new DNSEntry(hostname, ip_address, port));
		}

		DNSEntry[] result = entries.toArray(new DNSEntry[] {});

		if (result != null) {
			srv_cache.put(key, result);
		}

		return result;
	}

	/**
	 * Retrieves service DNS entry with highest priority for given <code>hostname</code>.
	 * Performs lookup for <code>_xmpp-server._tcp</code> SRV records.
	 *
	 * @param hostname name to resolve
	 * @return DNSEntry object containing DNS record with highest priority for given
	 * <code>hostname</code>
	 * @throws UnknownHostException
	 */
	public static DNSEntry getHostSRV_Entry(String hostname) throws UnknownHostException {
		String service = "_xmpp-server._tcp";
		int    defPort = 5269;

		return getHostSRV_Entry(hostname, service, defPort);
	}

	/**
	 * Retrieves list of DNS entries for given <code>hostname</code>. Allow specifying
	 * particular type of SRV record.
	 * @param hostname name to resolve
	 * @param service type of SRV records, for example <code>_xmpp-server._tcp</code>
	 * @param defPort default port number in case DNS records is missing one.
	 * @return DNSEntry object containing DNS record with highest priority for given
	 * <code>hostname</code>
	 *
	 * @throws UnknownHostException
	 */
	public static DNSEntry getHostSRV_Entry(String hostname, String service, int defPort)
					throws UnknownHostException {
		DNSEntry[] entries = getHostSRV_Entries(hostname, service, defPort);

		if ((entries == null) || (entries.length == 0)) {
			return null;
		}

		// Let's find the entry with the highest priority
		int      priority = Integer.MAX_VALUE;
		DNSEntry result   = null;

		// We try to get random entry here, in case there are multiple results and one
		// is consistently broken
		int start = rand.nextInt(entries.length);
		int idx   = 0;

		for (int i = 0; i < entries.length; ++i) {
			idx = (i + start) % entries.length;
			if (entries[idx].getPriority() < priority) {
				priority = entries[idx].getPriority();
				result   = entries[idx];
			}
		}
		if (result == null) {

			// Hm this should not happen, mistake in the algorithm?
			result = entries[0];
			log.log(Level.WARNING, "No result?? should not happen, an error in the code: {0}",
					Arrays.toString(entries));
		}
		if (log.isLoggable(Level.FINEST)) {
			log.log(Level.FINEST, "Start idx: {0}, last idx: {1}, selected DNSEntry: {2}",
					new Object[] { start,
					idx, result });
		}

		return result;
	}

	/**
	 * Returns <code>IP address</code> of the machine providing the service.
	 *
	 * @param hostname the domain name for which this record is valid
	 * @return<code>IP address</code> of the machine providing the service.
	 *
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostSRV_IP(String hostname) throws UnknownHostException {
		DNSEntry entry = getHostSRV_Entry(hostname);

		return (entry != null)
				? entry.getIp()
				: null;
	}
}


//~ Formatted in Tigase Code Convention on 14/02/07
