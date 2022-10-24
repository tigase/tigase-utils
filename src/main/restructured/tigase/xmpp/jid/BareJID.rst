.. java:import:: tigase.util.stringprep TigaseStringprepException

.. java:import:: tigase.util.stringprep XMPPStringPrepFactory

.. java:import:: tigase.util.stringprep XMPPStringPrepIfc

BareJID
=======

.. java:package:: tigase.xmpp.jid
   :noindex:

.. java:type:: public final class BareJID implements Comparable<BareJID>

   Instance of the \ ``BareJID``\  class holds a single, bare JID. When the object is created the parameters are checked and processed through the stringprep. An exception is throw in case of stringprep processing error. The instances of the class are immutable objects.  There are planed various optimisations for the class implementation, one of them is to allow for comparing the instanced by references ('==') instead of of equals() method. This is to improve the performance and lower memory usage. Other improvements and optimisations are also planed. Some of the optimisations require that the instances are created in strictly controlled way, hence there is no public constructor and you have to use factory methods to create a new instance of the \ ``BareJID``\  class.  The class also offers a few utility methods for parsing and constructing JID strings. Please see JavaDoc documentation for more details.  Created: Dec 28, 2009 10:47:51 PM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Fields
------
stringPrep
^^^^^^^^^^

.. java:field:: static XMPPStringPrepIfc stringPrep
   :outertype: BareJID

Methods
-------
bareJIDInstance
^^^^^^^^^^^^^^^

.. java:method:: public static BareJID bareJIDInstance(String jid) throws TigaseStringprepException
   :outertype: BareJID

   The method creates an instance of the \ ``BareJID``\  class. The bare JID is parsed and constructed from the \ ``String``\  parameter.  The localpart (nick name) and the domain part of the JID are parsed and processed through the stringprep. If a strngprep parsing error occurs an exception is thrown. The resource part, if provided in the JID paramater, is thrown away.  Please note, the method does not necessarily has to return a new instance of the class. It may return the same exact object every time you pass parameters which refer to the same bare JID identifier.

   :param jid: is a \ ``String``\  parameter used to create the bare JID instance.
   :throws TigaseStringprepException: if the stringprep processing fails for any JID part used to create the instance.
   :return: an instance of the \ ``BareJID``\  class which corresponds to the JID given as the parameter.

bareJIDInstance
^^^^^^^^^^^^^^^

.. java:method:: public static BareJID bareJIDInstance(String p_localpart, String p_domain) throws TigaseStringprepException
   :outertype: BareJID

   The method creates an instance of the \ ``BareJID``\  class. The bare JID is parsed and constructed from two \ ``String``\  parameters.  The localpart (nick name) and the domain part of the JID are parsed and processed through the stringprep. If a strngprep parsing error occurs an exception is thrown. Please note, the method does not necessarily has to return a new instance of the class. It may return the same exact object every time you pass parameters which refer to the same bare JID identifier.

   :param p_localpart: is a \ ``String``\  parameter assumed to be a JID localpart (nickname) and used to create the bare JID instance. The localpart parameter can be null.
   :param p_domain: is a \ ``String``\  parameter assumed to be a JID domain part and used to create the bare JID instance. This parameter must not be null.
   :throws TigaseStringprepException: if the stringprep processing fails for any JID part used to create the instance.
   :return: an instance of the \ ``BareJID``\  class which corresponds to the JID given as the parameter.

bareJIDInstanceNS
^^^^^^^^^^^^^^^^^

.. java:method:: public static BareJID bareJIDInstanceNS(String jid)
   :outertype: BareJID

   The method creates an instance of the \ ``BareJID``\  class. The bare JID is parsed and constructed from the \ ``String``\  parameter. Note, this method does not perform stringprep processing on input
   parameters.  The resource part, if provided in the JID paramater, is thrown away.  Please note, the method does not necessarily has to return a new instance of the class. It may return the same exact object every time you pass parameters which refer to the same bare JID identifier.

   :param jid: is a \ ``String``\  parameter used to create the bare JID instance.
   :return: an instance of the \ ``BareJID``\  class which corresponds to the JID given as the parameter or null if passed string/domain was null or effectively empty.

bareJIDInstanceNS
^^^^^^^^^^^^^^^^^

.. java:method:: public static BareJID bareJIDInstanceNS(String p_localpart, String p_domain)
   :outertype: BareJID

   The method creates an instance of the \ ``BareJID``\  class. The bare JID is parsed and constructed from two \ ``String``\  parameters. Note, this method does not perform stringprep processing on input
   parameters.  Please note, the method does not necessarily has to return a new instance of the class. It may return the same exact object every time you pass parameters which refer to the same bare JID identifier.

   :param p_localpart: is a \ ``String``\  parameter assumed to be a JID localpart (nickname) and used to create the bare JID instance. The localpart parameter can be null.
   :param p_domain: is a \ ``String``\  parameter assumed to be a JID domain part and used to create the bare JID instance. This parameter must not be null.
   :return: an instance of the \ ``BareJID``\  class which corresponds to the JID given as the parameter or null if passed domain was null or effectively empty.

compareTo
^^^^^^^^^

