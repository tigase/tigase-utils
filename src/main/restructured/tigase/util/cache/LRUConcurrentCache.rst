.. java:import:: java.util LinkedHashMap

.. java:import:: java.util Map

LRUConcurrentCache
==================

.. java:package:: tigase.util.cache
   :noindex:

.. java:type:: public class LRUConcurrentCache<K, V>

Constructors
------------
LRUConcurrentCache
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public LRUConcurrentCache(int maxEntries)
   :outertype: LRUConcurrentCache

Methods
-------
clear
^^^^^

.. java:method:: public void clear()
   :outertype: LRUConcurrentCache

containsKey
^^^^^^^^^^^

.. java:method:: public boolean containsKey(K key)
   :outertype: LRUConcurrentCache

get
^^^

.. java:method:: public V get(K key)
   :outertype: LRUConcurrentCache

limit
^^^^^

.. java:method:: public int limit()
   :outertype: LRUConcurrentCache

put
^^^

.. java:method:: public void put(K key, V value)
   :outertype: LRUConcurrentCache

remove
^^^^^^

.. java:method:: public V remove(K key)
   :outertype: LRUConcurrentCache

size
^^^^

.. java:method:: public int size()
   :outertype: LRUConcurrentCache

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: LRUConcurrentCache

