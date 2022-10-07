.. java:import:: java.nio.file Files

.. java:import:: java.nio.file Path

.. java:import:: java.nio.file Paths

.. java:import:: java.security.cert Certificate

.. java:import:: java.security.cert CertificateException

.. java:import:: java.security.cert X509Certificate

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util UUID

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

.. java:import:: java.util.stream Collectors

KeytoolCertificateGenerator
===========================

.. java:package:: tigase.cert
   :noindex:

.. java:type:: public class KeytoolCertificateGenerator implements CertificateGenerator

Methods
-------
canGenerateWildcardSAN
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean canGenerateWildcardSAN()
   :outertype: KeytoolCertificateGenerator

generateSelfSignedCertificate
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public X509Certificate generateSelfSignedCertificate(String email, String domain, String organizationUnit, String organization, String city, String state, String country, KeyPair keyPair) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException
   :outertype: KeytoolCertificateGenerator

generateSelfSignedCertificateEntry
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public CertificateEntry generateSelfSignedCertificateEntry(String email, String domain, String organizationUnit, String organization, String city, String state, String country, KeyPair keyPair) throws GeneralSecurityException, IOException
   :outertype: KeytoolCertificateGenerator

   :param keyPair: is ignored due to `keytool` limitations

