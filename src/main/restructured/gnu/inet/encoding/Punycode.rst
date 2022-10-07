Punycode
========

.. java:package:: gnu.inet.encoding
   :noindex:

.. java:type:: public class Punycode

   This class offers static methods for encoding/decoding strings using the Punycode algorithm.

   ..

   * RFC3492 Punycode

   Note that this implementation only supports 16-bit Unicode code points.

Fields
------
BASE
^^^^

.. java:field:: static final int BASE
   :outertype: Punycode

DAMP
^^^^

.. java:field:: static final int DAMP
   :outertype: Punycode

DELIMITER
^^^^^^^^^

.. java:field:: static final char DELIMITER
   :outertype: Punycode

INITIAL_BIAS
^^^^^^^^^^^^

.. java:field:: static final int INITIAL_BIAS
   :outertype: Punycode

INITIAL_N
^^^^^^^^^

.. java:field:: static final int INITIAL_N
   :outertype: Punycode

SKEW
^^^^

.. java:field:: static final int SKEW
   :outertype: Punycode

TMAX
^^^^

.. java:field:: static final int TMAX
   :outertype: Punycode

TMIN
^^^^

.. java:field:: static final int TMIN
   :outertype: Punycode

Methods
-------
adapt
^^^^^

.. java:method:: public static final int adapt(int delta, int numpoints, boolean first)
   :outertype: Punycode

codepoint2digit
^^^^^^^^^^^^^^^

.. java:method:: public static final int codepoint2digit(int c) throws PunycodeException
   :outertype: Punycode

decode
^^^^^^

.. java:method:: public static String decode(String input) throws PunycodeException
   :outertype: Punycode

   Decode a punycoded string.

   :param input: Punycode string
   :return: Unicode string.

digit2codepoint
^^^^^^^^^^^^^^^

.. java:method:: public static final int digit2codepoint(int d) throws PunycodeException
   :outertype: Punycode

encode
^^^^^^

.. java:method:: public static String encode(String input) throws PunycodeException
   :outertype: Punycode

   Punycodes a unicode string.

   :param input: Unicode string.
   :return: Punycoded string.

isBasic
^^^^^^^

.. java:method:: public static final boolean isBasic(char c)
   :outertype: Punycode

