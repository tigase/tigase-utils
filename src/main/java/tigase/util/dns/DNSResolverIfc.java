/**
 * Tigase Utils - Utilities module
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
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

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface DNSResolverIfc {

	static final String TIGASE_PRIMARY_ADDRESS = "tigase-primary-address";

	static final String TIGASE_SECONDARY_ADDRESS = "tigase-secondary-address";

	static final Logger log = Logger.getLogger(DNSResolverIfc.class.getName());

	static Random rand = new Random();

	/**
	 * Method provides default host information for the installation. It can be both hostname or IP address.
	 *
	 * @return a default host information.
	 */
	public String getDefaultHost();

	/**
	 * Method provides an array of all local host informations, by default it contains defaultHost.
	 *
	 * @return an array of all local hosts.
	 */
	default String[] getDefaultHosts() {
		return new String[]{getDefaultHost()};
	}

	/**
	 * Method provides alternative host information for the current instance. By default falls back to the default host
	 * information.
	 *
	 * @return alternative host information.
	 */
	default public String getSecondaryHost() {
		return getDefaultHost();
	}

	/**
	 * Resolve IP address for the given <code>hostname</code>
	 *
	 * @param hostname the domain name for which this record is valid
	 *
	 * @return <code>IP address</code> of the machine providing the service.
	 *
	 */
	default public String getHostIP(String hostname) throws UnknownHostException {
		return getHostIPs(hostname)[0];
	}

	/**
	 * Resolve all IP addresses for the given <code>hostname</code>
	 *
	 * @param hostname the domain name for which this record is valid
	 *
	 * @return Array of all <code>IP addresses</code> on which target host provide service.
	 *
	 */
	public String[] getHostIPs(String hostname) throws UnknownHostException;

	/**
	 * Retrieves list of SRV DNS entries for given <code>hostname</code>. Performs lookup for
	 * <code>_xmpp-server._tcp</code> SRV records.
	 *
	 * @param hostname the domain name for which this record is valid
	 *
	 * @return Array of the DNSEntry objects containing SRV DNS records
	 *
	 */
	default public DNSEntry[] getHostSRV_Entries(String hostname) throws UnknownHostException {
		String service = "_xmpp-server._tcp";
		int defPort = 5269;

		return getHostSRV_Entries(hostname, service, defPort);
	}

	/**
	 * Retrieves list of DNS entries for given <code>hostname</code>. Allow specifying particular type of SRV record.
	 *
	 * @param hostname the domain name for which this record is valid
	 * @param service type of SRV records, for example <code>_xmpp-server._tcp</code>
	 * @param defPort default port number in case DNS records is missing one.
	 *
	 * @return Array of the DNSEntry records
	 *
	 */
	default public DNSEntry[] getHostSRV_Entries(String hostname, String service, int defPort)
			throws UnknownHostException {
		String key = service + "." + hostname;

		String result_host = hostname;
		int port = defPort;
		int priority = 0;
		int weight = 0;
		long ttl = 3600 * 1000;
		final Set<DNSEntry> entries = new TreeSet<>();

		try {
			Hashtable<String, String> env = new Hashtable<String, String>(5);

			env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

			DirContext ctx = new InitialDirContext(env);
			Attributes attrs = ctx.getAttributes(service + "." + hostname, new String[]{"SRV"});
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

						entries.add(new DNSEntry(hostname, result_host, ip_addresses, port, ttl, priority, weight));
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
				log.log(Level.FINER, "Problem getting SRV DNS records for domain: " + hostname + ", " + e.getMessage());
			}
		}    // end of try-catch
		if (entries.isEmpty()) {
			String[] ip_address = getHostIPs(result_host);

			entries.add(new DNSEntry(hostname, ip_address, port));
		}

		if (log.isLoggable(Level.FINER)) {
			log.log(Level.FINER, "Resolved DNS for : " + hostname + " to: " + entries);
		}

		return entries.toArray(new DNSEntry[]{});
	}

	/**
	 * Retrieves service DNS entry with highest priority for given <code>hostname</code>. Performs lookup for
	 * <code>_xmpp-server._tcp</code> SRV records.
	 *
	 * @param hostname name to resolve
	 *
	 * @return DNSEntry object containing DNS record with highest priority for given <code>hostname</code>
	 *
	 */
	default public DNSEntry getHostSRV_Entry(String hostname) throws UnknownHostException {
		String service = "_xmpp-server._tcp";
		int defPort = 5269;

		return getHostSRV_Entry(hostname, service, defPort);
	}

	/**
	 * Retrieves list of DNS entries for given <code>hostname</code>. Allow specifying particular type of SRV record.
	 *
	 * @param hostname name to resolve
	 * @param service type of SRV records, for example <code>_xmpp-server._tcp</code>
	 * @param defPort default port number in case DNS records is missing one.
	 *
	 * @return DNSEntry object containing DNS record with highest priority for given <code>hostname</code>
	 *
	 */
	default public DNSEntry getHostSRV_Entry(String hostname, String service, int defPort) throws UnknownHostException {
		DNSEntry[] entries = getHostSRV_Entries(hostname, service, defPort);

		if ((entries == null) || (entries.length == 0)) {
			return null;
		}

		// Let's find the entry with the highest priority
		int priority = Integer.MAX_VALUE;
		DNSEntry result = null;

		// We try to get random entry here, in case there are multiple results and one
		// is consistently broken
		int start = rand.nextInt(entries.length);
		int idx = 0;

		for (int i = 0; i < entries.length; ++i) {
			idx = (i + start) % entries.length;
			if (entries[idx].getPriority() < priority) {
				priority = entries[idx].getPriority();
				result = entries[idx];
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
					new Object[]{start, idx, result});
		}

		return result;
	}

	/**
	 * Returns <code>IP address</code> of the machine providing the service.
	 *
	 * @param hostname the domain name for which this record is valid
	 */
	default public String getHostSRV_IP(String hostname) throws UnknownHostException {
		DNSEntry entry = getHostSRV_Entry(hostname);

		return (entry != null) ? entry.getIp() : null;
	}

}
