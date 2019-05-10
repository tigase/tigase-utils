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
package tigase.cert;

import sun.security.x509.*;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

// This may stop working very soon as JDK9 warns about usage of internal classes which may be removed in the future.
public class OldSelfSignedCertificateGenerator
		implements CertificateGenerator {

	private static final Logger log = Logger.getLogger(OldSelfSignedCertificateGenerator.class.getCanonicalName());

	private static void appendName(StringBuilder sb, String prefix, String value) {
		log.log(Level.INFO, "appending value: {0} with prefix: {1} to sb: {2}",
				new Object[]{value, prefix, sb.toString()});
		if (value != null) {
			if (sb.length() > 0) {
				sb.append(", ");
			}

			sb.append(prefix).append('=').append(value);
		}
	}

	@Override
	public X509Certificate generateSelfSignedCertificate(String email, String domain, String organizationUnit,
														 String organization, String city, String state, String country,
														 KeyPair keyPair)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException,
				   NoSuchProviderException, SignatureException {
		log.log(Level.INFO, "creating self signed cert, email: {0}, domain: {1}, organizationUnit: {2}," +
						"organization: {3}, city: {4}, state: {5}, country: {6}, keyPair: {7}",
				new Object[]{email, domain, organizationUnit, organization, city, state, country, keyPair});
		X509CertInfo certInfo = new X509CertInfo();
		CertificateVersion certVersion = new CertificateVersion();

		certInfo.set(X509CertInfo.VERSION, certVersion);

		Date firstDate = new Date();
		Date lastDate = new Date(firstDate.getTime() + 365 * 24 * 60 * 60 * 1000L);
		CertificateValidity interval = new CertificateValidity(firstDate, lastDate);

		certInfo.set(X509CertInfo.VALIDITY, interval);
		certInfo.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber((int) (firstDate.getTime() / 1000)));

		StringBuilder subject = new StringBuilder(1024);

		appendName(subject, "CN", domain);
		appendName(subject, "EMAILADDRESS", email);
		appendName(subject, "OU", organizationUnit);
		appendName(subject, "O", organization);
		appendName(subject, "L", city);
		appendName(subject, "ST", state);
		appendName(subject, "C", country);

		// since JDK 1.8 we need to pass X500Name as ISSUER and SUBJECT
		// while before JDK 1.8 we needed to pass special classes
		// For now let's catch exception and use passing special classes
		// as a fallback mechanism
		X500Name issuerName = new X500Name(subject.toString());
		try {
			certInfo.set(X509CertInfo.ISSUER, issuerName);
			certInfo.set(X509CertInfo.SUBJECT, issuerName);
		} catch (CertificateException ex) {
			// trying older solution as a fallback
			CertificateIssuerName certIssuer = new CertificateIssuerName(issuerName);
			CertificateSubjectName certSubject = new CertificateSubjectName(issuerName);

			certInfo.set(X509CertInfo.ISSUER, certIssuer);
			certInfo.set(X509CertInfo.SUBJECT, certSubject);
		}

		// certInfo.set(X509CertInfo.ISSUER + "." +
		// CertificateSubjectName.DN_NAME, issuerName);
		AlgorithmId algorithm = new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_oid);
		CertificateAlgorithmId certAlgorithm = new CertificateAlgorithmId(algorithm);

		certInfo.set(X509CertInfo.ALGORITHM_ID, certAlgorithm);

		CertificateX509Key certPublicKey = new CertificateX509Key(keyPair.getPublic());

		certInfo.set(X509CertInfo.KEY, certPublicKey);

		// certInfo.set(X509CertInfo.ALGORITHM_ID + "." +
		// CertificateAlgorithmId.ALGORITHM, algorithm);
		X509CertImpl newCert = new X509CertImpl(certInfo);

		newCert.sign(keyPair.getPrivate(), "SHA1WithRSA");

		log.log(Level.FINEST, "creating self signed cert, newCert: {0}", newCert);

		return newCert;
	}
}
