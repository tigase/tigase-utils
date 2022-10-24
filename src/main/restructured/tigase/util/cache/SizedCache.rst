.. java:import:: java.util LinkedHashMap

.. java:import:: java.util Map

SizedCache
==========

.. java:package:: tigase.util.cache
   :noindex:

.. java:type:: public class SizedCache<K, V> extends LinkedHashMap<K, V>

   Describe class SizedCache here.   Created: Mon Mar 3 15:16:52 2008

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Constructors
------------
SizedCache
^^^^^^^^^^

.. java:constructor:: public SizedCache(int maxSize)
   :outertype: SizedCache

Methods
-------
removeEldestEntry
^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
   :outertype: SizedCache

