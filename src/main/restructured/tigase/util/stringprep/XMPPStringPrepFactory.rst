.. java:import:: tigase.xmpp.jid BareJID

.. java:import:: tigase.xmpp.jid JID

.. java:import:: java.util.concurrent TimeUnit

XMPPStringPrepFactory
=====================

.. java:package:: tigase.util.stringprep
   :noindex:

.. java:type:: public abstract class XMPPStringPrepFactory

   Created: Dec 28, 2009 10:02:31 PM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Fields
------
STRINGPREP_PROCESSOR
^^^^^^^^^^^^^^^^^^^^

.. java:field:: public static String STRINGPREP_PROCESSOR
   :outertype: XMPPStringPrepFactory

STRINGPREP_PROCESSORS
^^^^^^^^^^^^^^^^^^^^^

.. java:field:: public static final String[] STRINGPREP_PROCESSORS
   :outertype: XMPPStringPrepFactory

STRINGPREP_PROCESSOR_PROP_KEY
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: public static final String STRINGPREP_PROCESSOR_PROP_KEY
   :outertype: XMPPStringPrepFactory

Methods
-------
getDefaultXMPPStringPrep
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static XMPPStringPrepIfc getDefaultXMPPStringPrep()
   :outertype: XMPPStringPrepFactory

   Factory method for creating and returning stringprep implementation used by the Tigase server. This factory allows for pluggable stringprep library usage and replacing the library used by default. The default stringprep processor uses LibIDN library. Very CPU demanding processing. Use with care in open, multi-lingual systems.

   :return: instance of XMPP Stringprep processor.

getEmptyXMPPStringPrep
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static XMPPStringPrepIfc getEmptyXMPPStringPrep()
   :outertype: XMPPStringPrepFactory

   Factory method for creating and returning stringprep implementation used by the Tigase server. This factory allows for pluggable stringprep library usage and replacing the library used by default. The empty stringprep processor does not perform any processing at all. It simply returns the string provided to the method. Recommended only in strictly controlled systems where there is no possibility of incorrectly formated JID getting to the system and the performance is the ke factor. uses simple Java \ ``String``\  processing.

   :return: instance of XMPP Stringprep processor.

getLibIDNXMPPStringPrep
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static XMPPStringPrepIfc getLibIDNXMPPStringPrep()
   :outertype: XMPPStringPrepFactory

   Factory method for creating and returning stringprep implementation used by the Tigase server. This factory allows for pluggable stringprep library usage and replacing the library used by default. The stringprep processor uses LibIDN library. Very CPU demanding processing. Use in open, multi-lingual systems.

   :return: instance of XMPP Stringprep processor.

getSimpleXMPPStringPrep
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static XMPPStringPrepIfc getSimpleXMPPStringPrep()
   :outertype: XMPPStringPrepFactory

   Factory method for creating and returning stringprep implementation used by the Tigase server. This factory allows for pluggable stringprep library usage and replacing the library used by default. The simple stringprep processor uses simple Java \ ``String``\  processing. Recommended in relatively closed, single language systems where there is very low probability for in correct JIDs. Causes very low impact on performance.

   :return: instance of XMPP Stringprep processor.

getXMPPStringPrep
^^^^^^^^^^^^^^^^^

.. java:method:: public static XMPPStringPrepIfc getXMPPStringPrep(String stringprepProcessor)
   :outertype: XMPPStringPrepFactory

main
^^^^

.. java:method:: public static void main(String[] args) throws Exception
   :outertype: XMPPStringPrepFactory