.. java:method:: @Override public int compareTo(BareJID o)
   :outertype: BareJID

   Method compares the \ ``BareJID``\  instance to the object given as a parameter. The method implements the \ ``compareTo``\  specification contract and returns values as we would expect from the call:

   .. parsed-literal::

      bareJID_1.toString().compareTo(bareJID_2.toString())

   :param o: is an \ ``BareJID``\  instance with which the comparision is performed.
   :return: values as we would expect from the call:

   .. parsed-literal::

      bareJID_1.toString().compareTo(bareJID_2.toString())

equals
^^^^^^

.. java:method:: @Override public boolean equals(Object b)
   :outertype: BareJID

   Method compares whether this \ ``BareJID``\  instance represents the same user bare JID as the one given in parameter. It returns \ ``true``\  of both the localpart (nickname) and domain part are the same for both objects.

   :param b: is a \ ``BareJID``\  object to which the instance is compared.
   :return: a \ ``boolean``\  value of \ ``true``\  if both instances represent the same bare JID and \ ``false``\  otherwise.

getDomain
^^^^^^^^^

.. java:method:: public String getDomain()
   :outertype: BareJID

   Method returns a domain part of the \ ``BareJID``\ . Please note the \ ``String``\  returned is parsed, checked and processed via stringprep, hence it represents a valid domain name as defined in XMPP RFC.

   :return: a domain part of the \ ``BareJID``\  instance.

getLocalpart
^^^^^^^^^^^^

.. java:method:: public String getLocalpart()
   :outertype: BareJID

   Method returns a localpart (nickname) of the \ ``BareJID``\ . Please note the \ ``String``\  returned is parsed, checked and processed via stringprep, hence it represents a valid localpart as defined in XMPP RFC.

   :return: a localpart (nickname) of the \ ``BareJID``\  instance.

hashCode
^^^^^^^^

.. java:method:: @Override public int hashCode()
   :outertype: BareJID

   Method returns a hash code calculated for the \ ``BareJID``\  instance.

   :return: an object hash code.

jidToBareJID
^^^^^^^^^^^^

.. java:method:: public static String jidToBareJID(String jid)
   :outertype: BareJID

   A utility method to strip the resource part from the given JID string. The method doesn't perform any checkings and it doesn't run stringprep processing on the given parameter. This is a pure string manipulation utility method.

   :param jid: is a \ ``String``\  representing user full JID.
   :return: a new \ ``String``\  instance of the JID wihout resource part.

parseJID
^^^^^^^^

.. java:method:: public static String[] parseJID(String jid)
   :outertype: BareJID

   A utility method to parse and split the given JID string into separate parts. The result is returned as a three elements' \ ``String``\  array:

   ..

   #. The first element (index 0) of the array is the JID's localpart (nickname). Can be null.
   #. The second element (index 1) of the array is the JID's domain part.
   #. The third element (index 2) of the array is the JID's resource part. Can be null.

   The method doesn't perform any checkings and it doesn't run stringprep processing on the given parameter. This is a pure string manipulation utility method.

   :param jid: is a \ ``String``\  representing user full JID.
   :return: a three element \ ``String``\  array with parsed JID parts. The array may contain null elements.

toString
^^^^^^^^

.. java:method:: public static String toString(String p_localpart, String p_domain)
   :outertype: BareJID

   A utility method to construct a \ ``String``\  representing user JID from given parameters. The method doesn't perform any checkings and it doesn't run stringprep processing on the given parameter. This is a pure string manipulation utility method.

   :param p_localpart: is a JID's localpart (nickname)
   :param p_domain: is a JID's domain part.
   :return: a new \ ``String``\  representing user's JID build from given parameters. If the localpart is null then the method simply returns parameter given as domain part.

toString
^^^^^^^^

.. java:method:: public static String toString(String p_localpart, String p_domain, String p_resource)
   :outertype: BareJID

   A utility method to construct a \ ``String``\  representing user JID from given parameters. The method doesn't perform any checkings and it doesn't run stringprep processing on the given parameter. This is a pure string manipulation utility method.

   :param p_localpart: is a JID's localpart (nickname)
   :param p_domain: is a JID's domain part.
   :param p_resource: is a JID's resource part.
   :return: a new \ ``String``\  representing user's JID build from given parameters. If the localpart and resource part is null then the method simply returns parameter given as domain part.

toString
^^^^^^^^

.. java:method:: public static String toString(BareJID bareJid, String p_resource)
   :outertype: BareJID

   A utility method to construct a \ ``String``\  representing user JID from given parameters. The method doesn't perform any checkings and it doesn't run stringprep processing on the resource parameter.

   :param bareJid: is a \ ``BareJID``\  instance.
   :param p_resource: is a JID's resource part.
   :return: a new \ ``String``\  representing user's JID build from given parameters.

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: BareJID

   Method returns a \ ``String``\  representation of the \ ``BareJID``\  instance.

   :return: a \ ``String``\  representation of the \ ``BareJID``\  instance.

useStringprepProcessor
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static void useStringprepProcessor(String stringprepProcessor)
   :outertype: BareJID

   Changes stringprep processor implementation used for the JID checking. The method can be called at any time to change used processor. All subsequent \ ``JID``\  and \ ``BareJID``\  instances are created using a new processor.

   :param stringprepProcessor: is a \ ``String``\  value with stringprep processor name or class name implementing stringprep processing interface.

