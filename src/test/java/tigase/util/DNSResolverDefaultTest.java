package tigase.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertTrue;
import static tigase.util.DNSResolverIfc.TIGASE_PRIMARY_ADDRESS;
import static tigase.util.DNSResolverIfc.TIGASE_SECONDARY_ADDRESS;

public class DNSResolverDefaultTest {

	@Test
	public void getEmptyHost() {
		System.setProperty(TIGASE_PRIMARY_ADDRESS, "");
		System.setProperty(TIGASE_SECONDARY_ADDRESS, "");
		final DNSResolverDefault dnsResolverDefault = new DNSResolverDefault();

		assertTrue("Default hostname is empty", !dnsResolverDefault.getDefaultHost().isEmpty());
		assertTrue("Secondary hostname is empty", !dnsResolverDefault.getSecondaryHost().isEmpty());
	}

}