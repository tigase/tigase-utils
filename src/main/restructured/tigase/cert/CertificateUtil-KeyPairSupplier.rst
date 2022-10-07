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

CertificateUtil.KeyPairSupplier
===============================

.. java:package:: tigase.cert
   :noindex:

.. java:type:: public interface KeyPairSupplier
   :outertype: CertificateUtil

Methods
-------
get
^^^

.. java:method::  KeyPair get() throws NoSuchAlgorithmException
   :outertype: CertificateUtil.KeyPairSupplier

