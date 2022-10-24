.. java:import:: tigase.util.cache SimpleCache

.. java:import:: javax.naming NamingEnumeration

.. java:import:: javax.naming.directory Attribute

.. java:import:: javax.naming.directory Attributes

.. java:import:: javax.naming.directory DirContext

.. java:import:: javax.naming.directory InitialDirContext

.. java:import:: java.math BigInteger

.. java:import:: java.net InetAddress

.. java:import:: java.net UnknownHostException

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

DNSResolverDefault
==================

.. java:package:: tigase.util.dns
   :noindex:

.. java:type:: public class DNSResolverDefault implements DNSResolverIfc

   DNSResolver class for handling DNS names

Fields
------
ip_cache
^^^^^^^^

.. java:field:: public static Map<String, DNSEntry> ip_cache
   :outertype: DNSResolverDefault

srv_cache
^^^^^^^^^

.. java:field:: public static Map<String, DNSEntry[]> srv_cache
   :outertype: DNSResolverDefault

Constructors
------------
DNSResolverDefault
^^^^^^^^^^^^^^^^^^

.. java:constructor:: protected DNSResolverDefault()
   :outertype: DNSResolverDefault

Methods
-------
getDefaultHost
^^^^^^^^^^^^^^

.. java:method:: @Override public String getDefaultHost()
   :outertype: DNSResolverDefault

getDefaultHosts
^^^^^^^^^^^^^^^

.. java:method:: @Override public String[] getDefaultHosts()
   :outertype: DNSResolverDefault

getHostIPs
^^^^^^^^^^

.. java:method:: @Override public String[] getHostIPs(String hostname) throws UnknownHostException
   :outertype: DNSResolverDefault

   Resolve all IP addresses for the given \ ``hostname``\

   :param hostname: the domain name for which this record is valid
   :return: Array of all \ ``IP addresses``\  on which target host provide service.

getHostSRV_Entries
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public DNSEntry[] getHostSRV_Entries(String hostname, String service, int defPort) throws UnknownHostException
   :outertype: DNSResolverDefault

getPrimaryHost
^^^^^^^^^^^^^^

.. java:method:: public String getPrimaryHost()
   :outertype: DNSResolverDefault

getSecondaryHost
^^^^^^^^^^^^^^^^

.. java:method:: @Override public String getSecondaryHost()
   :outertype: DNSResolverDefault

isHostValid
^^^^^^^^^^^

.. java:method:: protected static boolean isHostValid(String host)
   :outertype: DNSResolverDefault

main
^^^^

.. java:method:: @SuppressWarnings public static void main(String[] args) throws Exception
   :outertype: DNSResolverDefault

   \ ``main``\  method outputting various information about hostnames

   :param args: a \ ``String[]``\  containing domains to query, if none provided default one will be used

setPrimaryHost
^^^^^^^^^^^^^^

.. java:method:: public void setPrimaryHost(String tigasePrimaryHost)
   :outertype: DNSResolverDefault

setSecondaryHost
^^^^^^^^^^^^^^^^

.. java:method:: public void setSecondaryHost(String tigaseSecondaryHost)
   :outertype: DNSResolverDefault

