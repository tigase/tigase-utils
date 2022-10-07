.. java:import:: tigase.annotations TigaseDeprecated

.. java:import:: tigase.util Algorithms

.. java:import:: tigase.util Base64

.. java:import:: javax.crypto Cipher

.. java:import:: javax.security.auth.x500 X500Principal

.. java:import:: java.math BigInteger

.. java:import:: java.security.cert Certificate

.. java:import:: java.security.interfaces RSAPrivateKey

.. java:import:: java.security.spec InvalidKeySpecException

.. java:import:: java.security.spec PKCS8EncodedKeySpec

.. java:import:: java.util.logging ConsoleHandler

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

CertificateUtil
===============

.. java:package:: tigase.cert
   :noindex:

.. java:type:: public abstract class CertificateUtil

   Created: Sep 22, 2010 3:09:01 PM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Fields
------
ID_ON_XMPPADDR
^^^^^^^^^^^^^^

.. java:field:: protected static final byte[] ID_ON_XMPPADDR
   :outertype: CertificateUtil

Methods
-------
createKeyPair
^^^^^^^^^^^^^

.. java:method:: public static KeyPair createKeyPair(int size, String password) throws NoSuchAlgorithmException
   :outertype: CertificateUtil

createSelfSignedCertificate
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Deprecated @TigaseDeprecated public static X509Certificate createSelfSignedCertificate(String email, String domain, String organizationUnit, String organization, String city, String state, String country, KeyPair keyPair) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException
   :outertype: CertificateUtil

createSelfSignedCertificate
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static CertificateEntry createSelfSignedCertificate(String email, String domain, String organizationUnit, String organization, String city, String state, String country, KeyPairSupplier keyPairSupplier) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException
   :outertype: CertificateUtil

exportToPemFormat
^^^^^^^^^^^^^^^^^

.. java:method:: public static String exportToPemFormat(CertificateEntry entry) throws CertificateEncodingException
   :outertype: CertificateUtil

extractCN
^^^^^^^^^

.. java:method:: protected static String extractCN(X500Principal principal)
   :outertype: CertificateUtil

extractXmppAddrs
^^^^^^^^^^^^^^^^

.. java:method:: public static List<String> extractXmppAddrs(X509Certificate x509Certificate)
   :outertype: CertificateUtil

getCertAltCName
^^^^^^^^^^^^^^^

.. java:method:: public static List<String> getCertAltCName(X509Certificate cert)
   :outertype: CertificateUtil

getCertCName
^^^^^^^^^^^^

.. java:method:: public static String getCertCName(X509Certificate cert)
   :outertype: CertificateUtil

getCertificateBasicInfo
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static String getCertificateBasicInfo(Certificate cert)
   :outertype: CertificateUtil

getCertificateBasicInfo
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static StringBuilder getCertificateBasicInfo(StringBuilder sb, Certificate cert)
   :outertype: CertificateUtil

getCertificateFingerprint
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static String getCertificateFingerprint(Certificate cert) throws CertificateEncodingException, NoSuchAlgorithmException
   :outertype: CertificateUtil

getCertificateSerialNumber
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static Optional<BigInteger> getCertificateSerialNumber(Certificate cert)
   :outertype: CertificateUtil

isExpired
^^^^^^^^^

.. java:method:: public static boolean isExpired(X509Certificate cert)
   :outertype: CertificateUtil

isSelfSigned
^^^^^^^^^^^^

.. java:method:: public static boolean isSelfSigned(X509Certificate cert)
   :outertype: CertificateUtil

loadCertificate
^^^^^^^^^^^^^^^

.. java:method:: public static CertificateEntry loadCertificate(File file) throws FileNotFoundException, IOException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException
   :outertype: CertificateUtil

loadCertificate
^^^^^^^^^^^^^^^

.. java:method:: @Deprecated @TigaseDeprecated public static CertificateEntry loadCertificate(byte[] bytes) throws CertificateException, NoSuchProviderException
   :outertype: CertificateUtil

   Loads a certificate from a DER byte buffer.

loadCertificate
^^^^^^^^^^^^^^^

.. java:method:: public static CertificateEntry loadCertificate(String file) throws FileNotFoundException, IOException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException
   :outertype: CertificateUtil

loadPrivateKeyFromDER
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static PrivateKey loadPrivateKeyFromDER(File file) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException
   :outertype: CertificateUtil

main
^^^^

.. java:method:: public static void main(String[] args) throws Exception
   :outertype: CertificateUtil

match
^^^^^

.. java:method:: public static boolean match(String hostname, String altName)
   :outertype: CertificateUtil

   Checks if hostname matches name or wildcard

   :return: true if there is a match

parseCertificate
^^^^^^^^^^^^^^^^

.. java:method:: public static CertificateEntry parseCertificate(Reader data) throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException
   :outertype: CertificateUtil

sort
^^^^

.. java:method:: public static Certificate[] sort(Certificate[] chain)
   :outertype: CertificateUtil

sort
^^^^

.. java:method:: public static List<Certificate> sort(List<Certificate> certs)
   :outertype: CertificateUtil

storeCertificate
^^^^^^^^^^^^^^^^

.. java:method:: public static void storeCertificate(String file, CertificateEntry entry) throws CertificateEncodingException, IOException
   :outertype: CertificateUtil

validateCertificate
^^^^^^^^^^^^^^^^^^^

.. java:method:: public static CertCheckResult validateCertificate(Certificate[] chain, KeyStore trustKeystore, boolean revocationEnabled) throws NoSuchAlgorithmException, KeyStoreException, InvalidAlgorithmParameterException, CertificateException
   :outertype: CertificateUtil

verifyCertificateForDomain
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static boolean verifyCertificateForDomain(X509Certificate cert, String hostname) throws CertificateParsingException
   :outertype: CertificateUtil

   Method used to verify if certificate if valid for particular domain (if domain matches CN or ALT of certificate)

   :return: true if certificate is valid

verifyCertificateForHostname
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: protected static boolean verifyCertificateForHostname(String hostname, X509Certificate x509Certificate) throws CertificateParsingException
   :outertype: CertificateUtil

verifyCertificateForIp
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: protected static boolean verifyCertificateForIp(String ipAddr, X509Certificate x509Certificate) throws CertificateParsingException
   :outertype: CertificateUtil

