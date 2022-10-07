.. java:import:: java.security KeyPair

.. java:import:: java.security PrivateKey

.. java:import:: java.security.cert Certificate

.. java:import:: java.util Optional

CertificateEntry
================

.. java:package:: tigase.cert
   :noindex:

.. java:type:: public class CertificateEntry

   Created: Oct 9, 2010 5:08:30 PM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Constructors
------------
CertificateEntry
^^^^^^^^^^^^^^^^

.. java:constructor:: public CertificateEntry()
   :outertype: CertificateEntry

CertificateEntry
^^^^^^^^^^^^^^^^

.. java:constructor:: public CertificateEntry(Certificate[] chain, KeyPair keyPair)
   :outertype: CertificateEntry

CertificateEntry
^^^^^^^^^^^^^^^^

.. java:constructor:: public CertificateEntry(Certificate[] chain, PrivateKey privateKey)
   :outertype: CertificateEntry

Methods
-------
getCertChain
^^^^^^^^^^^^

.. java:method:: public Certificate[] getCertChain()
   :outertype: CertificateEntry

getCertificate
^^^^^^^^^^^^^^

.. java:method:: public Optional<Certificate> getCertificate()
   :outertype: CertificateEntry

getKeyPair
^^^^^^^^^^

.. java:method:: public Optional<KeyPair> getKeyPair()
   :outertype: CertificateEntry

getPrivateKey
^^^^^^^^^^^^^

.. java:method:: public PrivateKey getPrivateKey()
   :outertype: CertificateEntry

setCertChain
^^^^^^^^^^^^

.. java:method:: public void setCertChain(Certificate[] chain)
   :outertype: CertificateEntry

setPrivateKey
^^^^^^^^^^^^^

.. java:method:: public void setPrivateKey(PrivateKey privateKey)
   :outertype: CertificateEntry

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: CertificateEntry

toString
^^^^^^^^

.. java:method:: public String toString(boolean basic)
   :outertype: CertificateEntry

