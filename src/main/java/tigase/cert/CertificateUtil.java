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
import tigase.util.Algorithms;
import tigase.util.Base64;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created: Sep 22, 2010 3:09:01 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public abstract class CertificateUtil {

	protected static final byte[] ID_ON_XMPPADDR = new byte[]{0x06, 0x08, 0x2B, 0x06, 0x01, 0x05, 0x05, 0x07, 0x08,
															  0x05};
	private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
	private static final String BEGIN_KEY = "-----BEGIN PRIVATE KEY-----";
	private static final String BEGIN_RSA_KEY = "-----BEGIN RSA PRIVATE KEY-----";
	private static final String ENCRIPT_TEST = "--encript-test";
	private static final String ENCRIPT_TEST_SHORT = "-et";
	private static final String END_CERT = "-----END CERTIFICATE-----";
	private static final String END_KEY = "-----END PRIVATE KEY-----";
	private static final String END_RSA_KEY = "-----END RSA PRIVATE KEY-----";
	private static final String KEY_PAIR = "--key-pair";
	private static final String KEY_PAIR_SHORT = "-kp";
	private static final String LOAD_CERT = "--load-cert";
	private static final String LOAD_CERT_SHORT = "-lc";
	private static final String LOAD_DER_PRIVATE_KEY = "--load-der-priv-key";
	private static final String LOAD_DER_PRIVATE_KEY_SHORT = "-ldpk";
	private static final Logger log = Logger.getLogger(CertificateUtil.class.getName());
	private static final String PRINT_PROVIDERS = "--print-providers";
	private static final String PRINT_PROVIDERS_SHORT = "-pp";
	private static final String PRINT_SERVICES = "--print-services";
	private static final String PRINT_SERVICES_SHORT = "-ps";
	private static final String SELF_SIGNED_CERT = "--self-signed-cert";
	private static final String SELF_SIGNED_CERT_SHORT = "-ssc";
	private static final String STORE_CERT = "--store-cert";

	// ~--- methods
	// --------------------------------------------------------------

	private static final String IPv4_IPv6_PATTERN =
			"^(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))" +
					"|([0-9a-fA-F:]{2,}(:([0-9]{1,3}\\.){3}[0-9]{1,3})?))$";
	private static final String STORE_CERT_SHORT = "-sc";

	private static int calculateLength(byte[] buffer, int start) throws ArrayIndexOutOfBoundsException {
		log.log(Level.FINE, "calculating length, buffer: {0}, start: {1}", new Object[]{new String(buffer), start});
		int offset = start + 1;
		int b = (buffer[offset] & 0xff);
		if (b < 0x80) {
			return (b);
		}
		int result = 0;
		offset++;
		int len = b - 0x80;
		for (int i = 0; i < len; i++) {
			b = (buffer[(i + offset)] & 0xff);
			result = (result << 8) + b;
		}
		return result;
	}

	private final static int calculateOffset(byte[] buffer, int offset) throws ArrayIndexOutOfBoundsException {
		log.log(Level.FINE, "calculating offset, buffer: {0}, start: {1}", new Object[]{new String(buffer), offset});
		int b = (buffer[(offset + 1)] & 0xff);
		if (b < 0x80) {
			return (offset + 2);
		}
		int len = b - 0x80;
		return (offset + len + 2);
	}

	public static KeyPair createKeyPair(int size, String password) throws NoSuchAlgorithmException {
		log.log(Level.CONFIG, "creating KeyPair, size: {0}, password: {1}", new Object[]{size, password});
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

		keyPairGenerator.initialize(size);

		KeyPair keyPair = keyPairGenerator.genKeyPair();
		log.log(Level.CONFIG, "creating KeyPair, KeyPairGenerator: {0}, keyPair: {1}",
				new Object[]{keyPairGenerator, keyPair});

		return keyPair;
	}

	@Deprecated()
	@TigaseDeprecated(since = "8.0.0", removeIn = "7.3.0", note = "Use method with keyPairSupplier")
	public static X509Certificate createSelfSignedCertificate(String email, String domain, String organizationUnit,
															  String organization, String city, String state,
															  String country, KeyPair keyPair)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException,
				   NoSuchProviderException, SignatureException {

		return CertificateGeneratorFactory.getGenerator()
				.generateSelfSignedCertificate(email, domain, organizationUnit, organization, city, state, country,
											   keyPair);
	}

	public static CertificateEntry createSelfSignedCertificate(String email, String domain, String organizationUnit,
															   String organization, String city, String state,
															   String country, KeyPairSupplier keyPairSupplier)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException,
				   NoSuchProviderException, SignatureException {

		CertificateGenerator generator = CertificateGeneratorFactory.getGenerator();
		KeyPair keyPair = keyPairSupplier.get();
		try {
			return generator.generateSelfSignedCertificateEntry(email, domain, organizationUnit, organization,
																city, state, country, keyPair);
		} catch (GeneralSecurityException e) {
			throw new CertificateException(e);
		}
	}

	private static void encriptTest() throws Exception {

		// KeyPair test:
		// 1. Generating key pair:
		System.out.print("Generating key pair...");
		System.out.flush();

		KeyPair keyPair = createKeyPair(1024, "secret");

		System.out.println(" done.");

		// Encryption/decription test
		byte[] inputText = "Encription test...".getBytes();
		Cipher cipher = Cipher.getInstance("RSA");

		System.out.println("Encripting text: " + new String(inputText));
		cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

		byte[] cipherText = cipher.doFinal(inputText);

		System.out.println("Encripted text: " + Algorithms.bytesToHex(cipherText));
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

		byte[] plainText = cipher.doFinal(cipherText);

		System.out.println("Decripted text: " + new String(plainText));
	}

	public static String exportToPemFormat(CertificateEntry entry) throws CertificateEncodingException {
		log.log(Level.FINEST, "exportToPemFormat cert, entry: {0}", new Object[]{entry});
		StringBuilder sb = new StringBuilder(4096);

		if ((entry.getCertChain() != null) && (entry.getCertChain().length > 0)) {
			byte[] bytes = entry.getCertChain()[0].getEncoded();
			String b64 = Base64.encode(bytes);

			sb.append(BEGIN_CERT).append('\n').append(b64).append('\n').append(END_CERT).append('\n');
		}

		if (entry.getPrivateKey() != null) {
			byte[] bytes = entry.getPrivateKey().getEncoded();
			String b64 = Base64.encode(bytes);

			sb.append(BEGIN_KEY).append('\n').append(b64).append('\n').append(END_KEY).append('\n');
		}

		if ((entry.getCertChain() != null) && (entry.getCertChain().length > 1)) {
			for (int i = 1; i < entry.getCertChain().length; i++) {
				byte[] bytes = entry.getCertChain()[i].getEncoded();
				String b64 = Base64.encode(bytes);

				sb.append(BEGIN_CERT).append('\n').append(b64).append('\n').append(END_CERT).append('\n');
			}
		}

		log.log(Level.FINEST, "exportToPemFormat cert, string: {0}", new Object[]{sb.toString()});

		return sb.toString();
	}

	protected static String extractCN(X500Principal principal) {
		String[] dd = principal.getName(X500Principal.RFC2253).split(",");
		for (String string : dd) {
			if (string.toLowerCase().startsWith("cn=")) {
				log.log(Level.FINEST, "extractCN, principal: {0}, result: {1}",
						new Object[]{principal, string.substring(3)});
				return string.substring(3);
			}
		}
		return null;
	}

	private static String extractValue(byte[] buffer, byte[] id) {
		log.log(Level.FINE, "extracting value, buffer: {0}, id: {1}", new Object[]{new String(buffer), new String(id)});
		try {
			if (buffer[0] != 0x30) {
				return null;
			}
			int len = calculateLength(buffer, 0);
			int offset = calculateOffset(buffer, 0);

			for (int i = 0; i < id.length; i++) {
				int j = offset + i;
				if (j >= len) {
					return null;
				}
				if (id[i] != buffer[j]) {
					return null;
				}
			}

			int valStart = offset + id.length;

			int pos = calculateOffset(buffer, valStart);
			while (pos < buffer.length) {
				byte d = buffer[pos];
				int cmp = calculateOffset(buffer, pos);
				int l = calculateLength(buffer, pos);
				if (d == 0x0c || d == 0x16) {
					return new String(buffer, cmp, l);
				}
				pos = cmp;
			}
			return null;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public static List<String> extractXmppAddrs(final X509Certificate x509Certificate) {
		log.log(Level.FINEST, "extractXmppAddrs, x509Certificate: {0}", new Object[]{x509Certificate.toString()});
		final ArrayList<String> result = new ArrayList<String>();
		try {
			Collection<List<?>> altNames = x509Certificate.getSubjectAlternativeNames();
			if (altNames == null) {
				return result;
			}

			for (List<?> item : altNames) {
				Integer type = (Integer) item.get(0);
				if (type == 0) {
					byte[] buffer = (byte[]) item.get(1);
					String jid = extractValue(buffer, ID_ON_XMPPADDR);
					if (jid != null) {
						result.add(jid);
					}

				}
			}
			log.log(Level.FINEST, "extractXmppAddrs, result: {0}", new Object[]{result});
			return result;
		} catch (Exception e) {
			return result;
		}
	}

	public static List<String> getCertAltCName(X509Certificate cert) {
		log.log(Level.FINEST, "getCertAltCName, x509Certificate: {0}", new Object[]{cert.toString()});
		try {
			Collection<List<?>> subjectAlternativeNames = cert.getSubjectAlternativeNames();
			ArrayList<String> result = new ArrayList<>();

			if (subjectAlternativeNames != null && subjectAlternativeNames.size() > 0) {
				for (List list : subjectAlternativeNames) {
					// we are only interested in dNSName
					if (list.get(0).equals(2)) {
						result.add(list.get(1).toString());
					}
				}
			}
			log.log(Level.FINE, "Certificate alternative names: {0}", new Object[]{result});
			return result;
		} catch (CertificateParsingException e) {
			return Collections.emptyList();
		}
	}

	public static String getCertCName(X509Certificate cert) {
		log.log(Level.FINEST, "getCertCName, X509Certificate: {0}", new Object[]{cert});
		X500Principal princ = cert.getSubjectX500Principal();
		String name = princ.getName();
		String[] all = name.split(",");

		for (String n : all) {
			String[] ns = n.trim().split("=");

			if (ns[0].equals("CN")) {
				log.log(Level.FINE, "Certificate DN: {0}", new Object[]{ns[1]});
				return ns[1];
			}
		}

		return null;
	}

	public static String getCertificateBasicInfo(Certificate cert) {
		return getCertificateBasicInfo(new StringBuilder(), cert).toString();
	}

	public static StringBuilder getCertificateBasicInfo(StringBuilder sb, Certificate cert) {
		if (cert instanceof X509Certificate) {
			final X509Certificate certX509 = (X509Certificate) cert;
			sb.append("CN: ").append(getCertCName(certX509)).append('\n');
			final List<String> certAltCName = getCertAltCName(certX509);
			if (certAltCName != null && !certAltCName.isEmpty()) {
				sb.append('\t').append("alt: ").append(certAltCName).append('\n');
			}
			sb.append('\t').append("Issuer: ").append(certX509.getIssuerDN()).append('\n');
			sb.append('\t').append("Not Before: ").append(certX509.getNotBefore()).append('\n');
			sb.append('\t').append("Not After: ").append(certX509.getNotAfter()).append('\n');
			try {
				sb.append('\t').append("Fingerprint: ").append(getCertificateFingerprint(certX509)).append('\n');
			} catch (Exception e) {
				log.log(Level.WARNING, "Could not calculate fingerprint", e);
			}
			getCertificateSerialNumber(certX509).ifPresent(serialNumber -> sb.append('\t')
					.append("SerialNumber: [")
					.append(serialNumber.toString(16))
					.append("]\n"));
			sb.append('\n');
		}
		return sb;
	}

	public static String getCertificateFingerprint(Certificate cert)
			throws CertificateEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(cert.getEncoded());
		return Algorithms.bytesToHex(md.digest());
	}

	public static Optional<BigInteger> getCertificateSerialNumber(Certificate cert)  {
		BigInteger serialNumber = null;
		if (cert instanceof X509Certificate) {
			final X509Certificate certX509 = (X509Certificate) cert;
			serialNumber = certX509.getSerialNumber();
		}
		return Optional.ofNullable(serialNumber);
	}

	private static Certificate getRootCertificateCertificate(List<Certificate> certs) {
		Certificate rt = null;
		for (Certificate x509Certificate : certs) {
			Principal i = ((X509Certificate) x509Certificate).getIssuerDN();
			Principal s = ((X509Certificate) x509Certificate).getSubjectDN();
			if (i.equals(s)) {
				rt = x509Certificate;
			}
		}
		return rt;
	}

	public static boolean isExpired(X509Certificate cert) {
		try {
			cert.checkValidity();

			return false;
		} catch (Exception e) {
			return true;
		}
	}

	public static boolean isSelfSigned(X509Certificate cert) {
		final boolean result = cert.getIssuerDN().equals(cert.getSubjectDN());
		if (result) {
			log.log(Level.CONFIG, "Self-signed certificate for domain: {0}", new Object[]{cert.getSubjectDN()});
		}
		log.log(Level.FINEST, "isSelfSigned, result: {0}, X509Certificate: {1}", new Object[]{result, cert});
		return result;
	}

	private static void keyPairTest() throws Exception {

		// KeyPair test:
		// 1. Generating key pair:
		System.out.print("Generating key pair...");
		System.out.flush();

		KeyPair keyPair = createKeyPair(1024, "secret");

		System.out.println(" done, private key: " + keyPair.getPrivate() + ", public key: " + keyPair.getPublic());
	}

	public static CertificateEntry loadCertificate(File file)
			throws FileNotFoundException, IOException, CertificateException, NoSuchAlgorithmException,
				   InvalidKeySpecException {
		return parseCertificate(new FileReader(file));
	}

	/**
	 * Loads a certificate from a DER byte buffer.
	 */
	@Deprecated
	@TigaseDeprecated(since = "4.2.0", note = "Method loads only single certificate; use other #loadCertificate() methods")
	public static CertificateEntry loadCertificate(byte[] bytes) throws CertificateException, NoSuchProviderException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		Certificate certificate = cf.generateCertificate(bais);
		CertificateEntry entry = new CertificateEntry();
		entry.setCertChain(new Certificate[]{certificate});
		return entry;
	}

	public static CertificateEntry loadCertificate(String file)
			throws FileNotFoundException, IOException, CertificateException, NoSuchAlgorithmException,
				   InvalidKeySpecException {
		return loadCertificate(new File(file));
	}

	public static PrivateKey loadPrivateKeyFromDER(File file)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		log.log(Level.CONFIG, "loadPrivateKeyFromDER, file: {0}", new Object[]{file});
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		byte[] privKeyBytes = new byte[(int) file.length()];

		dis.read(privKeyBytes);
		dis.close();

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privKeyBytes);
		RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privSpec);

		return privKey;
	}

	public static void main(String[] args) throws Exception {
		final Level lvl = Level.FINE;
		log.setLevel(lvl);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(lvl);
		log.addHandler(consoleHandler);

		if ((args != null) && (args.length > 0)) {
			if (args[0].equals(PRINT_PROVIDERS) || args[0].equals(PRINT_PROVIDERS_SHORT)) {
				printProviders(false);
			}

			if (args[0].equals(PRINT_SERVICES) || args[0].equals(PRINT_SERVICES_SHORT)) {
				printProviders(true);
			}

			if (args[0].equals(KEY_PAIR) || args[0].equals(KEY_PAIR_SHORT)) {
				keyPairTest();
			}

			if (args[0].equals(ENCRIPT_TEST) || args[0].equals(ENCRIPT_TEST_SHORT)) {
				encriptTest();
			}

			if (args[0].equals(SELF_SIGNED_CERT) || args[0].equals(SELF_SIGNED_CERT_SHORT)) {
				selfSignedCertTest();
			}

			if (args[0].equals(LOAD_CERT) || args[0].equals(LOAD_CERT_SHORT)) {
				String file = args[1];
				CertificateEntry ce = loadCertificate(file);

				final boolean basic = args.length == 3 && "-simple".equals(args[2]);
				System.out.println(ce.toString(basic));

				final ArrayList<Certificate> certs = new ArrayList<>(Arrays.asList(ce.getCertChain()));
				if (getRootCertificateCertificate(certs) == null) {
					System.out.println("Can't find root certificate in chain!");
					for (Certificate x509Certificate : certs) {
						Principal i = ((X509Certificate) x509Certificate).getIssuerDN();
						Principal s = ((X509Certificate) x509Certificate).getSubjectDN();
						System.out.println(s + " ~ ISSUED BY: " + i);
					}
				}
				sort(ce.getCertChain());
			}

			if (args[0].equals(STORE_CERT) || args[0].equals(STORE_CERT_SHORT)) {
				String file = args[1];
				String cname = args[2];

				// Certificate
				String email = "artur.hefczyc@tigase.org";
				String domain = cname != null ? cname : "tigase.org";
				String ou = "XMPP Service";
				String o = "Tigase.org";
				String l = "Cambourne";
				String st = "Cambridgeshire";
				String c = "UK";
				CertificateEntry entry = createSelfSignedCertificate(email, domain, ou, o, l, st, c,
																	 () -> createKeyPair(1024, "secret"));

				storeCertificate(file, entry);
			}

			if (args[0].equals(LOAD_DER_PRIVATE_KEY) || args[0].equals(LOAD_DER_PRIVATE_KEY_SHORT)) {
				String file = args[1];
				PrivateKey key = loadPrivateKeyFromDER(new File(file));

				System.out.println(key.toString());
			}
		} else {
			printHelp();
		}
	}

	/**
	 * Checks if hostname matches name or wildcard
	 *
	 * @return true if there is a match
	 */
	public static boolean match(final String hostname, final String altName) {
		if (hostname == null || hostname.isEmpty() || altName == null || altName.isEmpty()) {
			return false;
		}

		final String normalizedAltName = altName.toLowerCase(Locale.US);
		if (!normalizedAltName.contains("*")) {
			return hostname.equals(normalizedAltName);
		}

		if (normalizedAltName.startsWith("*.") &&
				hostname.regionMatches(0, normalizedAltName, 2, normalizedAltName.length() - 2)) {
			return true;
		}

		int asteriskIdx = normalizedAltName.indexOf('*');
		int dotIdx = normalizedAltName.indexOf('.');
		if (asteriskIdx > dotIdx) {
			return false;
		}

		if (!hostname.regionMatches(0, normalizedAltName, 0, asteriskIdx)) {
			return false;
		}

		int suffixLength = normalizedAltName.length() - (asteriskIdx + 1);
		int suffixStart = hostname.length() - suffixLength;
		if (hostname.indexOf('.', asteriskIdx) < suffixStart) {
			return false; // wildcard '*' can't match a '.'
		}
		if (!hostname.regionMatches(suffixStart, normalizedAltName, asteriskIdx + 1, suffixLength)) {
			return false;
		}
		return true;
	}

	public static CertificateEntry parseCertificate(Reader data)
			throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException {
		BufferedReader br = new BufferedReader(data);
		StringBuilder sb = new StringBuilder(4096);
		List<X509Certificate> certs = new ArrayList<X509Certificate>();
		PrivateKey privateKey = null;
		String line;

		boolean addToBuffer = false;

		while ((line = br.readLine()) != null) {

			if (line.contains(BEGIN_CERT) || line.contains(BEGIN_KEY) || line.contains(BEGIN_RSA_KEY)) {
				addToBuffer = true;
			} else if (line.contains(END_CERT)) {
				addToBuffer = false;
				byte[] bytes = Base64.decode(sb.toString());
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				CertificateFactory cf = CertificateFactory.getInstance("X.509");

				while (bais.available() > 0) {
					Certificate cert = cf.generateCertificate(bais);

//					log.log( Level.FINEST, "parseCertificate, cert: {0}", new Object[] { cert } );
					certs.add((X509Certificate) cert);
				}

				sb = new StringBuilder(4096);
			} else if (line.contains(END_KEY)) {
				addToBuffer = false;
				byte[] bytes = Base64.decode(sb.toString());
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");

				privateKey = keyFactory.generatePrivate(keySpec);
				log.log(Level.FINEST, "parseCertificate, privateKey: {0}", new Object[]{privateKey});
				sb = new StringBuilder(4096);
			} else if (line.contains(END_RSA_KEY)) {
				addToBuffer = false;
				byte[] bytes = Base64.decode(sb.toString());
				RSAPrivateKeyDecoder decoder = new RSAPrivateKeyDecoder(bytes);

				privateKey = decoder.getPrivateKey();
				log.log(Level.FINEST, "parseCertificate, privateKey: {0}", new Object[]{privateKey});
				sb = new StringBuilder(4096);
			} else if (addToBuffer) {
				sb.append(line);
			}

		}

		CertificateEntry entry = new CertificateEntry();

		entry.setCertChain(certs.toArray(new Certificate[certs.size()]));
		entry.setPrivateKey(privateKey);

		log.log(Level.FINEST, "parseCertificate, entry: {0}", new Object[]{entry});

		return entry;
	}

	private static void printHelp() {
		System.out.println(CertificateUtil.class.getName() + " test code.");
		System.out.println("You can run following tests:");
		System.out.println(" " + PRINT_PROVIDERS + " | " + PRINT_PROVIDERS_SHORT + " - prints all supported providers");
		System.out.println(" " + PRINT_SERVICES + " | " + PRINT_SERVICES_SHORT + " - print all supported services");
		System.out.println(" " + KEY_PAIR + " | " + KEY_PAIR_SHORT + " - generate a key pair and print the result");
		System.out.println(" " + ENCRIPT_TEST + " | " + ENCRIPT_TEST_SHORT +
								   " - encript simple text with public key, decript with private");
		System.out.println(
				" " + SELF_SIGNED_CERT + " | " + SELF_SIGNED_CERT_SHORT + " - generate self signed certificate");
		System.out.println(
				" " + LOAD_CERT + " file.pem | " + LOAD_CERT_SHORT + " file.pem - load certificate from file");
		System.out.println(" " + STORE_CERT + " file.pem | " + STORE_CERT_SHORT +
								   " file.pem - generate self-signed certificate and save it to the given pem file");
		System.out.println(" " + LOAD_DER_PRIVATE_KEY + " | " + LOAD_DER_PRIVATE_KEY_SHORT +
								   " file.der - load private key from DER file.");
	}

	private static void printProviders(boolean includeServices) {

		// Initialization, basic information
		Provider[] providers = Security.getProviders();

		if ((providers != null) && (providers.length > 0)) {
			for (Provider provider : providers) {
				System.out.println(provider.getName() + "\t" + provider.getInfo());

				if (includeServices) {
					for (Provider.Service service : provider.getServices()) {
						System.out.println("\t" + service.getAlgorithm());
					}
				}
			}
		} else {
			System.out.println("No security providers found!");
		}
	}

	public static Certificate[] removeRootCACertificate(Certificate[] certChain) {
		return Arrays.stream(certChain)
				.filter(X509Certificate.class::isInstance)
				.map(X509Certificate.class::cast)
				.filter(cert -> {
					final boolean[] keyUsage = cert.getKeyUsage();
					return !(keyUsage != null && keyUsage[5] && isSelfSigned(cert));
				})
				.toArray(Certificate[]::new);
	}

	private static void selfSignedCertTest() throws Exception {
		KeyPair keyPair = createKeyPair(1024, "secret");

		// Certificate
		String email = "artur.hefczyc@tigase.org";
		String domain = "tigase.org";
		String ou = "XMPP Service";
		String o = "Tigase.org";
		String l = "Cambourne";
		String st = "Cambridgeshire";
		String c = "UK";

		System.out.println("Creating self-signed certificate for issuer: " + domain);

		CertificateEntry entry = createSelfSignedCertificate(email, domain, ou, o, l, st, c, () -> keyPair);
		X509Certificate cert = (X509Certificate) entry.getCertChain()[0];

		System.out.print("Checking certificate validity today...");
		System.out.flush();
		cert.checkValidity();
		System.out.println(" done.");
		System.out.print("Checking certificate validity yesterday...");
		System.out.flush();

		try {
			cert.checkValidity(new Date(System.currentTimeMillis() - (1000 * 3600 * 24)));
			System.out.println(" error.");
		} catch (CertificateNotYetValidException e) {
			System.out.println(" not valid!");
		}

		System.out.print("Verifying certificate with public key...");
		System.out.flush();
		if (entry.getKeyPair().isPresent()) {
			cert.verify(entry.getKeyPair().get().getPublic());
		} else {
			System.out.println(" KeyPair is missing");
		}
		System.out.println(" done.");
		System.out.println(cert.toString());
	}

	public static Certificate[] sort(Certificate[] chain) {
		List<Certificate> res = sort(new ArrayList<Certificate>(Arrays.asList(chain)));
		return res.toArray(new Certificate[res.size()]);
	}

	public static List<Certificate> sort(List<Certificate> certs) {
		Certificate rt = getRootCertificateCertificate(certs);

		if (rt == null) {
			throw new RuntimeException("Can't find root certificate in chain!");
		}

		ArrayList<Certificate> res = new ArrayList<Certificate>();
		certs.remove(rt);
		res.add(rt);

		while (!certs.isEmpty()) {
			boolean found = false;
			for (Certificate x509Certificate : certs) {
				Principal i = ((X509Certificate) x509Certificate).getIssuerDN();
				if (i.equals(((X509Certificate) rt).getSubjectDN())) {
					rt = x509Certificate;
					found = true;
					break;
				}
			}
			if (found) {
				certs.remove(rt);
				res.add(0, rt);
			} else {
				throw new RuntimeException("Can't find certificate " + ((X509Certificate) rt).getSubjectDN() +
												   " in chain. Verify that all entries are correct and match against each other!");
			}
		}

		return res;
	}

	public static void storeCertificate(String file, CertificateEntry entry)
			throws CertificateEncodingException, IOException {
		log.log(Level.FINEST, "storeCertificate, file: {0}, entry: {1}", new Object[]{file, entry});

		String pemFormat = exportToPemFormat(entry);

		File f = new File(file);
		if (f.exists()) {
			f.renameTo(new File(file + ".bak"));
		}

		FileWriter fw = new FileWriter(f, false);

		fw.write(pemFormat);
		fw.close();
	}

	public static CertCheckResult validateCertificate(Certificate[] chain, KeyStore trustKeystore,
													  boolean revocationEnabled)
			throws NoSuchAlgorithmException, KeyStoreException, InvalidAlgorithmParameterException,
				   CertificateException {
		if (log.isLoggable(Level.FINEST)) {
			log.log(Level.FINEST,
					"Validating cert: {0}, chain size: {1}, trustKeystore size: {2}, revocationEnabled: {3}",
					new Object[]{((X509Certificate) chain[0]).getSubjectDN(), chain.length, trustKeystore.size(),
								 revocationEnabled});
		}
		CertPathValidator certPathValidator = CertPathValidator.getInstance(CertPathValidator.getDefaultType());
		X509CertSelector selector = new X509CertSelector();
		PKIXBuilderParameters params = new PKIXBuilderParameters(trustKeystore, selector);

		params.setRevocationEnabled(false);

		List<Certificate> certList = Arrays.asList(chain);
		CertPath certPath = CertificateFactory.getInstance("X.509").generateCertPath(certList);

		try {
			certPathValidator.validate(certPath, params);

			return CertCheckResult.trusted;
		} catch (CertPathValidatorException ex) {
			if (isExpired((X509Certificate) chain[0])) {
				return CertCheckResult.expired;
			}

			if ((chain.length == 1) && isSelfSigned((X509Certificate) chain[0])) {
				return CertCheckResult.self_signed;
			} else {
				return CertCheckResult.untrusted;
			}
		}
	}

	/**
	 * Method used to verify if certificate if valid for particular domain (if domain matches CN or ALT of certificate)
	 *
	 * @return true if certificate is valid
	 */
	public static boolean verifyCertificateForDomain(X509Certificate cert, String hostname)
			throws CertificateParsingException {
		if (hostname.matches(IPv4_IPv6_PATTERN)) {
			return verifyCertificateForIp(hostname, cert);
		} else {
			return verifyCertificateForHostname(hostname, cert);
		}
	}

	protected static boolean verifyCertificateForHostname(String hostname, X509Certificate x509Certificate)
			throws CertificateParsingException {
		log.log(Level.FINEST, "verifyCertificateForHostname, hostname: {0}, x509Certificate: {1}",
				new Object[]{hostname, x509Certificate});
		boolean altNamePresents = false;
		Collection<List<?>> altNames = x509Certificate.getSubjectAlternativeNames();
		if (altNames != null) {
			for (List<?> entry : altNames) {
				Integer altNameType = (Integer) entry.get(0);
				if (altNameType != 2) {
					continue;
				}
				altNamePresents = true;
				String altName = (String) entry.get(1);
				if (match(hostname, altName)) {
					return true;
				}
			}
		}

		if (!altNamePresents) {
			X500Principal principal = x509Certificate.getSubjectX500Principal();
			String cn = extractCN(principal);
			if (cn != null) {
				return match(hostname, cn);
			}
		}
		return false;
	}

	protected static boolean verifyCertificateForIp(String ipAddr, X509Certificate x509Certificate)
			throws CertificateParsingException {
		log.log(Level.FINEST, "verifyCertificateForIp, ipAddr: {0}, x509Certificate: {1}",
				new Object[]{ipAddr, x509Certificate});
		for (List<?> entry : x509Certificate.getSubjectAlternativeNames()) {
			Integer altNameType = (Integer) entry.get(0);
			if (altNameType != 7) {
				continue;
			}
			String altName = (String) entry.get(1);
			if (ipAddr.equalsIgnoreCase(altName)) {
				return true;
			}
		}
		return false;
	}

	public interface KeyPairSupplier {

		KeyPair get() throws NoSuchAlgorithmException;

	}
}

