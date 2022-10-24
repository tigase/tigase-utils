.. java:import:: java.io BufferedReader

.. java:import:: java.io IOException

.. java:import:: java.io InputStreamReader

.. java:import:: java.io PrintWriter

SystemConsole
=============

.. java:package:: tigase.util.ui.console
   :noindex:

.. java:type:: public class SystemConsole implements ConsoleIfc

   Simple SystemConsole class handling user input/output from command-line, including labels and secure password reading

Constructors
------------
SystemConsole
^^^^^^^^^^^^^

.. java:constructor:: public SystemConsole()
   :outertype: SystemConsole

Methods
-------
readLine
^^^^^^^^

.. java:method:: public String readLine()
   :outertype: SystemConsole

readLine
^^^^^^^^

.. java:method:: public String readLine(String label)
   :outertype: SystemConsole

readPassword
^^^^^^^^^^^^

.. java:method:: public char[] readPassword(String label)
   :outertype: SystemConsole

writeLine
^^^^^^^^^

.. java:method:: public void writeLine(String format, Object... args)
   :outertype: SystemConsole

writeLine
^^^^^^^^^

.. java:method:: public void writeLine(Object obj)
   :outertype: SystemConsole

