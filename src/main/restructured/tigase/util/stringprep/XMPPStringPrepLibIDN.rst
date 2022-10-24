.. java:import:: gnu.inet.encoding Stringprep

.. java:import:: gnu.inet.encoding StringprepException

XMPPStringPrepLibIDN
====================

.. java:package:: tigase.util.stringprep
   :noindex:

.. java:type::  class XMPPStringPrepLibIDN implements XMPPStringPrepIfc

   Created: Dec 28, 2009 10:04:08 PM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Methods
-------
nameprep
^^^^^^^^

.. java:method:: @Override public String nameprep(String domain) throws TigaseStringprepException
   :outertype: XMPPStringPrepLibIDN

nodeprep
^^^^^^^^

.. java:method:: @Override public String nodeprep(String localpart) throws TigaseStringprepException
   :outertype: XMPPStringPrepLibIDN

resourceprep
^^^^^^^^^^^^

.. java:method:: @Override public String resourceprep(String resource) throws TigaseStringprepException
   :outertype: XMPPStringPrepLibIDN

