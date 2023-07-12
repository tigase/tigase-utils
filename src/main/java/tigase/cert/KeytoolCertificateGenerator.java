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

//import sun.security.x509.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// This may stop working very soon as JDK9 warns about usage of internal classes which may be removed in the future.
public class KeytoolCertificateGenerator
		implements CertificateGenerator {

	private static final Logger log = Logger.getLogger(KeytoolCertificateGenerator.class.getCanonicalName());

	private static void appendName(StringBuilder sb, String prefix, String value) {
		log.log(Level.FINE, "appending value: {0} with prefix: {1} to sb: {2}",
				new Object[]{value, prefix, sb.toString()});
		if (value != null) {
			if (sb.length() > 0) {
				sb.append(", ");
			}

			sb.append(prefix).append('=').append(value);
		}
	}

	@Override
	public boolean canGenerateWildcardSAN() {
		return Runtime.version().feature() >= 17;
	}

	@Override
	public X509Certificate generateSelfSignedCertificate(String email, String domain, String organizationUnit,
														 String organization, String city, String state, String country,
														 KeyPair keyPair)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException,
				   NoSuchProviderException, SignatureException {
		throw new UnsupportedOperationException(
				"Generating self-signed certificate only is not supported by this implementaiton");
	}

	/**
	 * @param keyPair is ignored due to `keytool` limitations
	 */
	@Override
	public CertificateEntry generateSelfSignedCertificateEntry(String email, String domain, String organizationUnit,
															   String organization, String city, String state,
															   String country, KeyPair keyPair, boolean generateWildcard)
			throws GeneralSecurityException, IOException {

		// test running under windows

		UUID uuid = UUID.randomUUID();
		final String password = "123456";
		if (Files.notExists(Paths.get("certs"))) {
			Files.createDirectory(Paths.get("certs"));
		}
		final Path file = Paths.get("certs", domain + "_" + uuid + ".jks");

		KeyStore keyStore = KeyStore.getInstance("JKS");
		if (file.toFile().exists()) {
			keyStore.load(new FileInputStream(file.toFile()), password.toCharArray());

			if (keyStore.containsAlias(domain)) {
				keyStore.deleteEntry(domain);
				keyStore.store(new FileOutputStream(file.toFile()), password.toCharArray());
			}
		}

		final List<String> commandParameters = new ArrayList<>(List.of("keytool", "-genkey"));
		commandParameters.addAll(List.of("-alias", domain));
		commandParameters.addAll(List.of("-keyalg", "RSA"));
		commandParameters.addAll(List.of("-keysize", "2048"));
		commandParameters.addAll(List.of("-sigalg", "SHA256withRSA"));
		commandParameters.addAll(List.of("-storetype", "JKS"));
		commandParameters.addAll(List.of("-keystore", file.toString()));
		commandParameters.addAll(List.of("-storepass", password));
		commandParameters.addAll(List.of("-keypass", password));
		commandParameters.addAll(
				List.of("-dname", getDomainName(email, domain, organizationUnit, organization, city, state, country)));
		commandParameters.addAll(List.of("-validity", "365"));
		commandParameters.addAll(List.of("-deststoretype", "pkcs12"));
		commandParameters.addAll(List.of("-storetype", "JKS"));
		if (generateWildcard && canGenerateWildcardSAN() && !isWildcardDomain(domain)) {
			commandParameters.addAll(getSAN(domain));
		}
		ProcessBuilder keytool = new ProcessBuilder().command(commandParameters);

		final Process process = keytool.start();
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			throw new IOException("Keytool execution error");
		}

		if (log.isLoggable(Level.FINEST)) {
			log.log(Level.FINEST,
					"Generating certificate using `keytool` using command: " + process.info() + ", parameters: " +
							commandParameters);
		}

		if (process.exitValue() > 0) {
			final String processError = (new BufferedReader(new InputStreamReader(process.getErrorStream()))).lines()
					.collect(Collectors.joining(" \\ "));
			final String processOutput = (new BufferedReader(new InputStreamReader(process.getInputStream()))).lines()
					.collect(Collectors.joining(" \\ "));
			log.log(Level.WARNING, "Error generating certificate, error output: " + processError + ", normal output: " +
					processOutput + ", commandline parameters: " + commandParameters);
			throw new IOException(
					"Keytool execution error: \'" + processError + "\', output: \'" + processOutput + "\'" + ", commandline parameters: " + commandParameters);
		}

		keyStore.load(new FileInputStream(file.toFile()), password.toCharArray());
		KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(domain,
																						new KeyStore.PasswordProtection(
																								password.toCharArray()));

		Certificate[] chain = keyStore.getCertificateChain(domain);
		PrivateKey privateKey = pkEntry.getPrivateKey();
		final CertificateEntry certificateEntry = new CertificateEntry(chain, privateKey);

		Files.deleteIfExists(file);
		return certificateEntry;
	}

	private boolean isWildcardDomain(String domain) {
		return domain.startsWith("*.");
	}

	private String getDomainName(String email, String domain, String organizationUnit, String organization, String city,
								 String state, String country) {
		// keytool doesn't allow for a domain with wildcard in SAN extenstion so it has to go directly to CN
		return "CN=" + domain + ", OU=" + organizationUnit + ", O=" + organization + ", L=" + city + ", ST=" + state +
				", C=" + country + ", EMAILADDRESS=" + email;
	}

	private List<String> getSAN(String domain) {
		return List.of("-ext", "SAN=dns:*." + domain);
	}
}
