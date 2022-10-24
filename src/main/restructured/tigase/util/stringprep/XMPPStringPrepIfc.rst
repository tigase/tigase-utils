XMPPStringPrepIfc
=================

.. java:package:: tigase.util.stringprep
   :noindex:

.. java:type:: public interface XMPPStringPrepIfc

   Interface defining stringprep processor API. According to RFC-3920 all JID's parts have to be stringprep processed, each part should go through different algorithm. The interface and XMPPStringPrepFactory allow for pluggable stringprep processor implementation. See the factory documentation for more details. Created: Dec 28, 2009 9:57:43 PM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Methods
-------
nameprep
^^^^^^^^

.. java:method::  String nameprep(String domain) throws TigaseStringprepException
   :outertype: XMPPStringPrepIfc

   JID's domain part stringprep processing. In RFC-3920 this is called nameprep processing.

   :param domain: a \ ``String``\  value of the JID's domain part.
   :throws TigaseStringprepException: if stringprep exception occurs.
   :return: nameprep processed JID's doman part.

nodeprep
^^^^^^^^

.. java:method::  String nodeprep(String localpart) throws TigaseStringprepException
   :outertype: XMPPStringPrepIfc

   JID's localpart (or nick name) stringprep processing. In RFC-3920 this is called nodeprep processing.

   :param localpart: a \ ``String``\  value of the JID's localpart.
   :throws TigaseStringprepException: if stringprep exception occurs.
   :return: nodeprep processed JID's localpart.

resourceprep
^^^^^^^^^^^^

.. java:method::  String resourceprep(String resource) throws TigaseStringprepException
   :outertype: XMPPStringPrepIfc

   JID's resource stringprep proessing. In RFC-3920 this is called resourceprep processing.

   :param resource: a \ ``String``\  value of the JID's resource part.
   :throws TigaseStringprepException: if stringprep exception occurs.
   :return: resourceprep processed JID's resource part.

