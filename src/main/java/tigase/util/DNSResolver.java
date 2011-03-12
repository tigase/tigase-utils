/*
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2007 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
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

package tigase.util;

//~--- JDK imports ------------------------------------------------------------

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

//~--- classes ----------------------------------------------------------------

/**
 * Describe class DNSResolver here.
 * 
 * 
 * Created: Mon Sep 11 09:59:02 2006
 * 
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class DNSResolver {

	/**
	 * Variable <code>log</code> is a class logger.
	 */
	private static final Logger log = Logger.getLogger(DNSResolver.class.getName());
	private static final String LOCALHOST = "localhost";
	private static final String OPEN_DNS_HIT_NXDOMAIN = "hit-nxdomain.opendns.com";
	private static final long DNS_CACHE_TIME = 1000 * 60;
	private static Map<String, DNSEntry[]> srv_cache = Collections
			.synchronizedMap(new SimpleCache<String, DNSEntry[]>(100, DNS_CACHE_TIME));
	private static Map<String, DNSEntry> ip_cache = Collections
			.synchronizedMap(new SimpleCache<String, DNSEntry>(100, DNS_CACHE_TIME));
	private static String[] localnames = null;
	private static String defaultHostname = null;
	private static String opendns_hit_nxdomain_ip = null;
	private static long resolveDefaultTime = 0;

	// ~--- static initializers --------------------------------------------------

	static {
		long start = System.currentTimeMillis();

		ip_cache.put(LOCALHOST, new DNSEntry(LOCALHOST, "127.0.0.1"));

		try {
			if (!LOCALHOST.equals(InetAddress.getLocalHost().getHostName().toLowerCase())) {
				localnames = new String[2];
				localnames[0] = InetAddress.getLocalHost().getHostName().toLowerCase();
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
					if (addr.isLoopbackAddress() || addr.isAnyLocalAddress()
							|| addr.isLinkLocalAddress() || addr.isSiteLocalAddress()) {
						continue;
					}

					defaultHostname = addr.getHostName().toLowerCase();
				}
			}

			if (defaultHostname == null) {
				defaultHostname = localnames[0];
			}
		} catch (UnknownHostException e) {
			localnames = new String[] { LOCALHOST };
			defaultHostname = LOCALHOST;
			log.severe("Most likely network misconfiguration problem, make sure the localhost "
					+ "name to whichever it is set does resolve to IP address, now using: "
					+ defaultHostname);
		} // end of try-catch

		// OpenDNS workaround, but this may take a while so let's do it in
		// background...
		new Thread("OpenDNS checker") {
			@Override
			public void run() {
				try {
					opendns_hit_nxdomain_ip =
							InetAddress.getByName(OPEN_DNS_HIT_NXDOMAIN).getHostAddress();
				} catch (UnknownHostException e) {
					opendns_hit_nxdomain_ip = null;
				}
			}
		}.start();
		resolveDefaultTime = System.currentTimeMillis() - start;

		if (resolveDefaultTime > 0) {
			log.warning("Resolving default host name took: " + resolveDefaultTime);
		}
	}

	// ~--- get methods ----------------------------------------------------------

	/**
	 * Method description
	 * 
	 * 
	 * @return
	 */
	public static String[] getDefHostNames() {

		// if (extrahosts != null) {
		// String[] hosts = extrahosts.split(",");
		// String[] result = new String[localnames.length + hosts.length];
		// System.arraycopy(localnames, 0, result, 0, localnames.length);
		// System.arraycopy(hosts, 0, result, localnames.length, hosts.length);
		// return result;
		// }
		return ((localnames != null) ? Arrays.copyOf(localnames, localnames.length) : null);
	}

	/**
	 * Method description
	 * 
	 * 
	 * @return
	 */
	public static String getDefaultHostname() {
		return defaultHostname;
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param hostname
	 * 
	 * @return
	 * 
	 * @throws UnknownHostException
	 */
	public static String getHostIP(String hostname) throws UnknownHostException {
		DNSEntry cache_res = ip_cache.get(hostname);

		if (cache_res != null) {
			return cache_res.getIp();
		} // end of if (result != null)

		InetAddress[] all = InetAddress.getAllByName(hostname);
		String ip_address = all[0].getHostAddress();

		if (ip_address.equals(opendns_hit_nxdomain_ip)) {
			throw new UnknownHostException("OpenDNS NXDOMAIN");
		}

		ip_cache.put(hostname, new DNSEntry(hostname, ip_address));

		return ip_address;
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param hostname
	 * 
	 * @return
	 * 
	 * @throws UnknownHostException
	 */
	public static DNSEntry[] getHostSRV_Entries(String hostname)
			throws UnknownHostException {
		DNSEntry[] cache_res = srv_cache.get(hostname);

		if (cache_res != null) {
			return cache_res;
		} // end of if (result != null)

		String result_host = hostname;
		int port = 5269;
		int priority = 0;
		int weight = 0;
		long ttl = 3600 * 1000;
		DNSEntry[] entries = null;

		try {
			Hashtable<String, String> env = new Hashtable<String, String>(5);

			env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

			DirContext ctx = new InitialDirContext(env);
			Attributes attrs =
					ctx.getAttributes("_xmpp-server._tcp." + hostname, new String[] { "SRV" });
			Attribute att = attrs.get("SRV");

			// System.out.println("SRV Attribute: " + att);
			if ((att != null) && (att.size() > 0)) {
				entries = new DNSEntry[att.size()];

				for (int i = 0; i < entries.length; i++) {
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
						port = 5269;
					}

					result_host = dns_resp[3];

					InetAddress[] all = InetAddress.getAllByName(result_host);
					String ip_address = all[0].getHostAddress();

					if (ip_address.equals(opendns_hit_nxdomain_ip)) {
						throw new UnknownHostException("OpenDNS NXDOMAIN");
					}

					entries[i] =
							new DNSEntry(hostname, result_host, ip_address, port, ttl, priority, weight);
				}
			} else {
				log.log(Level.FINER, "Empty SRV DNS records set for domain: {0}", hostname);
			}

			ctx.close();
		} catch (NamingException e) {
			result_host = hostname;

			if (log.isLoggable(Level.FINER)) {
				log.log(Level.FINER, "Problem getting SRV DNS records for domain: " + hostname, e);
			}
		} // end of try-catch

		if (entries == null) {
			InetAddress[] all = InetAddress.getAllByName(result_host);
			String ip_address = all[0].getHostAddress();

			if (ip_address.equals(opendns_hit_nxdomain_ip)) {
				throw new UnknownHostException("OpenDNS NXDOMAIN");
			}

			entries = new DNSEntry[] { new DNSEntry(hostname, ip_address, port) };
		}

		// System.out.println("Adding " + hostname + " to cache DNSEntry: " +
		// entry.toString());
		if (entries != null) {
			srv_cache.put(hostname, entries);
		}

		return entries;
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param hostname
	 * 
	 * @return
	 * 
	 * @throws UnknownHostException
	 */
	public static DNSEntry getHostSRV_Entry(String hostname) throws UnknownHostException {
		DNSEntry[] entries = getHostSRV_Entries(hostname);

		if ((entries == null) || (entries.length == 0)) {
			return null;
		}

		return entries[0];
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param hostname
	 * 
	 * @return
	 * 
	 * @throws UnknownHostException
	 */
	public static String getHostSRV_IP(String hostname) throws UnknownHostException {
		DNSEntry entry = getHostSRV_Entry(hostname);

		return (entry != null) ? entry.getIp() : null;
	}

	// ~--- methods --------------------------------------------------------------

	/**
	 * Describe <code>main</code> method here.
	 * 
	 * @param args
	 *          a <code>String[]</code> value
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		String host = "gmail.com";

		if (args.length > 0) {
			host = args[0];
		}

		System.out.println(host + ": " + Arrays.toString(getHostSRV_Entries(host)));
		System.out.println("Localhost name: " + InetAddress.getLocalHost().getHostName());
		System.out.println("Localhost canonnical name: "
				+ InetAddress.getLocalHost().getCanonicalHostName());
		System.out.println("Is local loopback: "
				+ InetAddress.getLocalHost().isLoopbackAddress());

		for (String hostname : localnames) {
			InetAddress[] all = InetAddress.getAllByName(hostname);

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

		// InetAddress[] all = InetAddress.getAllByName(host);
		// for (InetAddress ia: all) {
		// System.out.println("Host: " + ia.toString());
		// } // end of for (InetAddress ia: all)
		// Hashtable env = new Hashtable();
		// env.put("java.naming.factory.initial",
		// "com.sun.jndi.dns.DnsContextFactory");
		// // env.put("java.naming.provider.url", "dns://10.75.32.10");
		// DirContext ctx = new InitialDirContext(env);
		// Attributes attrs =
		// ctx.getAttributes("_xmpp-server._tcp." + host,
		// new String[] {"SRV", "A"});
		// String id = "SRV";
		// Attribute att = attrs.get(id);
		// if (att == null) {
		// id = "A";
		// att = attrs.get(id);
		// } // end of if (attr == null)
		// System.out.println(id + ": " + att.get(0));
		// System.out.println("Class: " + att.get(0).getClass().getSimpleName());
		// for (NamingEnumeration ae = attrs.getAll(); ae.hasMoreElements(); ) {
		// Attribute attr = (Attribute)ae.next();
		// String attrId = attr.getID();
		// for (Enumeration vals = attr.getAll(); vals.hasMoreElements();
		// System.out.println(attrId + ": " + vals.nextElement()));
		// }
		// ctx.close();
	}
} // DNSResolver

// ~ Formatted in Sun Code Convention

// ~ Formatted by Jindent --- http://www.jindent.com
