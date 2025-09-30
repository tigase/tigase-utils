package tigase.cert;

import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import static org.junit.Assert.*;
import static tigase.cert.CertificateUtil.createKeyPair;
import static tigase.cert.CertificateUtil.createSelfSignedCertificate;

public class CertificateEntryTest {

	private X509Certificate cert;
	private KeyPair keyPair;

	@Before
	public void setUp() throws Exception {
		keyPair = createKeyPair(1024, "secret");

		// Certificate
		String email = "artur.hefczyc@tigase.org";
		String domain = "tigase.org";
		String ou = "XMPP Service";
		String o = "Tigase.org";
		String l = "Cambourne";
		String st = "Cambridgeshire";
		String c = "UK";

		//System.out.println("Creating self-signed certificate for issuer: " + domain);

		CertificateEntry entry = createSelfSignedCertificate(email, domain, ou, o, l, st, c, () -> keyPair);
		cert = (X509Certificate) entry.getCertChain()[0];
	}

	@Test
	public void getCertChain() {
		CertificateEntry entry = new CertificateEntry(new Certificate[]{ cert }, keyPair);
		assertArrayEquals(CertificateUtil.sort(entry.getCertChain()), entry.getCertChain());
	}

	@Test
	public void getDomain() {
		CertificateEntry entry = new CertificateEntry(new Certificate[]{ cert }, keyPair);
		assertEquals("tigase.org", entry.getDomain());
	}

	@Test
	public void getAllDomains() {
		CertificateEntry entry = new CertificateEntry(new Certificate[]{ cert }, keyPair);
		assertTrue(entry.getAllDomains().contains("tigase.org"));
		assertTrue(entry.getAllDomains().contains("*.tigase.org"));
		assertEquals(2, entry.getAllDomains().size());
	}

	@Test
	public void isSelfSigned() {
		CertificateEntry entry = new CertificateEntry(new Certificate[]{ cert }, keyPair);
		assertTrue(entry.isSelfSigned());
	}

	@Test
	public void isValid() {
		CertificateEntry entry = new CertificateEntry(new Certificate[]{ cert }, keyPair);
		assertTrue(entry.isValid());
	}
}