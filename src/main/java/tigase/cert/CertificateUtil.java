/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2014 "Tigase, Inc." <office@tigase.com>
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
 *
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */
package tigase.cert;

//~--- non-JDK imports --------------------------------------------------------

import tigase.util.Algorithms;
import tigase.util.Base64;

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;
import sun.security.x509.*;
//~--- JDK imports ------------------------------------------------------------

//~--- classes ----------------------------------------------------------------

/**
 * Created: Sep 22, 2010 3:09:01 PM
 * 
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public abstract class CertificateUtil {
	private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
	private static final String BEGIN_KEY = "-----BEGIN PRIVATE KEY-----";
	private static final String BEGIN_RSA_KEY = "-----BEGIN RSA PRIVATE KEY-----";
	private static final String ENCRIPT_TEST = "--encript-test";
	private static final String ENCRIPT_TEST_SHORT = "-et";
	private static final String END_CERT = "-----END CERTIFICATE-----";
	private static final String END_KEY = "-----END PRIVATE KEY-----";
	private static final String END_RSA_KEY = "-----END RSA PRIVATE KEY-----";
	protected static final byte[] ID_ON_XMPPADDR = new byte[] { 0x06, 0x08, 0x2B, 0x06, 0x01, 0x05, 0x05, 0x07, 0x08, 0x05 };
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

	private static final String STORE_CERT_SHORT = "-sc";

	private static void appendName(StringBuilder sb, String prefix, String value) {
		if (value != null) {
			if (sb.length() > 0) {
				sb.append(", ");
			}

			sb.append(prefix).append('=').append(value);
		}
	}

	private static int calculateLength(byte[] buffer, int start) throws ArrayIndexOutOfBoundsException {
		int offset = start + 1;
		int b = (buffer[offset] & 0xff);
		if (b < 0x80)
			return (b);
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
		int b = (buffer[(offset + 1)] & 0xff);
		if (b < 0x80)
			return (offset + 2);
		int len = b - 0x80;
		return (offset + len + 2);
	}

	/**
	 * Method description
	 * 
	 * 
	 * 
	 * @param size
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair createKeyPair(int size, String password) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

		keyPairGenerator.initialize(size);

		KeyPair keyPair = keyPairGenerator.genKeyPair();

		return keyPair;
	}

	/**
	 * Method description
	 * 
	 * 
	 * 
	 * @param email
	 * @param domain
	 * @param organizationUnit
	 * @param organization
	 * @param city
	 * @param state
	 * @param country
	 * @param keyPair
	 * @return
	 * 
	 * @throws CertificateException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchProviderException
	 * @throws SignatureException
	 */
	public static X509Certificate createSelfSignedCertificate(String email, String domain, String organizationUnit,
			String organization, String city, String state, String country, KeyPair keyPair) throws CertificateException,
			IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
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
		appendName(subject, "CN", "*." + domain);
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

		return newCert;
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

	/**
	 * Method description
	 * 
	 * 
	 * @param entry
	 * 
	 * @return
	 * 
	 * @throws CertificateEncodingException
	 */
	public static String exportToPemFormat(CertificateEntry entry) throws CertificateEncodingException {
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

		return sb.toString();
	}

	// ~--- get methods
	// ----------------------------------------------------------

	private static String extractValue(byte[] buffer, byte[] id) {
		try {
			if (buffer[0] != 0x30)
				return null;
			int len = calculateLength(buffer, 0);
			int offset = calculateOffset(buffer, 0);

			for (int i = 0; i < id.length; i++) {
				int j = offset + i;
				if (j >= len)
					return null;
				if (id[i] != buffer[j])
					return null;
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
		final ArrayList<String> result = new ArrayList<String>();
		try {
			Collection<List<?>> altNames = x509Certificate.getSubjectAlternativeNames();
			if (altNames == null)
				return result;

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
			return result;
		} catch (Exception e) {
			return result;
		}

	}

	/**
	 * Method description
	 *
	 *
	 * @param cert
	 *
	 * @return
	 */
	public static ArrayList<String> getCertAltCName( X509Certificate cert ) {
		try {
			ArrayList<String> result = new ArrayList<>();
			Collection<List<?>> subjectAlternativeNames = cert.getSubjectAlternativeNames();

			for ( List list : subjectAlternativeNames ) {
				// we are only interested in dNSName
				if ( list.get( 0 ).equals( 2 ) ){
					result.add( list.get( 1 ).toString() );
				}
			}
			return result;
		} catch ( CertificateParsingException e ) {
			return null;
		}
	}


	/**
	 * Method description
	 * 
	 * 
	 * @param cert
	 * 
	 * @return
	 */
	public static String getCertCName(X509Certificate cert) {
		X500Principal princ = cert.getSubjectX500Principal();
		String name = princ.getName();
		String[] all = name.split(",");

		for (String n : all) {
			String[] ns = n.trim().split("=");

			if (ns[0].equals("CN")) {
				return ns[1];
			}
		}

		return null;
	}

	// ~--- methods
	// --------------------------------------------------------------

	/**
	 * Method description
	 * 
	 * 
	 * @param cert
	 * 
	 * @return
	 */
	public static boolean isExpired(X509Certificate cert) {
		try {
			cert.checkValidity();

			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param cert
	 * 
	 * @return
	 */
	public static boolean isSelfSigned(X509Certificate cert) {
		return cert.getIssuerDN().equals(cert.getSubjectDN());
	}

	private static void keyPairTest() throws Exception {

		// KeyPair test:
		// 1. Generating key pair:
		System.out.print("Generating key pair...");
		System.out.flush();

		KeyPair keyPair = createKeyPair(1024, "secret");

		System.out.println(" done, private key: " + keyPair.getPrivate() + ", public key: " + keyPair.getPublic());
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param file
	 * 
	 * @return
	 * 
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static CertificateEntry loadCertificate(File file) throws FileNotFoundException, IOException, CertificateException,
			NoSuchAlgorithmException, InvalidKeySpecException {
		return parseCertificate(new FileReader(file));
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param file
	 * 
	 * @return
	 * 
	 * 
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static CertificateEntry loadCertificate(String file) throws FileNotFoundException, IOException,
			CertificateException, NoSuchAlgorithmException, InvalidKeySpecException {
		return loadCertificate(new File(file));
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param file
	 * 
	 * @return
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static PrivateKey loadPrivateKeyFromDER(File file) throws FileNotFoundException, IOException,
			NoSuchAlgorithmException, InvalidKeySpecException {
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		byte[] privKeyBytes = new byte[(int) file.length()];

		dis.read(privKeyBytes);
		dis.close();

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privKeyBytes);
		RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privSpec);

		return privKey;
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param args
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
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

				System.out.println(ce.toString());
			}

			if (args[0].equals(STORE_CERT) || args[0].equals(STORE_CERT_SHORT)) {
				String file = args[1];

				// Certificate
				String email = "artur.hefczyc@tigase.org";
				String domain = "tigase.org";
				String ou = "XMPP Service";
				String o = "Tigase.org";
				String l = "Cambourne";
				String st = "Cambridgeshire";
				String c = "UK";
				KeyPair keyPair = createKeyPair(1024, "secret");
				X509Certificate cert = createSelfSignedCertificate(email, domain, ou, o, l, st, c, keyPair);
				CertificateEntry entry = new CertificateEntry();

				entry.setPrivateKey(keyPair.getPrivate());
				entry.setCertChain(new Certificate[] { cert });
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
	 * Method description
	 * 
	 * 
	 * @param data
	 * 
	 * @return
	 * 
	 * @throws CertificateException
	 * @throws IOException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static CertificateEntry parseCertificate(Reader data) throws IOException, CertificateException,
			NoSuchAlgorithmException, InvalidKeySpecException {
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

					certs.add((X509Certificate) cert);
				}

				sb = new StringBuilder(4096);
			} else if (line.contains(END_KEY)) {
				addToBuffer = false;
				byte[] bytes = Base64.decode(sb.toString());
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");

				privateKey = keyFactory.generatePrivate(keySpec);
				sb = new StringBuilder(4096);
			} else if (line.contains(END_RSA_KEY)) {
				addToBuffer = false;
				byte[] bytes = Base64.decode(sb.toString());
				RSAPrivateKeyDecoder decoder = new RSAPrivateKeyDecoder(bytes);

				privateKey = decoder.getPrivateKey();
				sb = new StringBuilder(4096);
			} else if (addToBuffer)
				sb.append(line);

		}

		CertificateEntry entry = new CertificateEntry();

		entry.setCertChain(certs.toArray(new Certificate[certs.size()]));
		entry.setPrivateKey(privateKey);

		return entry;
	}

	public static Certificate[] sort(Certificate[] chain) {
		List<Certificate> res = sort(new ArrayList<Certificate>(Arrays.asList(chain)));
		return res.toArray(new Certificate[res.size()]);
	}

	public static List<Certificate> sort(List<Certificate> certs) {
		Certificate rt = null;
		for (Certificate x509Certificate : certs) {
			Principal i = ((X509Certificate) x509Certificate).getIssuerDN();
			Principal s = ((X509Certificate) x509Certificate).getSubjectDN();
			if (i.equals(s))
				rt = x509Certificate;
		}

		if (rt == null)
			throw new RuntimeException("Can't find root certificate in chain!");

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
				throw new RuntimeException("Can't find certificate " + ((X509Certificate) rt).getSubjectDN()
						+ " in chain. Verify that all entries are correct and match against each other!");
			}
		}

		return res;
	}

	private static void printHelp() {
		System.out.println(CertificateUtil.class.getName() + " test code.");
		System.out.println("You can run following tests:");
		System.out.println(" " + PRINT_PROVIDERS + " | " + PRINT_PROVIDERS_SHORT + " - prints all supported providers");
		System.out.println(" " + PRINT_SERVICES + " | " + PRINT_SERVICES_SHORT + " - print all supported services");
		System.out.println(" " + KEY_PAIR + " | " + KEY_PAIR_SHORT + " - generate a key pair and print the result");
		System.out.println(" " + ENCRIPT_TEST + " | " + ENCRIPT_TEST_SHORT
				+ " - encript simple text with public key, decript with private");
		System.out.println(" " + SELF_SIGNED_CERT + " | " + SELF_SIGNED_CERT_SHORT + " - generate self signed certificate");
		System.out.println(" " + LOAD_CERT + " file.pem | " + LOAD_CERT_SHORT + " file.pem - load certificate from file");
		System.out.println(" " + STORE_CERT + " file.pem | " + STORE_CERT_SHORT
				+ " file.pem - generate self-signed certificate and save it to the given pem file");
		System.out.println(" " + LOAD_DER_PRIVATE_KEY + " | " + LOAD_DER_PRIVATE_KEY_SHORT
				+ " file.der - load private key from DER file.");
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param includeServices
	 */
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

		X509Certificate cert = createSelfSignedCertificate(email, domain, ou, o, l, st, c, keyPair);

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
		cert.verify(keyPair.getPublic());
		System.out.println(" done.");
		System.out.println(cert.toString());
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param file
	 * @param entry
	 * 
	 * @throws CertificateEncodingException
	 * @throws IOException
	 */
	public static void storeCertificate(String file, CertificateEntry entry) throws CertificateEncodingException, IOException {
		String pemFormat = exportToPemFormat(entry);

		File f = new File(file);
		if (f.exists())
			f.renameTo(new File(file + ".bak"));

		FileWriter fw = new FileWriter(f, false);

		fw.write(pemFormat);
		fw.close();
	}

	/**
	 * Method description
	 * 
	 * 
	 * 
	 * @param chain
	 * @param revocationEnabled
	 * 
	 * @param trustKeystore
	 * @return
	 * 
	 * 
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static CertCheckResult validateCertificate(Certificate[] chain, KeyStore trustKeystore, boolean revocationEnabled)
			throws NoSuchAlgorithmException, KeyStoreException, InvalidAlgorithmParameterException, CertificateException {
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
}

// ~ Formatted in Sun Code Convention

// ~ Formatted by Jindent --- http://www.jindent.com
