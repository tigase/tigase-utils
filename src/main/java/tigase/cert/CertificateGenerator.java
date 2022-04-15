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

import tigase.annotations.TigaseDeprecated;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public interface CertificateGenerator {

	boolean canGenerateWildcardSAN();

	@Deprecated
	@TigaseDeprecated(since = "4.3.0", removeIn = "5.0.0", note = "Due to JDK API limitations")
	X509Certificate generateSelfSignedCertificate(String email, String domain, String organizationUnit,
												  String organization, String city, String state, String country,
												  KeyPair keyPair)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException,
				   NoSuchProviderException, SignatureException;

	CertificateEntry generateSelfSignedCertificateEntry(String email, String domain, String organizationUnit,
														String organization, String city, String state, String country,
														KeyPair keyPair)
			throws GeneralSecurityException, IOException;

}
