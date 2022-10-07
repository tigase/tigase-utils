Stringprep
==========

.. java:package:: gnu.inet.encoding
   :noindex:

.. java:type:: public class Stringprep

   This class offers static methods for preparing internationalized strings. It supports the following stringprep profiles:

   ..

   * RFC3491 nameprep
   * RFC3920 XMPP nodeprep and resourceprep

   Note that this implementation only supports 16-bit Unicode code points.

Methods
-------
contains
^^^^^^^^

.. java:method:: static boolean contains(StringBuffer s, char[] p)
   :outertype: Stringprep

contains
^^^^^^^^

.. java:method:: static boolean contains(StringBuffer s, char[][] p)
   :outertype: Stringprep

contains
^^^^^^^^

.. java:method:: static boolean contains(char c, char[][] p)
   :outertype: Stringprep

filter
^^^^^^

.. java:method:: static void filter(StringBuffer s, char[] f)
   :outertype: Stringprep

filter
^^^^^^

.. java:method:: static void filter(StringBuffer s, char[][] f)
   :outertype: Stringprep

map
^^^

.. java:method:: static void map(StringBuffer s, char[] search, String[] replace)
   :outertype: Stringprep

nameprep
^^^^^^^^

.. java:method:: public static String nameprep(String input) throws StringprepException, NullPointerException
   :outertype: Stringprep

   Preps a name according to the Stringprep profile defined in RFC3491. Unassigned code points are not allowed.

   :param input: the name to prep.
   :throws StringprepException: If the name cannot be prepped with this profile.
   :throws NullPointerException: If the name is null.
   :return: the prepped name.

nameprep
^^^^^^^^

.. java:method:: public static String nameprep(String input, boolean allowUnassigned) throws StringprepException, NullPointerException
   :outertype: Stringprep

   Preps a name according to the Stringprep profile defined in RFC3491.

   :param input: the name to prep.
   :param allowUnassigned: true if the name may contain unassigned code points.
   :throws StringprepException: If the name cannot be prepped with this profile.
   :throws NullPointerException: If the name is null.
   :return: the prepped name.

nodeprep
^^^^^^^^

.. java:method:: public static String nodeprep(String input) throws StringprepException, NullPointerException
   :outertype: Stringprep

   Preps a node name according to the Stringprep profile defined in RFC3920. Unassigned code points are not allowed.

   :param input: the node name to prep.
   :throws StringprepException: If the node name cannot be prepped with this profile.
   :throws NullPointerException: If the node name is null.
   :return: the prepped node name.

nodeprep
^^^^^^^^

.. java:method:: public static String nodeprep(String input, boolean allowUnassigned) throws StringprepException, NullPointerException
   :outertype: Stringprep

   Preps a node name according to the Stringprep profile defined in RFC3920.

   :param input: the node name to prep.
   :param allowUnassigned: true if the node name may contain unassigned code points.
   :throws StringprepException: If the node name cannot be prepped with this profile.
   :throws NullPointerException: If the node name is null.
   :return: the prepped node name.

resourceprep
^^^^^^^^^^^^

.. java:method:: public static String resourceprep(String input) throws StringprepException, NullPointerException
   :outertype: Stringprep

   Preps a resource name according to the Stringprep profile defined in RFC3920. Unassigned code points are not allowed.

   :param input: the resource name to prep.
   :throws StringprepException: If the resource name cannot be prepped with this profile.
   :throws NullPointerException: If the resource name is null.
   :return: the prepped node name.

resourceprep
^^^^^^^^^^^^

.. java:method:: public static String resourceprep(String input, boolean allowUnassigned) throws StringprepException, NullPointerException
   :outertype: Stringprep

   Preps a resource name according to the Stringprep profile defined in RFC3920.

   :param input: the resource name to prep.
   :param allowUnassigned: true if the resource name may contain unassigned code points.
   :throws StringprepException: If the resource name cannot be prepped with this profile.
   :throws NullPointerException: If the resource name is null.
   :return: the prepped node name.

