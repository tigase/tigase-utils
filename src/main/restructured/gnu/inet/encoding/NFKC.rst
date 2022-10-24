NFKC
====

.. java:package:: gnu.inet.encoding
   :noindex:

.. java:type:: public class NFKC

   This class offers static methods for Unicode NFKC string normalization.

Fields
------
LBase
^^^^^

.. java:field:: static final int LBase
   :outertype: NFKC

LCount
^^^^^^

.. java:field:: static final int LCount
   :outertype: NFKC

NCount
^^^^^^

.. java:field:: static final int NCount
   :outertype: NFKC

SBase
^^^^^

.. java:field:: static final int SBase
   :outertype: NFKC

   Entire hangul code copied from: http://www.unicode.org/unicode/reports/tr15/  Several hangul specific constants

SCount
^^^^^^

.. java:field:: static final int SCount
   :outertype: NFKC

TBase
^^^^^

.. java:field:: static final int TBase
   :outertype: NFKC

TCount
^^^^^^

.. java:field:: static final int TCount
   :outertype: NFKC

VBase
^^^^^

.. java:field:: static final int VBase
   :outertype: NFKC

VCount
^^^^^^

.. java:field:: static final int VCount
   :outertype: NFKC

Methods
-------
canonicalOrdering
^^^^^^^^^^^^^^^^^

.. java:method:: static void canonicalOrdering(StringBuffer in)
   :outertype: NFKC

   Rearranges characters in a stringbuffer in order to respect the canonical ordering properties.

   :param The: StringBuffer to rearrange.

combiningClass
^^^^^^^^^^^^^^

.. java:method:: static int combiningClass(char c)
   :outertype: NFKC

   Returns the combining class of a given character.

   :param c: The character.
   :return: The combining class.

compose
^^^^^^^

.. java:method:: static int compose(char a, char b)
   :outertype: NFKC

   Tries to compose two characters canonically.

   :param a: First character.
   :param b: Second character.
   :return: The composed character or -1 if no composition could be found.

composeHangul
^^^^^^^^^^^^^

.. java:method:: static int composeHangul(char a, char b)
   :outertype: NFKC

   Composes two hangul characters.

   :param a: First character.
   :param b: Second character.
   :return: Returns the composed character or -1 if the two characters cannot be composed.

composeIndex
^^^^^^^^^^^^

.. java:method:: static int composeIndex(char a)
   :outertype: NFKC

   Returns the index inside the composition table.

   :param a: Character to look up.
   :return: Index if found, -1 otherwise.

decomposeHangul
^^^^^^^^^^^^^^^

.. java:method:: static String decomposeHangul(char s)
   :outertype: NFKC

   Decomposes a hangul character.

   :param s: A character to decompose.
   :return: A string containing the hangul decomposition of the input character. If no hangul decomposition can be found, a string containing the character itself is returned.

decomposeIndex
^^^^^^^^^^^^^^

.. java:method:: static int decomposeIndex(char c)
   :outertype: NFKC

   Returns the index inside the decomposition table, implemented using a binary search.

   :param c: Character to look up.
   :return: Index if found, -1 otherwise.

normalizeNFKC
^^^^^^^^^^^^^

.. java:method:: public static String normalizeNFKC(String in)
   :outertype: NFKC

   Applies NFKC normalization to a string.

   :param in: The string to normalize.
   :return: An NFKC normalized string.

