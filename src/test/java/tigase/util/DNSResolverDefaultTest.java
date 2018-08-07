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