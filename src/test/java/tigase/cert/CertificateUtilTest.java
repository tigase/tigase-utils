/*
 * CertificateUtilTest.java
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

package tigase.cert;

import junit.framework.TestCase;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;

import static tigase.cert.CertificateUtil.*;

/**
 *
 * @author andrzej
 */
public class CertificateUtilTest extends TestCase {

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

		assertEquals("Failed encryption-decryption test", new String(inputText), 
				new String(plainText));
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

		cert.verify(keyPair.getPublic());
		assertTrue("Verified certificate with public key - done", true);
	}

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
		assertTrue("Verified certificate domain - domain: " + domain, verifyCertificateForDomain(cert, domain));
		assertFalse("Verified certificate domain - fail.tigase.org", verifyCertificateForDomain(cert, "fail.tigase.org"));
	}
}
