.. java:import:: java.util Comparator

.. java:import:: java.util EnumSet

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

.. java:import:: java.util.regex Matcher

.. java:import:: java.util.regex Pattern

Version.Builder
===============

.. java:package:: tigase.util
   :noindex:

.. java:type:: public static class Builder
   :outertype: Version

Constructors
------------
Builder
^^^^^^^

.. java:constructor:: public Builder(int major, int minor, int bugfix)
   :outertype: Version.Builder

Builder
^^^^^^^

.. java:constructor:: public Builder(Version version)
   :outertype: Version.Builder

Methods
-------
build
^^^^^

.. java:method:: public Version build()
   :outertype: Version.Builder

setBuild
^^^^^^^^

.. java:method:: public Builder setBuild(int build)
   :outertype: Version.Builder

setCommit
^^^^^^^^^

.. java:method:: public Builder setCommit(String commit)
   :outertype: Version.Builder

setVersionType
^^^^^^^^^^^^^^

.. java:method:: public Builder setVersionType(TYPE versionType)
   :outertype: Version.Builder

