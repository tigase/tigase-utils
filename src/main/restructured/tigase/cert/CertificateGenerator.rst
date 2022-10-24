.. java:import:: tigase.annotations TigaseDeprecated

.. java:import:: java.io IOException

.. java:import:: java.security.cert CertificateException

.. java:import:: java.security.cert X509Certificate

CertificateGenerator
====================

.. java:package:: tigase.cert
   :noindex:

.. java:type:: public interface CertificateGenerator

Methods
-------
canGenerateWildcardSAN
^^^^^^^^^^^^^^^^^^^^^^

.. java:method::  boolean canGenerateWildcardSAN()
   :outertype: CertificateGenerator

generateSelfSignedCertificate
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Deprecated @TigaseDeprecated  X509Certificate generateSelfSignedCertificate(String email, String domain, String organizationUnit, String organization, String city, String state, String country, KeyPair keyPair) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException
   :outertype: CertificateGenerator

generateSelfSignedCertificateEntry
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method::  CertificateEntry generateSelfSignedCertificateEntry(String email, String domain, String organizationUnit, String organization, String city, String state, String country, KeyPair keyPair) throws GeneralSecurityException, IOException
   :outertype: CertificateGenerator

