.. java:import:: tigase.xml Element

.. java:import:: java.util List

.. java:import:: java.util Map

.. java:import:: java.util Map.Entry

.. java:import:: java.util TreeMap

ElementCriteria
===============

.. java:package:: tigase.criteria
   :noindex:

.. java:type:: public class ElementCriteria implements Criteria

   Created: 2007-06-19 20:34:57

   :author: bmalkow

Constructors
------------
ElementCriteria
^^^^^^^^^^^^^^^

.. java:constructor:: public ElementCriteria(String name, String cdata, String[] attname, String[] attValue)
   :outertype: ElementCriteria

Methods
-------
add
^^^

.. java:method:: public Criteria add(Criteria criteria)
   :outertype: ElementCriteria

empty
^^^^^

.. java:method:: public static final ElementCriteria empty()
   :outertype: ElementCriteria

match
^^^^^

.. java:method:: public boolean match(Element element)
   :outertype: ElementCriteria

name
^^^^

.. java:method:: public static final ElementCriteria name(String name)
   :outertype: ElementCriteria

name
^^^^

.. java:method:: public static final ElementCriteria name(String name, String xmlns)
   :outertype: ElementCriteria

name
^^^^

.. java:method:: public static final ElementCriteria name(String name, String cdata, String[] attNames, String[] attValues)
   :outertype: ElementCriteria

name
^^^^

.. java:method:: public static final ElementCriteria name(String name, String[] attNames, String[] attValues)
   :outertype: ElementCriteria

nameType
^^^^^^^^

.. java:method:: public static final ElementCriteria nameType(String name, String type)
   :outertype: ElementCriteria

xmlns
^^^^^

.. java:method:: public static final ElementCriteria xmlns(String xmlns)
   :outertype: ElementCriteria

