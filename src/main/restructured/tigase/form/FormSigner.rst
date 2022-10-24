.. java:import:: tigase.xmpp.jid JID

.. java:import:: java.security InvalidKeyException

.. java:import:: java.security NoSuchAlgorithmException

FormSigner
==========

.. java:package:: tigase.form
   :noindex:

.. java:type:: public class FormSigner

Constructors
------------
FormSigner
^^^^^^^^^^

.. java:constructor:: public FormSigner(String oauthToken, String oauthTokenSecret, String oauthConsumerKey, String oauthConsumerSecret)
   :outertype: FormSigner

Methods
-------
signForm
^^^^^^^^

.. java:method:: public void signForm(JID to, Form form) throws FormSignerException
   :outertype: FormSigner

   Sign given form with current time. Signature will be added to form.

   :param to: the full destination address, including resource, if any.
   :param form: form form to sign.
   :throws FormSignerException: when the key is invalid or the algorithm is not supported

