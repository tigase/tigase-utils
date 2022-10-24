.. java:import:: java.util Arrays

.. java:import:: java.util Comparator

DNSEntry
========

.. java:package:: tigase.util.dns
   :noindex:

.. java:type:: public class DNSEntry implements Comparable<DNSEntry>

   The class defines an instance of a single DNS entry.

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Constructors
------------
DNSEntry
^^^^^^^^

.. java:constructor:: public DNSEntry(String hostname, String ip)
   :outertype: DNSEntry

   Constructs DNS entry with hostname and IP to which it resolves.

   :param hostname: the domain name for which this record is valid
   :param ip: \ ``IP address``\  of the machine providing the service.

DNSEntry
^^^^^^^^

.. java:constructor:: public DNSEntry(String hostname, String[] ips)
   :outertype: DNSEntry

   Constructs DNS entry with hostname and multiple IP to which it resolves.

   :param hostname: the domain name for which this record is valid
   :param ips: Array of all \ ``IP addresses``\  on which target host provide service.

DNSEntry
^^^^^^^^

.. java:constructor:: public DNSEntry(String hostname, String ip, int port)
   :outertype: DNSEntry

   Constructs DNS entry with hostname, IP to which it resolves and a default port number used for connections.

   :param hostname: the domain name for which this record is valid
   :param ip: \ ``IP address``\  of the machine providing the service.
   :param port: the TCP or UDP port on which the service is to be found

DNSEntry
^^^^^^^^

.. java:constructor:: public DNSEntry(String hostname, String[] ips, int port)
   :outertype: DNSEntry

   Constructs DNS entry with hostname, IPs to which it resolves and a default port number used for connections.

   :param hostname: the domain name for which this record is valid
   :param ips: \ ``IP addresses``\  of the machines providing the service.
   :param port: the TCP or UDP port on which the service is to be found

DNSEntry
^^^^^^^^

.. java:constructor:: public DNSEntry(String hostname, String dnsResultHost, String ip, int port, long ttl, int priority, int weight)
   :outertype: DNSEntry

   Constructs complete SRV DNS entry.

   :param hostname: the domain name for which this record is valid
   :param dnsResultHost: the canonical hostname of the machine providing the service.
   :param ip: \ ``IP address``\  of the machine providing the service.
   :param port: the TCP or UDP port on which the service is to be found
   :param ttl: standard DNS time to live field.
   :param priority: the priority of the target host, lower value means more preferred.
   :param weight: relative weight for records with the same priority.

DNSEntry
^^^^^^^^

.. java:constructor:: public DNSEntry(String hostname, String dnsResultHost, String[] ips, int port, long ttl, int priority, int weight)
   :outertype: DNSEntry

   Constructs complete SRV DNS entry.

   :param hostname: the domain name for which this record is valid
   :param dnsResultHost: the canonical hostname of the machine providing the service.
   :param ips: Array of all \ ``IP addresses``\  on which target host provide service.
   :param port: the TCP or UDP port on which the service is to be found
   :param ttl: standard DNS time to live field.
   :param priority: the priority of the target host, lower value means more preferred.
   :param weight: relative weight for records with the same priority.

Methods
-------
compareTo
^^^^^^^^^

.. java:method:: @Override public int compareTo(DNSEntry o)
   :outertype: DNSEntry

getDnsResultHost
^^^^^^^^^^^^^^^^

.. java:method:: public String getDnsResultHost()
   :outertype: DNSEntry

   Returns the domain name for which this record is valid

   :return: the domain name for which this record is valid

getHostname
^^^^^^^^^^^

.. java:method:: public String getHostname()
   :outertype: DNSEntry

   Returns the canonical hostname of the machine providing the service.

   :return: the canonical hostname of the machine providing the service.

getIp
^^^^^

.. java:method:: public String getIp()
   :outertype: DNSEntry

   Returns \ ``IP address``\  of the machine providing the service.

   :return: \ ``IP address``\  of the machine providing the service.

getIps
^^^^^^

.. java:method:: public String[] getIps()
   :outertype: DNSEntry

   Returns array containing all \ ``IP addresses``\  on which service is available (in case hostname resolves to multiple IPs)

   :return: array containing all \ ``IP addresses``\  on which service is available

getPort
^^^^^^^

.. java:method:: public int getPort()
   :outertype: DNSEntry

   Returns the TCP or UDP port on which the service is to be found

   :return: the TCP or UDP port on which the service is to be found

getPriority
^^^^^^^^^^^

.. java:method:: public int getPriority()
   :outertype: DNSEntry

   Returns the priority of the target host, lower value means more preferred.

   :return: the priority of the target host, lower value means more preferred.

getTtl
^^^^^^

.. java:method:: public long getTtl()
   :outertype: DNSEntry

   Returns standard DNS time to live field.

   :return: standard DNS time to live field.

getWeight
^^^^^^^^^

.. java:method:: public int getWeight()
   :outertype: DNSEntry

   Returns relative weight for records with the same priority.

   :return: relative weight for records with the same priority.

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: DNSEntry

   Returns string interpretation of the DNS entry

   :return: string interpretation of the DNS entry

