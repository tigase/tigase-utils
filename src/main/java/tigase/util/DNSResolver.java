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


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

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
  private static final Logger log = Logger.getLogger("tigase.util.DNSResolver");

	private static Map<String, Object> cache =
		Collections.synchronizedMap(new SimpleCache<String, Object>(1000));
	private static String[] localnames = null;

	static {
		cache.put("localhost", "127.0.0.1");
		try {
			localnames = new String[2];
			localnames[0] = InetAddress.getLocalHost().getHostName();
			localnames[1] = "localhost";
			InetAddress[] all = InetAddress.getAllByName(localnames[0]);
			cache.put(localnames[0], all[0].getHostAddress());
		} // end of try
		catch (UnknownHostException e) {
			localnames = new String[] {"localhost"};
		} // end of try-catch
	}

	public static String[] getDefHostNames() {
// 		if (extrahosts != null) {
// 			String[] hosts = extrahosts.split(",");
// 			String[] result = new String[localnames.length + hosts.length];
// 			System.arraycopy(localnames, 0, result, 0, localnames.length);
// 			System.arraycopy(hosts, 0, result, localnames.length, hosts.length);
// 			return result;
// 		}
		return (localnames != null ?
			Arrays.copyOf(localnames, localnames.length) : null);
	}

	public static String getHostSRV_IP(final String hostname)
		throws UnknownHostException {

		String cache_res = (String)cache.get(hostname);
		if (cache_res != null) {
			return cache_res;
		} // end of if (result != null)

		String result_host = hostname;

		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put("java.naming.factory.initial",
				"com.sun.jndi.dns.DnsContextFactory");
			DirContext ctx = new InitialDirContext(env);
			Attributes attrs =
				ctx.getAttributes("_xmpp-server._tcp." + hostname, new String[] {"SRV"});
			Attribute att = attrs.get("SRV");
			if (att != null) {
				String res = att.get().toString();
				int idx = res.lastIndexOf(" ");
				result_host = res.substring(idx + 1, res.length());
			} // end of if (att != null)
			ctx.close();
		} // end of try
		catch (NamingException e) {
			result_host = hostname;
		} // end of try-catch

		InetAddress[] all = InetAddress.getAllByName(result_host);

		cache.put(hostname, all[0].getHostAddress());
		return all[0].getHostAddress();
	}

	/**
	 * Describe <code>main</code> method here.
	 *
	 * @param args a <code>String[]</code> value
	 */
	public static void main(final String[] args) throws Exception {

		String host = "gmail.com";
		if (args.length > 0) { host = args[0]; }

		System.out.println("IP: " + getHostSRV_IP(host));

// 		InetAddress[] all = InetAddress.getAllByName(host);
// 		for (InetAddress ia: all) {
// 			System.out.println("Host: " + ia.toString());
// 		} // end of for (InetAddress ia: all)

// 		Hashtable env = new Hashtable();
// 		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
// 		//		env.put("java.naming.provider.url", "dns://10.75.32.10");
// 		DirContext ctx = new InitialDirContext(env);
// 		Attributes attrs =
// 			ctx.getAttributes("_xmpp-server._tcp." + host,
// 				new String[] {"SRV", "A"});

// 		String id = "SRV";
// 		Attribute att = attrs.get(id);
// 		if (att == null) {
// 			id = "A";
// 			att = attrs.get(id);
// 		} // end of if (attr == null)
// 		System.out.println(id + ": " + att.get(0));
// 		System.out.println("Class: " + att.get(0).getClass().getSimpleName());

// 		for (NamingEnumeration ae = attrs.getAll(); ae.hasMoreElements(); ) {
// 			Attribute attr = (Attribute)ae.next();
// 			String attrId = attr.getID();
// 			for (Enumeration vals = attr.getAll(); vals.hasMoreElements();
// 					 System.out.println(attrId + ": " + vals.nextElement()));
// 		}
// 		ctx.close();
	}


} // DNSResolver