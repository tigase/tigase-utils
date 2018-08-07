/*
 * DNSResolverDefault.java
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

package tigase.util.dns;

import tigase.util.cache.SimpleCache;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DNSResolver class for handling DNS names
 */
public class DNSResolverDefault
		implements DNSResolverIfc {

	private static final long DNS_CACHE_TIME = 1000 * 60;
	private static final String LOCALHOST = "localhost";
	private static final Logger log = Logger.getLogger(DNSResolverDefault.class.getName());
	private static final String OPEN_DNS_HIT_NXDOMAIN = "hit-nxdomain.opendns.com";
	public static Map<String, DNSEntry> ip_cache = Collections.synchronizedMap(
			new SimpleCache<String, DNSEntry>(100, DNS_CACHE_TIME));
	public static Map<String, DNSEntry[]> srv_cache = Collections.synchronizedMap(
			new SimpleCache<String, DNSEntry[]>(100, DNS_CACHE_TIME));
	private static String defaultHost = null;
	private static String[] localnames = null;
	private static String opendns_hit_nxdomain_ip = null;
	private static String primaryHost = null;
	private static long resolveDefaultTime = 0;
	private static String secondaryHost = null;

	protected static boolean isHostValid(String host) {
		try {
			if (host != null && !host.trim().isEmpty()) {
				InetAddress.getByName(host);
				return true;
			}
		} catch (UnknownHostException ex) {
			log.log(Level.SEVERE, "Not possible to resolve " + host + ", using fallback address...");
		}
		return false;
	}

	/**
	 * <code>main</code> method outputting various information about hostnames
	 *
	 * @param args a <code>String[]</code> containing domains to query, if none provided default one will be used
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void main(final String[] args) throws Exception {
		String host = "tigase.im";

		if (args.length > 0) {
			host = args[0];
		}

		DNSResolverDefault dnsResolver = new DNSResolverDefault();

		String[] hostIPs = dnsResolver.getHostIPs(host);
		DNSEntry[] dns_entries = dnsResolver.getHostSRV_Entries(host);

		System.out.println(host + ":getHostIP: " + dnsResolver.getHostIP(host));
		System.out.println(host + ":getHostIPs (" + hostIPs.length + "): " + Arrays.toString(hostIPs));
		System.out.println(host + ":getHostSRV_IP: " + dnsResolver.getHostSRV_IP(host));
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
		Attributes attrs = ctx.getAttributes("_xmpp-server._tcp." + host, new String[]{"SRV", "A"});
		String id = "SRV";
		Attribute att = attrs.get(id);

		if (att == null) {
			id = "A";
			att = attrs.get(id);
		}    // end of if (attr == null)
		System.out.println(id + ": " + att.get(0));
		System.out.println("Class: " + att.get(0).getClass().getSimpleName());
		for (NamingEnumeration<? extends Attribute> ae = attrs.getAll(); ae.hasMoreElements(); ) {
			Attribute attr = (Attribute) ae.next();
			String attrId = attr.getID();

			for (Enumeration vals = attr.getAll();
				 vals.hasMoreElements();
				 System.out.println(attrId + ": " + vals.nextElement())) {
				;
			}
		}
		ctx.close();
		System.out.println("-------------------");
		for (DNSEntry entry : dns_entries) {
			System.out.println(entry.toString());
		}
		System.out.println("-------------------");
		System.out.println("defaultHostname: " + defaultHost);
		System.out.println("-------------------");
		System.out.println("Localhost name: " + InetAddress.getLocalHost().getHostName());
		System.out.println("Localhost canonnical name: " + InetAddress.getLocalHost().getCanonicalHostName());
		System.out.println("Is local loopback: " + InetAddress.getLocalHost().isLoopbackAddress());
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

	protected DNSResolverDefault() {

		long start = System.currentTimeMillis();

		// Turn the Java DNS Cache off, we are caching ourselves and we want
		// to have a full control over it.
		java.security.Security.setProperty("networkaddress.cache.ttl", "0");
		ip_cache.put(LOCALHOST, new DNSEntry(LOCALHOST, "127.0.0.1"));
		setPrimaryHost(System.getProperty(TIGASE_PRIMARY_ADDRESS));
		setSecondaryHost(System.getProperty(TIGASE_SECONDARY_ADDRESS));

		try {
			String newHostName = InetAddress.getLocalHost().getCanonicalHostName().toLowerCase();

			if (newHostName.equalsIgnoreCase(InetAddress.getLocalHost().getHostAddress())) {
				newHostName = InetAddress.getLocalHost().getHostName().toLowerCase();
			}
			if (!LOCALHOST.equals(newHostName)) {
				localnames = new String[2];
				localnames[0] = newHostName;
				localnames[1] = LOCALHOST;

				InetAddress[] all = InetAddress.getAllByName(localnames[0]);

				ip_cache.put(localnames[0], new DNSEntry(localnames[0], all[0].getHostAddress().toLowerCase()));
			} else {
				localnames = new String[]{LOCALHOST};
			}
			for (String hostname : localnames) {
				InetAddress[] all = InetAddress.getAllByName(hostname);

				for (InetAddress addr : all) {
					if (addr.isLoopbackAddress() || addr.isAnyLocalAddress() || addr.isLinkLocalAddress() ||
							addr.isSiteLocalAddress()) {
						continue;
					}
					defaultHost = addr.getCanonicalHostName().toLowerCase();
					if (defaultHost.equalsIgnoreCase(addr.getHostAddress())) {
						defaultHost = addr.getHostName();
					}
				}
			}
			if (defaultHost == null) {
				defaultHost = localnames[0];
			}

		} catch (UnknownHostException e) {
			localnames = new String[]{LOCALHOST};
			defaultHost = LOCALHOST;
			log.log(Level.SEVERE,
					"Retrieval of default hostnames failed! Most likely network misconfiguration problem," +
							"make sure the local hostname to whichever it is set does resolve to IP address," +
							"fallback to: " + defaultHost, e);
		}

		// OpenDNS workaround, but this may take a while so let's do it in
		// background...
		new Thread("OpenDNS checker") {
			@Override
			public void run() {
				try {
					opendns_hit_nxdomain_ip = InetAddress.getByName(OPEN_DNS_HIT_NXDOMAIN).getHostAddress();
				} catch (UnknownHostException e) {
					opendns_hit_nxdomain_ip = null;
				}
			}
		}.start();
		resolveDefaultTime = System.currentTimeMillis() - start;
		if (resolveDefaultTime > 0) {
			log.log(Level.WARNING, "Resolving default host name: {0} took: {1}",
					new Object[]{defaultHost, resolveDefaultTime});
		}

	}

	@Override
	public String[] getDefaultHosts() {
		return ((localnames != null) ? Arrays.copyOf(localnames, localnames.length) : null);
	}

	@Override
	public String getDefaultHost() {
		if (primaryHost == null) {
			return defaultHost;
		} else {
			return primaryHost;
		}
	}

	public String getPrimaryHost() {
		return primaryHost;
	}

	public void setPrimaryHost(String tigasePrimaryHost) {
		if (isHostValid(tigasePrimaryHost)) {
			primaryHost = tigasePrimaryHost;
			log.log(Level.WARNING, "Explicit configuring default hostname: {0}", new Object[]{getDefaultHost()});
		} else {
			primaryHost = defaultHost;
		}
	}

	@Override
	public String getSecondaryHost() {
		if (secondaryHost == null) {
			return getDefaultHost();
		} else {
			return secondaryHost;
		}
	}

	public void setSecondaryHost(String tigaseSecondaryHost) {
		if (isHostValid(tigaseSecondaryHost)) {
			secondaryHost = tigaseSecondaryHost;
		} else {
			secondaryHost = null;
		}
	}

	/**
	 * Resolve all IP addresses for the given <code>hostname</code>
	 *
	 * @param hostname the domain name for which this record is valid
	 *
	 * @return Array of all <code>IP addresses</code> on which target host provide service.
	 *
	 * @throws UnknownHostException
	 */
	@Override
	public String[] getHostIPs(String hostname) throws UnknownHostException {
		DNSEntry cache_res = ip_cache.get(hostname);

		if (cache_res != null) {
			return cache_res.getIps();
		}    // end of if (result != null)

		InetAddress[] all = InetAddress.getAllByName(hostname);
		String[] ip_addresses = new String[all.length];

		for (int j = 0; j < all.length; j++) {
			ip_addresses[j] = all[j].getHostAddress();
			if (ip_addresses[j].equals(opendns_hit_nxdomain_ip)) {
				throw new UnknownHostException("OpenDNS NXDOMAIN");
			}
		}
		ip_cache.put(hostname, new DNSEntry(hostname, ip_addresses));

		return ip_addresses;
	}

	@Override
	public DNSEntry[] getHostSRV_Entries(String hostname, String service, int defPort) throws UnknownHostException {
		String key = service + "." + hostname;
		DNSEntry[] cache_res = srv_cache.get(key);

		if (cache_res != null) {
			return cache_res;
		}

		cache_res = DNSResolverIfc.super.getHostSRV_Entries(hostname, service, defPort);

		if (cache_res != null) {
			srv_cache.put(key, cache_res);
		}

		return cache_res;
	}
}
