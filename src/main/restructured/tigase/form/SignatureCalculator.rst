.. java:import:: tigase.util Base64

.. java:import:: tigase.xmpp.jid JID

.. java:import:: javax.crypto Mac

.. java:import:: javax.crypto SecretKey

.. java:import:: javax.crypto.spec SecretKeySpec

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.net URLEncoder

.. java:import:: java.security InvalidKeyException

.. java:import:: java.security MessageDigest

.. java:import:: java.security NoSuchAlgorithmException

.. java:import:: java.security SecureRandom

SignatureCalculator
===================

.. java:package:: tigase.form
   :noindex:

.. java:type:: public class SignatureCalculator

Fields
------
SUPPORTED_TYPE
^^^^^^^^^^^^^^

.. java:field:: public static final String SUPPORTED_TYPE
   :outertype: SignatureCalculator

Constructors
------------
SignatureCalculator
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public SignatureCalculator()
   :outertype: SignatureCalculator

SignatureCalculator
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public SignatureCalculator(String oauthConsumerKey, String oauthConsumerSecret)
   :outertype: SignatureCalculator

SignatureCalculator
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public SignatureCalculator(String oauthToken, String oauthTokenSecret, String oauthConsumerKey, String oauthConsumerSecret)
   :outertype: SignatureCalculator

Methods
-------
addEmptyFields
^^^^^^^^^^^^^^

.. java:method:: public void addEmptyFields(Form form)
   :outertype: SignatureCalculator

calculateSignature
^^^^^^^^^^^^^^^^^^

.. java:method:: public String calculateSignature(JID to, Form form) throws InvalidKeyException, NoSuchAlgorithmException
   :outertype: SignatureCalculator

   Calculate signature of given form. Form will not be changed.

   :param to: the full destination address, including resource, if any.
   :param form: form to sign.
   :return: Signature of form.

escape
^^^^^^

.. java:method:: protected static String escape(String s)
   :outertype: SignatureCalculator

getOauthConsumerKey
^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getOauthConsumerKey()
   :outertype: SignatureCalculator

   :return: the oauthConsumerKey

getOauthConsumerSecret
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getOauthConsumerSecret()
   :outertype: SignatureCalculator

   :return: the oauthConsumerSecret

getOauthToken
^^^^^^^^^^^^^

.. java:method:: public String getOauthToken()
   :outertype: SignatureCalculator

   :return: the oauthToken

getOauthTokenSecret
^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getOauthTokenSecret()
   :outertype: SignatureCalculator

   :return: the oauthTokenSecret

h
^

.. java:method:: protected byte[] h(byte[] data) throws NoSuchAlgorithmException
   :outertype: SignatureCalculator

hmac
^^^^

.. java:method:: protected static byte[] hmac(SecretKey key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException
   :outertype: SignatureCalculator

isMethodSupported
^^^^^^^^^^^^^^^^^

.. java:method:: public boolean isMethodSupported(String fOauthSignatureMethod)
   :outertype: SignatureCalculator

key
^^^

.. java:method:: protected SecretKey key(byte[] key)
   :outertype: SignatureCalculator

randomString
^^^^^^^^^^^^

.. java:method:: protected String randomString()
   :outertype: SignatureCalculator

setOauthConsumerKey
^^^^^^^^^^^^^^^^^^^

.. java:method:: public void setOauthConsumerKey(String oauthConsumerKey)
   :outertype: SignatureCalculator

   :param oauthConsumerKey: the oauthConsumerKey to set

setOauthConsumerSecret
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void setOauthConsumerSecret(String oauthConsumerSecret)
   :outertype: SignatureCalculator

   :param oauthConsumerSecret: the oauthConsumerSecret to set

setOauthToken
^^^^^^^^^^^^^

.. java:method:: public void setOauthToken(String oauthToken)
   :outertype: SignatureCalculator

   :param oauthToken: the oauthToken to set

setOauthTokenSecret
^^^^^^^^^^^^^^^^^^^

.. java:method:: public void setOauthTokenSecret(String oauthTokenSecret)
   :outertype: SignatureCalculator

   :param oauthTokenSecret: the oauthTokenSecret to set

sign
^^^^

.. java:method:: public void sign(JID to, Form form) throws InvalidKeyException, NoSuchAlgorithmException
   :outertype: SignatureCalculator

   Sign given form with current time. Signature will be added to form.

   :param to: the full destination address, including resource, if any.
   :param form: form to sign.

sign
^^^^

.. java:method:: public void sign(JID to, String nonce, long timestamp, Form form) throws InvalidKeyException, NoSuchAlgorithmException
   :outertype: SignatureCalculator

   Sign given Form. Signature will be added to form.

   :param to: the full destination address, including resource, if any.
   :param nonce: A nonce value that the client has to set. Can be a random alphanumerical string.
   :param timestamp: Number of seconds since 1st of January 1970, 00:00:00 GMT. The client has to set this at the time of signature.
   :param form: form to sign.

