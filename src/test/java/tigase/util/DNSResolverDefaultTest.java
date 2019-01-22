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
package tigase.util;

import org.junit.Test;
import tigase.util.dns.DNSResolverFactory;
import tigase.util.dns.DNSResolverIfc;

import static org.junit.Assert.assertTrue;
import static tigase.util.dns.DNSResolverIfc.TIGASE_PRIMARY_ADDRESS;
import static tigase.util.dns.DNSResolverIfc.TIGASE_SECONDARY_ADDRESS;

public class DNSResolverDefaultTest {

	@Test
	public void getEmptyHost() {
		System.setProperty(TIGASE_PRIMARY_ADDRESS, "");
		System.setProperty(TIGASE_SECONDARY_ADDRESS, "");

		final DNSResolverIfc dnsResolverDefault = DNSResolverFactory.getInstance();

		assertTrue("Default hostname is empty", !dnsResolverDefault.getDefaultHost().isEmpty());
		assertTrue("Secondary hostname is empty", !dnsResolverDefault.getSecondaryHost().isEmpty());
	}

}