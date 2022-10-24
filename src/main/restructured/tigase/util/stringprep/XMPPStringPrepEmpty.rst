XMPPStringPrepEmpty
===================

.. java:package:: tigase.util.stringprep
   :noindex:

.. java:type:: public class XMPPStringPrepEmpty implements XMPPStringPrepIfc

   Class implementing stringprep processor interface. This is a dummy implementation performing no processing at all. All methods simply return value passed as the method call parameter. Use of this implementation is recommended inly in strictly controlled systems where there is no possibility of getting incorrectly formated JIDs to the system. Of course this implementation causes no impact on the system performance.  Created: Feb 4, 2010 9:52:41 AM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Methods
-------
nameprep
^^^^^^^^

.. java:method:: @Override public String nameprep(String domain)
   :outertype: XMPPStringPrepEmpty

nodeprep
^^^^^^^^

.. java:method:: @Override public String nodeprep(String localpart)
   :outertype: XMPPStringPrepEmpty

resourceprep
^^^^^^^^^^^^

.. java:method:: @Override public String resourceprep(String resource)
   :outertype: XMPPStringPrepEmpty

