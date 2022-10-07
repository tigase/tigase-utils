.. java:import:: java.util.logging Logger

SimpleCache
===========

.. java:package:: tigase.util.cache
   :noindex:

.. java:type:: public class SimpleCache<K, V> implements Map<K, V>

   Describe class SimpleCache here.   Created: Sun Nov 26 19:55:22 2006

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Fields
------
cache_off
^^^^^^^^^

.. java:field:: protected boolean cache_off
   :outertype: SimpleCache

Constructors
------------
SimpleCache
^^^^^^^^^^^

.. java:constructor:: public SimpleCache(int maxSize, long time)
   :outertype: SimpleCache

Methods
-------
clear
^^^^^

.. java:method:: public void clear()
   :outertype: SimpleCache

containsKey
^^^^^^^^^^^

.. java:method:: public boolean containsKey(Object key)
   :outertype: SimpleCache

containsValue
^^^^^^^^^^^^^

.. java:method:: public boolean containsValue(Object value)
   :outertype: SimpleCache

dataMap
^^^^^^^

.. java:method:: protected Map<K, V> dataMap()
   :outertype: SimpleCache

entrySet
^^^^^^^^

.. java:method:: public Set<Map.Entry<K, V>> entrySet()
   :outertype: SimpleCache

equals
^^^^^^

.. java:method:: public boolean equals(Object o)
   :outertype: SimpleCache

get
^^^

.. java:method:: public V get(Object key)
   :outertype: SimpleCache

hashCode
^^^^^^^^

.. java:method:: public int hashCode()
   :outertype: SimpleCache

isEmpty
^^^^^^^

.. java:method:: public boolean isEmpty()
   :outertype: SimpleCache

keySet
^^^^^^

.. java:method:: public Set<K> keySet()
   :outertype: SimpleCache

put
^^^

.. java:method:: public V put(K key, V value)
   :outertype: SimpleCache

putAll
^^^^^^

.. java:method:: public void putAll(Map<? extends K, ? extends V> m)
   :outertype: SimpleCache

remove
^^^^^^

.. java:method:: public V remove(Object key)
   :outertype: SimpleCache

removeOld
^^^^^^^^^

.. java:method:: public void removeOld()
   :outertype: SimpleCache

size
^^^^

.. java:method:: public int size()
   :outertype: SimpleCache

values
^^^^^^

.. java:method:: public Collection<V> values()
   :outertype: SimpleCache

