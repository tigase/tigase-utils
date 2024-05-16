/*
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
package tigase.cert;

import junit.framework.TestCase;

import javax.crypto.Cipher;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.KeyPair;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.Objects;

import static tigase.cert.CertificateUtil.*;

/**
 * @author andrzej
 */
public class CertificateUtilTest
		extends TestCase {

	public void testCertificateDomainVerification() throws Exception {
		KeyPair keyPair = createKeyPair(1024, "secret");

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
		X509Certificate cert = (X509Certificate) entry.getCertChain()[0];

		System.out.println(entry);
		System.out.println(CertificateUtil.exportToPemFormat(entry));

		assertTrue("Verified certificate domain - domain: " + domain, verifyCertificateForDomain(cert, domain));

		CertificateGenerator generator = CertificateGeneratorFactory.getGenerator();
		if (generator.canGenerateWildcardSAN()) {
			assertTrue("Verified certificate domain - wildcard domain: " + domain,
					   verifyCertificateForDomain(cert, "subdomain." + domain));
			assertFalse("Verified certificate domain - fail.tigase.im",
						verifyCertificateForDomain(cert, "fail.tigase.im"));
		}
	}

	public void testCertificateWildcardDomainVerification() throws Exception {
		KeyPair keyPair = createKeyPair(1024, "secret");

		// Certificate
		String email = "artur.hefczyc@tigase.org";
		String domain = "tigase.org";
		String ou = "XMPP Service";
		String o = "Tigase.org";
		String l = "Cambourne";
		String st = "Cambridgeshire";
		String c = "UK";

		String wildcardDomain = "*." + domain;

		CertificateEntry entry = createSelfSignedCertificate(email, wildcardDomain, ou, o, l, st, c, () -> keyPair);
		X509Certificate cert = (X509Certificate) entry.getCertChain()[0];

		System.out.println(entry);
		System.out.println(CertificateUtil.exportToPemFormat(entry));

		assertTrue("Verified certificate domain - domain: " + wildcardDomain, verifyCertificateForDomain(cert, domain));

		CertificateGenerator generator = CertificateGeneratorFactory.getGenerator();
		if (generator.canGenerateWildcardSAN()) {
			assertTrue("Verified certificate domain - wildcard domain: " + wildcardDomain,
					   verifyCertificateForDomain(cert, "subdomain." + domain));
			assertFalse("Verified certificate domain - fail.tigase.im",
						verifyCertificateForDomain(cert, "fail.tigase.im"));
		}
	}

	public void testEcdsPkcs8Load() throws Exception {
		try (Reader r = new InputStreamReader(
				Objects.requireNonNull(this.getClass().getResourceAsStream("/key_ecds.pem")))) {
			var entry = CertificateUtil.parseCertificate(r);
			assertNotNull(entry);
			assertNotNull(entry.getPrivateKey());
			assertTrue(entry.getPrivateKey() instanceof ECPrivateKey);
			assertEquals(1, entry.getCertChain().length);
		}
	}

	public void testEncription() throws Exception {

		KeyPair keyPair = createKeyPair(1024, "secret");

		assertNotNull("Key pair generation failed", keyPair);

		// Encryption/decription test
		byte[] inputText = "Encription test...".getBytes();
		Cipher cipher = Cipher.getInstance("RSA");

		cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

		byte[] cipherText = cipher.doFinal(inputText);

		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

		byte[] plainText = cipher.doFinal(cipherText);

		assertEquals("Failed encryption-decryption test", new String(inputText), new String(plainText));
	}

	public void testRsaPkcs8Load() throws Exception {
		try (Reader r = new InputStreamReader(
				Objects.requireNonNull(this.getClass().getResourceAsStream("/key_rsa.pem")))) {
			var entry = CertificateUtil.parseCertificate(r);
			assertNotNull(entry);
			assertNotNull(entry.getPrivateKey());
			assertTrue(entry.getPrivateKey() instanceof RSAPrivateKey);
			assertEquals(1, entry.getCertChain().length);
		}
	}
	public void testRsaPkcs1Load() throws Exception {
		try (Reader r = new InputStreamReader(
				Objects.requireNonNull(this.getClass().getResourceAsStream("/key_rsa_pkcs1.pem")))) {
			var entry = CertificateUtil.parseCertificate(r);
			assertNotNull(entry);
			assertNotNull(entry.getPrivateKey());
			assertTrue(entry.getPrivateKey() instanceof RSAPrivateKey);
			assertEquals(1, entry.getCertChain().length);
		}
	}

	public void testSelfSignedCert() throws Exception {
		KeyPair keyPair = createKeyPair(1024, "secret");

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
		X509Certificate cert = (X509Certificate) entry.getCertChain()[0];

		cert.checkValidity();
		assertTrue("Checked certificate validty for today - valid", true);

		try {
			cert.checkValidity(new Date(System.currentTimeMillis() - (1000 * 3600 * 24)));
			fail("Checked certificate validty for yesterday - valid");
		} catch (CertificateNotYetValidException e) {
			assertTrue("Checked certificate validty for yesterday - not valid", true);
		}

		cert.verify(entry.getKeyPair().get().getPublic());
		assertTrue("Verified certificate with public key - done", true);
	}
}
