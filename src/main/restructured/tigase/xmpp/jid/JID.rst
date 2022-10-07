.. java:import:: tigase.util.stringprep TigaseStringprepException

.. java:import:: java.util Objects

JID
===

.. java:package:: tigase.xmpp.jid
   :noindex:

.. java:type:: public final class JID implements Comparable<JID>

   The class defines an instance of a single XMPP JID identifier. When the object is created all parameters are checked and processed through the stringprep. An exception is thrown in case of a stringprep processing error.   Created: Dec 28, 2009 10:48:04 PM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Methods
-------
compareTo
^^^^^^^^^

.. java:method:: @Override public int compareTo(JID o)
   :outertype: JID

   Method compares the \ ``JID``\  instance with a given object. The implementation fulfills the specification contract and returns a value as you would expect from the call:

   .. parsed-literal::

      jid_1.toString().compareTo(jid_2.toString())

   :param o: is a \ ``JID``\  instance to compare to.
   :return: an integer value which is a result of comparing the two objects.

copyWithResource
^^^^^^^^^^^^^^^^

.. java:method:: public JID copyWithResource(String resource) throws TigaseStringprepException
   :outertype: JID

   The method returns a copy of the \ ``JID``\  instance with a different resource part given as a parameter.

   :param resource: is a \ ``String``\  instance representing JID's new resource part.
   :throws TigaseStringprepException: if resource stringprep processing fails.
   :return: a new instance of the \ ``JID``\  class with a new resource part.

copyWithResourceNS
^^^^^^^^^^^^^^^^^^

.. java:method:: public JID copyWithResourceNS(String resource)
   :outertype: JID

   The method returns a copy of the \ ``JID``\  instance with a different resource part given as a parameter.

   :param resource: is a \ ``String``\  instance representing JID's new resource part.
   :return: a new instance of the \ ``JID``\  class with a new resource part.

copyWithoutResource
^^^^^^^^^^^^^^^^^^^

.. java:method:: public JID copyWithoutResource()
   :outertype: JID

   The method returns a copy of the \ ``JID``\  instance with removed resource part. The result is similar to the \ ``BareJID``\  instance, however there are APIs which require \ ``JID``\  object to use.

   :return: a new instance of the \ ``JID``\  class with removed resource part.

equals
^^^^^^

.. java:method:: @Override public boolean equals(Object b)
   :outertype: JID

   Method compares whether this \ ``JID``\  instance represents the same user JID as the one given in parameter. It returns \ ``true``\  of all: the localpart (nickname), domain part, and the resource part are the same for both objects.

   :param b: is a \ ``JID``\  object to which the instance is compared.
   :return: a \ ``boolean``\  value of \ ``true``\  if both instances represent the same JID and \ ``false``\  otherwise.

getBareJID
^^^^^^^^^^

.. java:method:: public BareJID getBareJID()
   :outertype: JID

   Method returns \ ``BareJID``\  instance for this JID.

   :return: a \ ``BareJID``\  instance.

getDomain
^^^^^^^^^

.. java:method:: public String getDomain()
   :outertype: JID

   Method returns a domain part of the \ ``JID``\  instance.

   :return: a domain part of the \ ``JID``\  instance.

getLocalpart
^^^^^^^^^^^^

.. java:method:: public String getLocalpart()
   :outertype: JID

   Method a localpart (nickname) of the \ ``JID``\  instance.

   :return: a localpart (nickname) of the \ ``JID``\  instance.

getResource
^^^^^^^^^^^

.. java:method:: public String getResource()
   :outertype: JID

   Method a resource part of the \ ``JID``\  instance.

   :return: a resource part of the \ ``JID``\  instance.

hasResource
^^^^^^^^^^^

.. java:method:: public boolean hasResource()
   :outertype: JID

hashCode
^^^^^^^^

.. java:method:: @Override public int hashCode()
   :outertype: JID

   Method returns a hash code calculated for the \ ``JID``\  instance.

   :return: an object hash code.

jidInstance
^^^^^^^^^^^

.. java:method:: public static JID jidInstance(BareJID bareJid, String p_resource) throws TigaseStringprepException
   :outertype: JID

   Constructs a new \ ``JID``\  instance using given \ ``BareJID``\  instance as user bare JID and \ ``String``\  instance as a resource part.  As the \ ``BareJID``\  instances are immutable the constructor doesn't create a copy of the given \ ``BareJID``\ , instead it saves the reference to a given object. The resource parameter is parsed, checked and run through the stringprep processing. In case of stringprep error, an exception is thrown.

   :param bareJid: is a \ ``BareJID``\  instance used to create the \ ``JID``\  instance.
   :param p_resource: is a \ ``String``\  instance representing JID's resource part.
   :throws TigaseStringprepException: exception if there was an error during stringprep processing or null if passed string/domain was null or effectively empty..
   :return: \ ``JID``\  class instance.

jidInstance
^^^^^^^^^^^

.. java:method:: public static JID jidInstance(BareJID bareJid)
   :outertype: JID

   Creates a new \ ``JID``\  instance using given \ ``BareJID``\  instance as a parameter. The resource part is set to null. As the \ ``BareJID``\  instances are immutable the constructor doesn't create a copy of the given \ ``BareJID``\ , instead it saves the reference to a given object.

   :param bareJid: is a \ ``BareJID``\  instance used to create the \ ``JID``\  instance.
   :return: \ ``JID``\  class instance.

