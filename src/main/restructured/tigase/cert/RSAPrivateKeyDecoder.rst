.. java:import:: java.io ByteArrayInputStream

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.math BigInteger

.. java:import:: java.security KeyFactory

.. java:import:: java.security NoSuchAlgorithmException

.. java:import:: java.security PrivateKey

.. java:import:: java.security.spec InvalidKeySpecException

.. java:import:: java.security.spec RSAPrivateCrtKeySpec

RSAPrivateKeyDecoder
====================

.. java:package:: tigase.cert
   :noindex:

.. java:type:: public class RSAPrivateKeyDecoder

   Created: Oct 9, 2010 9:16:55 PM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Constructors
------------
RSAPrivateKeyDecoder
^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public RSAPrivateKeyDecoder(byte[] bytes)
   :outertype: RSAPrivateKeyDecoder

RSAPrivateKeyDecoder
^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public RSAPrivateKeyDecoder(InputStream is)
   :outertype: RSAPrivateKeyDecoder

Methods
-------
getKeySpec
^^^^^^^^^^

.. java:method:: public RSAPrivateCrtKeySpec getKeySpec() throws IOException
   :outertype: RSAPrivateKeyDecoder

getPrivateKey
^^^^^^^^^^^^^

.. java:method:: public PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException
   :outertype: RSAPrivateKeyDecoder

