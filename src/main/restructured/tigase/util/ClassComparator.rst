.. java:import:: java.io Serializable

.. java:import:: java.util Comparator

ClassComparator
===============

.. java:package:: tigase.util
   :noindex:

.. java:type:: public class ClassComparator implements Comparator<Class>, Serializable

   In a few cases classes have to be kept in \ ``Set``\ . This \ ``Comparator``\  implementation has been created to return proper value for \ ``compare``\  method and to make it possible to store classes in any \ ``Set``\ .

   Created: Sat Oct 9 22:27:54 2004

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Methods
-------
compare
^^^^^^^

.. java:method:: public int compare(Class c1, Class c2)
   :outertype: ClassComparator

   Method \ ``compare``\  is used to perform

   :param c1: an \ ``Object``\  value
   :param c2: an \ ``Object``\  value
   :return: an \ ``int``\  value