jidInstance
^^^^^^^^^^^

.. java:method:: public static JID jidInstance(String jid) throws TigaseStringprepException
   :outertype: JID

   Constructs a new \ ``JID``\  instance using a JID parameter given as a \ ``String``\  instance. The parameter is parsed, checked and run through stringprep processing. An exception is thrown if there is an error while the JID is checked.

   :param jid: a JID parameter given as a \ ``String``\  instance.
   :throws TigaseStringprepException: exception if there was an error during stringprep processing or null if passed string/domain was null or effectively empty..
   :return: \ ``JID``\  class instance.

jidInstance
^^^^^^^^^^^

.. java:method:: public static JID jidInstance(String localpart, String domain) throws TigaseStringprepException
   :outertype: JID

   Constructs a new \ ``JID``\  instance using given \ ``String``\  parameters.  All the \ ``String``\  parameters are parsed, checked and run through the stringprep processing. In case of stringprep error, an exception is thrown.

   :param localpart: is a \ ``String``\  instance representing JID's localpart (nickname) part.
   :param domain: is a \ ``String``\  instance representing JID's domain part.
   :throws TigaseStringprepException: exception if there was an error during stringprep processing or null if passed string/domain was null or effectively empty..
   :return: \ ``JID``\  class instance.

jidInstance
^^^^^^^^^^^

.. java:method:: public static JID jidInstance(String localpart, String domain, String resource) throws TigaseStringprepException
   :outertype: JID

   Constructs a new \ ``JID``\  instance using given \ ``String``\  parameters.  All the \ ``String``\  parameters are parsed, checked and run through the stringprep processing. In case of stringprep error, an exception is thrown.

   :param localpart: is a \ ``String``\  instance representing JID's localpart (nickname) part.
   :param domain: is a \ ``String``\  instance representing JID's domain part.
   :param resource: is a \ ``String``\  instance representing JID's resource part.
   :throws TigaseStringprepException: exception if there was an error during stringprep processing or if passed string/domain was null or effectively empty..
   :return: \ ``JID``\  class instance.

jidInstanceNS
^^^^^^^^^^^^^

.. java:method:: public static JID jidInstanceNS(BareJID bareJid)
   :outertype: JID

   Constructs a new \ ``JID``\  instance using given \ ``BareJID``\  instance as user bare JID and \ ``String``\  instance as a resource part. Note, this method does not perform stringprep processing
   on input parameters.  As the \ ``BareJID``\  instances are immutable the constructor doesn't create a copy of the given \ ``BareJID``\ , instead it saves the reference to a given object.

   :param bareJid: is a \ ``BareJID``\  instance used to create the \ ``JID``\  instance.
   :return: \ ``JID``\  class instance or null if passed string/domain was null or effectively empty.

jidInstanceNS
^^^^^^^^^^^^^

.. java:method:: public static JID jidInstanceNS(BareJID bareJid, String p_resource)
   :outertype: JID

   Constructs a new \ ``JID``\  instance using given \ ``BareJID``\  instance as user bare JID and \ ``String``\  instance as a resource part. Note, this method does not perform stringprep processing
   on input parameters.  As the \ ``BareJID``\  instances are immutable the constructor doesn't create a copy of the given \ ``BareJID``\ , instead it saves the reference to a given object.

   :param bareJid: is a \ ``BareJID``\  instance used to create the \ ``JID``\  instance.
   :param p_resource: is a \ ``String``\  instance representing JID's resource part.
   :return: \ ``JID``\  class instance or null if passed string/domain was null or effectively empty.

jidInstanceNS
^^^^^^^^^^^^^

.. java:method:: public static JID jidInstanceNS(String jid)
   :outertype: JID

   Constructs a new \ ``JID``\  instance using a JID parameter given as a \ ``String``\  instance. Note, this method does not perform stringprep processing on input parameters and it returns
   null if null is passed as parameter. The method does not throw \ ``NullPointerException``\  if the \ ``String``\  passed is null.

   :param jid: a JID parameter given as a \ ``String``\  instance.
   :return: \ ``JID``\  class instance or null.

jidInstanceNS
^^^^^^^^^^^^^

.. java:method:: public static JID jidInstanceNS(String localpart, String domain, String resource)
   :outertype: JID

   Constructs a new \ ``JID``\  instance using given \ ``String``\  parameters. Note, this method
   does not perform stringprep processing on input parameters.

   :param localpart: is a \ ``String``\  instance representing JID's localpart (nickname) part.
   :param domain: is a \ ``String``\  instance representing JID's domain part.
   :param resource: is a \ ``String``\  instance representing JID's resource part.
   :return: \ ``JID``\  class instance.

jidInstanceNS
^^^^^^^^^^^^^

.. java:method:: public static JID jidInstanceNS(String localpart, String domain)
   :outertype: JID

   Constructs a new \ ``JID``\  instance using given \ ``String``\  parameters. Note, this method
   does not perform stringprep processing on input parameters.

   :param localpart: is a \ ``String``\  instance representing JID's localpart (nickname) part.
   :param domain: is a \ ``String``\  instance representing JID's domain part.
   :return: \ ``JID``\  class instance.

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: JID

   Method returns a \ ``String``\  representation of the \ ``JID``\  instance.

   :return: a \ ``String``\  representation of the \ ``JID``\  instance.

