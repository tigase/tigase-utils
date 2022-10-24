.. java:import:: java.util Arrays

StringUtilities
===============

.. java:package:: tigase.util
   :noindex:

.. java:type:: public class StringUtilities

   Class with string utilities, mostly helping with canonical representation of String

   :author: wojtek

Methods
-------
checkIfArrayContainsString
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static boolean checkIfArrayContainsString(char[] data, char[] string)
   :outertype: StringUtilities

checkIfArrayContainsString
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static boolean checkIfArrayContainsString(char[] data, int fromIndex, char[] string)
   :outertype: StringUtilities

convertNonPrintableCharactersToLiterals
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static String convertNonPrintableCharactersToLiterals(String input)
   :outertype: StringUtilities

convertNonPrintableCharactersToLiterals
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static String convertNonPrintableCharactersToLiterals(String input, boolean maintainWhitespace)
   :outertype: StringUtilities

intArrayToString
^^^^^^^^^^^^^^^^

.. java:method:: public static String intArrayToString(int[] arr, String separator)
   :outertype: StringUtilities

   Concatenate all elements of input array inserting separator between each

   :param arr: an array to be concatenated
   :param separator: to be inserted between each element of array
   :return: string representation of the array

internStringArray
^^^^^^^^^^^^^^^^^

.. java:method:: public static String[] internStringArray(String[] in)
   :outertype: StringUtilities

   Process all strings of an array using .intern()

   :param in: array of Strings to be interned
   :return: array of interned string

padString
^^^^^^^^^

.. java:method:: public static StringBuilder padString(StringBuilder sb, String text, int width)
   :outertype: StringUtilities

padString
^^^^^^^^^

.. java:method:: public static StringBuilder padString(StringBuilder sb, String text, int width, String leftBracket, String rightBracket)
   :outertype: StringUtilities

padString
^^^^^^^^^

.. java:method:: public static StringBuilder padString(StringBuilder sb, String text, JUSTIFY justify, int width, char padChar, String leftBracket, String rightBracket)
   :outertype: StringUtilities

padStringToColumn
^^^^^^^^^^^^^^^^^

.. java:method:: public static StringBuilder padStringToColumn(StringBuilder sb, String text, JUSTIFY justify, int column, char padChar, String leftBracket, String rightBracket)
   :outertype: StringUtilities

stringArrayToString
^^^^^^^^^^^^^^^^^^^

.. java:method:: public static String stringArrayToString(String[] arr, String separator)
   :outertype: StringUtilities

   Concatenate all elements of input array inserting separator between each

   :param arr: an array to be concatenated
   :param separator: to be inserted between each element of array
   :return: string representation of the array

stringToArrayOfString
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static String[] stringToArrayOfString(String in, String splitter)
   :outertype: StringUtilities

   Split string into an Array of Strings using provided splitter, output array is interned

   :param in: String to be splited
   :param splitter: delimiter of items
   :return: Arrays of interned Strings

