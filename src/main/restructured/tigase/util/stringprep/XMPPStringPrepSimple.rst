.. java:import:: java.util.regex Pattern

XMPPStringPrepSimple
====================

.. java:package:: tigase.util.stringprep
   :noindex:

.. java:type:: public class XMPPStringPrepSimple implements XMPPStringPrepIfc

   Created: Feb 4, 2010 9:31:23 AM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Methods
-------
nameprep
^^^^^^^^

.. java:method:: @Override public String nameprep(String domain) throws TigaseStringprepException
   :outertype: XMPPStringPrepSimple

nodeprep
^^^^^^^^

.. java:method:: @Override public String nodeprep(String localpart) throws TigaseStringprepException
   :outertype: XMPPStringPrepSimple

resourceprep
^^^^^^^^^^^^

.. java:method:: @Override public String resourceprep(String resource) throws TigaseStringprepException
   :outertype: XMPPStringPrepSimple

