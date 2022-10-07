.. java:import:: java.util.function Predicate

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

.. java:import:: java.util.stream Collectors

ParameterParser
===============

.. java:package:: tigase.util.ui.console
   :noindex:

.. java:type:: public class ParameterParser

   General purpose command-line parser accepting (if defined) both single-letter (and space separated value) and full names (and equal-sign separated value). It also supports default values and generation of help based on configured options.

Constructors
------------
ParameterParser
^^^^^^^^^^^^^^^

.. java:constructor:: public ParameterParser()
   :outertype: ParameterParser

   Constructs new \ ``ParameterParser``\  without any options and without interactive mode

ParameterParser
^^^^^^^^^^^^^^^

.. java:constructor:: public ParameterParser(boolean interactiveMode)
   :outertype: ParameterParser

   Constructs new \ ``ParameterParser``\  without any options and allowing enabling interactive mode

   :param interactiveMode: indicates whether interactive mode should be enabled (include it in the options and handle accordingly)

ParameterParser
^^^^^^^^^^^^^^^

.. java:constructor:: public ParameterParser(List<CommandlineParameter> options, boolean interactiveMode)
   :outertype: ParameterParser

   Constructs new \ ``ParameterParser``\  with initial options and allowing enabling interactive mode

   :param options: initial list of available options
   :param interactiveMode: indicates whether interactive mode should be enabled (include it in the options and handle accordingly)

Methods
-------
addOption
^^^^^^^^^

.. java:method:: public void addOption(CommandlineParameter option) throws IllegalArgumentException
   :outertype: ParameterParser

   Adds \ :java:ref:`CommandlineParameter`\  to list of available parameters

   :param option: a \ :java:ref:`CommandlineParameter`\  that should be added
   :throws IllegalArgumentException: is thrown when the same option (either same name or same short-letter) exists in the defined list.

addOptions
^^^^^^^^^^

.. java:method:: public void addOptions(List<CommandlineParameter> options)
   :outertype: ParameterParser

   Add collection of \ :java:ref:`CommandlineParameter`\  to available options. Calls internally \ :java:ref:`ParameterParser.addOption(tigase.util.ui.console.CommandlineParameter)`\

   :param options: collection of \ :java:ref:`CommandlineParameter`\  to be added.

getHelp
^^^^^^^

.. java:method:: public String getHelp()
   :outertype: ParameterParser

   Generates help output with default instruction.

   :return: String with default instruction.

getHelp
^^^^^^^

.. java:method:: public String getHelp(String executionCommand)
   :outertype: ParameterParser

   :param executionCommand: an example of the execution command, for example \ ``$ java -cp \"jars/*.jar\" tigase.util.DBSchemaLoader [options]``\
   :return: string representing all the available options and their description

getOptionByLetter
^^^^^^^^^^^^^^^^^

.. java:method:: public Optional<CommandlineParameter> getOptionByLetter(String letter)
   :outertype: ParameterParser

   Returns \ :java:ref:`CommandlineParameter`\  that matches passed parameter

   :param letter: single-letter identification of the \ :java:ref:`CommandlineParameter`\
   :return: an Optional \ :java:ref:`CommandlineParameter`\  that matches passed letter.

getOptionByName
^^^^^^^^^^^^^^^

.. java:method:: public Optional<CommandlineParameter> getOptionByName(String commandName)
   :outertype: ParameterParser

   Returns \ :java:ref:`CommandlineParameter`\  that matches passed parameter

   :param commandName: full-name identification of the \ :java:ref:`CommandlineParameter`\
   :return: an Optional \ :java:ref:`CommandlineParameter`\  that matches passed name.

getOptions
^^^^^^^^^^

.. java:method:: public List<CommandlineParameter> getOptions()
   :outertype: ParameterParser

   Retrieves List with all defined \ :java:ref:`CommandlineParameter`\  options

   :return: List with all defined \ :java:ref:`CommandlineParameter`\  options

getOptions
^^^^^^^^^^

.. java:method:: public List<CommandlineParameter> getOptions(Predicate<? super CommandlineParameter> predicate)
   :outertype: ParameterParser

   Retrieves List with all defined \ :java:ref:`CommandlineParameter`\  options that matches passed Predicate

   :param predicate: by which list should be filtered
   :return: List of \ :java:ref:`CommandlineParameter`\  filtered to matching options.

getOptionsNames
^^^^^^^^^^^^^^^

.. java:method:: public List<String> getOptionsNames(Predicate<? super CommandlineParameter> predicate)
   :outertype: ParameterParser

   Retrieves List with all defined \ :java:ref:`CommandlineParameter`\  options names that matches passed Predicate

   :param predicate: by which list should be filtered
   :return: List of \ :java:ref:`CommandlineParameter`\  names filtered to matching options.

getTask
^^^^^^^

.. java:method:: public Optional<Task> getTask()
   :outertype: ParameterParser

isInteractiveMode
^^^^^^^^^^^^^^^^^

.. java:method:: public boolean isInteractiveMode()
   :outertype: ParameterParser

main
^^^^

.. java:method:: public static void main(String[] args)
   :outertype: ParameterParser

parseArgs
^^^^^^^^^

.. java:method:: public Properties parseArgs(String[] args)
   :outertype: ParameterParser

   Main parsing method which takes as an input array of parameters and returns a Properties object with parsed parameter/values. It supports both single letter format (\ ``-X value``\  as well as full name (\ ``--name=value``\ ). In case no value for defined option was passed but it's defined as required and contains default value it will be included in the resulting Properties. If interactive mode is enabled, there will be a prompt for all missing options (defined but not included in passed parameter)

   :param args: an input array of parameters and defined values.
   :return: Properties with parameter/value pairs matching defined options.

removeOption
^^^^^^^^^^^^

.. java:method:: public void removeOption(CommandlineParameter option)
   :outertype: ParameterParser

   Removes given option from list of supported options.

   :param option: \ ``commandlineParameter``\  option to be removed

removeOptionByLetter
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void removeOptionByLetter(String singleLetter)
   :outertype: ParameterParser

   Removes given option from list of supported options by it's single letter

   :param singleLetter: identification of the option

removeOptionByName
^^^^^^^^^^^^^^^^^^

.. java:method:: public void removeOptionByName(String name)
   :outertype: ParameterParser

   Removes given option from list of supported options by it's full name

   :param name: full name identification of the option

setTasks
^^^^^^^^

.. java:method:: public void setTasks(Task[] supportedTasks)
   :outertype: ParameterParser

