IDNA
====

.. java:package:: gnu.inet.encoding
   :noindex:

.. java:type:: public class IDNA

   This class offers static methods for converting internationalized domain names to ACE and back.

   ..

   * RFC3490 IDNA

   Note that this implementation only supports 16-bit Unicode code points.

Fields
------
ACE_PREFIX
^^^^^^^^^^

.. java:field:: public static final String ACE_PREFIX
   :outertype: IDNA

Methods
-------
toASCII
^^^^^^^

.. java:method:: public static String toASCII(String input) throws IDNAException
   :outertype: IDNA

   Converts a Unicode string to ASCII using the procedure in RFC3490 section 4.1. Unassigned characters are not allowed and STD3 ASCII rules are enforced. The input string may be a domain name containing dots.

   :param input: Unicode string.
   :return: Encoded string.

toASCII
^^^^^^^

.. java:method:: public static String toASCII(String input, boolean allowUnassigned, boolean useSTD3ASCIIRules) throws IDNAException
   :outertype: IDNA

   Converts a Unicode string to ASCII using the procedure in RFC3490 section 4.1. Unassigned characters are not allowed and STD3 ASCII rules are enforced.

   :param input: Unicode string.
   :param allowUnassigned: Unassigned characters, allowed or not?
   :param useSTD3ASCIIRules: STD3 ASCII rules, enforced or not?
   :return: Encoded string.

toUnicode
^^^^^^^^^

.. java:method:: public static String toUnicode(String input)
   :outertype: IDNA

   Converts an ASCII-encoded string to Unicode. Unassigned characters are not allowed and STD3 hostnames are enforced. Input may be domain name containing dots.

   :param input: ASCII input string.
   :return: Unicode string.

toUnicode
^^^^^^^^^

.. java:method:: public static String toUnicode(String input, boolean allowUnassigned, boolean useSTD3ASCIIRules)
   :outertype: IDNA

   Converts an ASCII-encoded string to Unicode.

   :param input: ASCII input string.
   :param allowUnassigned: Allow unassigned Unicode characters.
   :param useSTD3ASCIIRules: Check that the output conforms to STD3.
   :return: Unicode string.

