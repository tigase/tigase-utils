.. java:import:: java.util Comparator

.. java:import:: java.util EnumSet

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

.. java:import:: java.util.regex Matcher

.. java:import:: java.util.regex Pattern

Version
=======

.. java:package:: tigase.util
   :noindex:

.. java:type:: public class Version implements Comparable<Version>

Fields
------
VERSION_COMPARATOR
^^^^^^^^^^^^^^^^^^

.. java:field:: public static final Comparator<Version> VERSION_COMPARATOR
   :outertype: Version

ZERO
^^^^

.. java:field:: public static final Version ZERO
   :outertype: Version

incrementableFields
^^^^^^^^^^^^^^^^^^^

.. java:field:: public static final EnumSet<FIELD> incrementableFields
   :outertype: Version

Methods
-------
compareTo
^^^^^^^^^

.. java:method:: @Override public int compareTo(Version that)
   :outertype: Version

equals
^^^^^^

.. java:method:: @Override public boolean equals(Object o)
   :outertype: Version

getBaseVersion
^^^^^^^^^^^^^^

.. java:method:: public Version getBaseVersion()
   :outertype: Version

getBugfix
^^^^^^^^^

.. java:method:: public int getBugfix()
   :outertype: Version

getBuild
^^^^^^^^

.. java:method:: public int getBuild()
   :outertype: Version

getCommit
^^^^^^^^^

.. java:method:: public String getCommit()
   :outertype: Version

getMajor
^^^^^^^^

.. java:method:: public int getMajor()
   :outertype: Version

getMinor
^^^^^^^^

.. java:method:: public int getMinor()
   :outertype: Version

getTypeNumber
^^^^^^^^^^^^^

.. java:method:: public int getTypeNumber()
   :outertype: Version

getVersionType
^^^^^^^^^^^^^^

.. java:method:: public TYPE getVersionType()
   :outertype: Version

hashCode
^^^^^^^^

.. java:method:: @Override public int hashCode()
   :outertype: Version

increment
^^^^^^^^^

.. java:method:: public Version increment(FIELD field, int amount) throws IllegalArgumentException
   :outertype: Version

   Method increments given field by the specified amount

   :param field: to be incremented - only \ *incrementable*\  fields are supported, i.e. any of the \ :java:ref:`Version.incrementableFields`\ .
   :param amount: by which version should be incremented
   :return: incremented version if correct field was passed as argument, otherwise same non-incremented version is returned.

isZero
^^^^^^

.. java:method:: public boolean isZero()
   :outertype: Version

of
^^

.. java:method:: public static Version of(String str) throws IllegalArgumentException
   :outertype: Version

   Supports both tigase-server-7.2.0-SNAPSHOT-b4895-dist-max.tar.gz and version strings

   :param str: string to be parsed. Must match the supported formats
   :throws IllegalArgumentException: when provided input doesn't match supported formats
   :return: a Version object based on the provided string.

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Version

toString
^^^^^^^^

.. java:method:: public String toString(int padding)
   :outertype: Version

