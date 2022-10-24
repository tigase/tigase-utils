.. java:import:: tigase.xml Element

.. java:import:: tigase.xmpp.jid JID

.. java:import:: java.security InvalidKeyException

.. java:import:: java.security NoSuchAlgorithmException

FormSignatureVerifier
=====================

.. java:package:: tigase.form
   :noindex:

.. java:type:: public class FormSignatureVerifier

Constructors
------------
FormSignatureVerifier
^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public FormSignatureVerifier(String oauthConsumerKey, String oauthConsumerSecret)
   :outertype: FormSignatureVerifier

Methods
-------
isFormSigned
^^^^^^^^^^^^

.. java:method:: protected boolean isFormSigned(Form form)
   :outertype: FormSignatureVerifier

verify
^^^^^^

.. java:method:: public long verify(JID to, Element form) throws FormSignerException
   :outertype: FormSignatureVerifier

   Verify signature of given form.

   :param to: the full destination address, including resource, if any.
   :param form: signed Form to verify.
   :throws FormSignerException: if signature is invalid or can't be checked.
   :return: timestamp of signature is signature is valid. If signature is invalid exception will be throwed.

verify
^^^^^^

.. java:method:: public long verify(JID to, Element form, SignatureVerifyHandler handler) throws FormSignerException
   :outertype: FormSignatureVerifier

   Verify signature of given form.

   :param to: the full destination address, including resource, if any.
   :param form: signed Form to verify.
   :param handler: handler to make additional verification (for example validate received \ ``oauth_token``\ ).
   :throws FormSignerException: if signature is invalid or can't be checked.
   :return: timestamp of signature is signature is valid. If signature is invalid exception will be throwed.

verify
^^^^^^

.. java:method:: public long verify(JID to, Form form) throws FormSignerException
   :outertype: FormSignatureVerifier

   Verify signature of given form.

   :param to: the full destination address, including resource, if any.
   :param form: signed Form to verify.
   :throws FormSignerException: if signature is invalid or can't be checked.
   :return: timestamp of signature is signature is valid. If signature is invalid exception will be throwed.

verify
^^^^^^

.. java:method:: public long verify(JID to, Form form, SignatureVerifyHandler handler) throws FormSignerException
   :outertype: FormSignatureVerifier

   Verify signature of given form.

   :param to: the full destination address, including resource, if any.
   :param form: signed Form to verify.
   :param handler: handler to make additional verification (for example validate received \ ``oauth_token``\ ).
   :throws FormSignerException: if signature is invalid or can't be checked.
   :return: timestamp of signature is signature is valid. If signature is invalid exception will be throwed.

