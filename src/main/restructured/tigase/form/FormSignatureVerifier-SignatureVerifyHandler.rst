.. java:import:: tigase.xml Element

.. java:import:: tigase.xmpp.jid JID

.. java:import:: java.security InvalidKeyException

.. java:import:: java.security NoSuchAlgorithmException

FormSignatureVerifier.SignatureVerifyHandler
============================================

.. java:package:: tigase.form
   :noindex:

.. java:type:: public interface SignatureVerifyHandler
   :outertype: FormSignatureVerifier

Methods
-------
onFormVerify
^^^^^^^^^^^^

.. java:method::  void onFormVerify(JID to, Form form, SignatureCalculator signatureCalculator) throws FormSignerException
   :outertype: FormSignatureVerifier.SignatureVerifyHandler

