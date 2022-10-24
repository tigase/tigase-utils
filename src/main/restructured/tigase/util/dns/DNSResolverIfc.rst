.. java:import:: javax.naming NamingException

.. java:import:: javax.naming.directory Attribute

.. java:import:: javax.naming.directory Attributes

.. java:import:: javax.naming.directory DirContext

.. java:import:: javax.naming.directory InitialDirContext

.. java:import:: java.net UnknownHostException

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

DNSResolverIfc
==============

.. java:package:: tigase.util.dns
   :noindex:

.. java:type:: public interface DNSResolverIfc

Fields
------
TIGASE_PRIMARY_ADDRESS
^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String TIGASE_PRIMARY_ADDRESS
   :outertype: DNSResolverIfc

TIGASE_SECONDARY_ADDRESS
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String TIGASE_SECONDARY_ADDRESS
   :outertype: DNSResolverIfc

log
^^^

.. java:field:: static final Logger log
   :outertype: DNSResolverIfc

rand
^^^^

.. java:field:: static Random rand
   :outertype: DNSResolverIfc

Methods
-------
getDefaultHost
^^^^^^^^^^^^^^

.. java:method:: public String getDefaultHost()
   :outertype: DNSResolverIfc

   Method provides default host information for the installation. It can be both hostname or IP address.

   :return: a default host information.

getDefaultHosts
^^^^^^^^^^^^^^^

.. java:method::  String[] getDefaultHosts()
   :outertype: DNSResolverIfc

   Method provides an array of all local host informations, by default it contains defaultHost.

   :return: an array of all local hosts.

getHostIP
^^^^^^^^^

.. java:method:: public String getHostIP(String hostname) throws UnknownHostException
   :outertype: DNSResolverIfc

   Resolve IP address for the given \ ``hostname``\

   :param hostname: the domain name for which this record is valid
   :return: \ ``IP address``\  of the machine providing the service.

getHostIPs
^^^^^^^^^^

.. java:method:: public String[] getHostIPs(String hostname) throws UnknownHostException
   :outertype: DNSResolverIfc

   Resolve all IP addresses for the given \ ``hostname``\

   :param hostname: the domain name for which this record is valid
   :return: Array of all \ ``IP addresses``\  on which target host provide service.

getHostSRV_Entries
^^^^^^^^^^^^^^^^^^

.. java:method:: public DNSEntry[] getHostSRV_Entries(String hostname) throws UnknownHostException
   :outertype: DNSResolverIfc

   Retrieves list of SRV DNS entries for given \ ``hostname``\ . Performs lookup for \ ``_xmpp-server._tcp``\  SRV records.

   :param hostname: the domain name for which this record is valid
   :return: Array of the DNSEntry objects containing SRV DNS records

getHostSRV_Entries
^^^^^^^^^^^^^^^^^^

.. java:method:: public DNSEntry[] getHostSRV_Entries(String hostname, String service, int defPort) throws UnknownHostException
   :outertype: DNSResolverIfc

   Retrieves list of DNS entries for given \ ``hostname``\ . Allow specifying particular type of SRV record.

   :param hostname: the domain name for which this record is valid
   :param service: type of SRV records, for example \ ``_xmpp-server._tcp``\
   :param defPort: default port number in case DNS records is missing one.
   :return: Array of the DNSEntry records

getHostSRV_Entry
^^^^^^^^^^^^^^^^

.. java:method:: public DNSEntry getHostSRV_Entry(String hostname) throws UnknownHostException
   :outertype: DNSResolverIfc

   Retrieves service DNS entry with highest priority for given \ ``hostname``\ . Performs lookup for \ ``_xmpp-server._tcp``\  SRV records.

   :param hostname: name to resolve
   :return: DNSEntry object containing DNS record with highest priority for given \ ``hostname``\

getHostSRV_Entry
^^^^^^^^^^^^^^^^

.. java:method:: public DNSEntry getHostSRV_Entry(String hostname, String service, int defPort) throws UnknownHostException
   :outertype: DNSResolverIfc

   Retrieves list of DNS entries for given \ ``hostname``\ . Allow specifying particular type of SRV record.

   :param hostname: name to resolve
   :param service: type of SRV records, for example \ ``_xmpp-server._tcp``\
   :param defPort: default port number in case DNS records is missing one.
   :return: DNSEntry object containing DNS record with highest priority for given \ ``hostname``\

getHostSRV_IP
^^^^^^^^^^^^^

.. java:method:: public String getHostSRV_IP(String hostname) throws UnknownHostException
   :outertype: DNSResolverIfc

   Returns \ ``IP address``\  of the machine providing the service.

   :param hostname: the domain name for which this record is valid

getSecondaryHost
^^^^^^^^^^^^^^^^

.. java:method:: public String getSecondaryHost()
   :outertype: DNSResolverIfc

   Method provides alternative host information for the current instance. By default falls back to the default host information.

   :return: alternative host information.

