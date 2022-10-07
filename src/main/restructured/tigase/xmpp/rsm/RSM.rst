.. java:import:: tigase.xml Element

RSM
===

.. java:package:: tigase.xmpp.rsm
   :noindex:

.. java:type:: public class RSM

Fields
------
XMLNS
^^^^^

.. java:field:: public static final String XMLNS
   :outertype: RSM

after
^^^^^

.. java:field::  String after
   :outertype: RSM

before
^^^^^^

.. java:field::  String before
   :outertype: RSM

count
^^^^^

.. java:field::  Integer count
   :outertype: RSM

first
^^^^^

.. java:field::  String first
   :outertype: RSM

hasBefore
^^^^^^^^^

.. java:field::  boolean hasBefore
   :outertype: RSM

index
^^^^^

.. java:field::  Integer index
   :outertype: RSM

last
^^^^

.. java:field::  String last
   :outertype: RSM

max
^^^

.. java:field::  int max
   :outertype: RSM

Constructors
------------
RSM
^^^

.. java:constructor:: public RSM(int defaultMax)
   :outertype: RSM

RSM
^^^

.. java:constructor:: public RSM()
   :outertype: RSM

Methods
-------
fromElement
^^^^^^^^^^^

.. java:method:: public RSM fromElement(Element e)
   :outertype: RSM

getAfter
^^^^^^^^

.. java:method:: public String getAfter()
   :outertype: RSM

getBefore
^^^^^^^^^

.. java:method:: public String getBefore()
   :outertype: RSM

getCount
^^^^^^^^

.. java:method:: public Integer getCount()
   :outertype: RSM

getFirst
^^^^^^^^

.. java:method:: public String getFirst()
   :outertype: RSM

getIndex
^^^^^^^^

.. java:method:: public Integer getIndex()
   :outertype: RSM

getLast
^^^^^^^

.. java:method:: public String getLast()
   :outertype: RSM

getMax
^^^^^^

.. java:method:: public int getMax()
   :outertype: RSM

hasBefore
^^^^^^^^^

.. java:method:: public boolean hasBefore()
   :outertype: RSM

parseRootElement
^^^^^^^^^^^^^^^^

.. java:method:: public static RSM parseRootElement(Element e, int defaultMax)
   :outertype: RSM

parseRootElement
^^^^^^^^^^^^^^^^

.. java:method:: public static RSM parseRootElement(Element e)
   :outertype: RSM

setAfter
^^^^^^^^

.. java:method:: public void setAfter(String after)
   :outertype: RSM

setBefore
^^^^^^^^^

.. java:method:: public void setBefore(String before)
   :outertype: RSM

setCount
^^^^^^^^

.. java:method:: public void setCount(Integer count)
   :outertype: RSM

setFirst
^^^^^^^^

.. java:method:: public void setFirst(String first)
   :outertype: RSM

setHasBefore
^^^^^^^^^^^^

.. java:method:: public void setHasBefore(boolean hasBefore)
   :outertype: RSM

setIndex
^^^^^^^^

.. java:method:: public void setIndex(Integer index)
   :outertype: RSM

setLast
^^^^^^^

.. java:method:: public void setLast(String last)
   :outertype: RSM

setMax
^^^^^^

.. java:method:: public void setMax(int max)
   :outertype: RSM

setResults
^^^^^^^^^^

.. java:method:: public void setResults(Integer count, String first, String last)
   :outertype: RSM

setResults
^^^^^^^^^^

.. java:method:: public void setResults(Integer count, Integer index)
   :outertype: RSM

toElement
^^^^^^^^^

.. java:method:: public Element toElement()
   :outertype: RSM

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: RSM

